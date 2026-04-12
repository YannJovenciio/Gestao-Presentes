package com.gestao.gestaopresente.service.auth;

import com.gestao.gestaopresente.domain.entity.Usuario;
import com.gestao.gestaopresente.infra.repository.UsuarioRepository;
import com.gestao.gestaopresente.presentation.controller.auth.LoginDto;
import com.gestao.gestaopresente.presentation.controller.auth.RegisterDto;
import com.gestao.gestaopresente.service.auth.JwtService.UsuarioResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthService {
    private final UsuarioRepository repository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public AuthService(
            UsuarioRepository repository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public UsuarioResponse signup(RegisterDto input) {
        if (repository.existsByEmail(input.email()))
            throw new IllegalArgumentException("Email fornecido já está em uso");

        Usuario usuario = new Usuario();
        usuario.setFullName(input.fullName());
        usuario.setEmail(input.email());
        String senhaHasheada = passwordEncoder.encode(input.password());
        log.info("Senha original: {}", input.password());
        log.info("Senha hasheada: {}", senhaHasheada);
        usuario.setPassword(senhaHasheada);

        repository.save(usuario);
        return new UsuarioResponse(usuario.getFullName(), usuario.getEmail(), usuario.getCreatedAt());
    }

    public Usuario authenticate(LoginDto input) {
        log.info("Autenticando usuário: {}", input.email());

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            input.email(),
                            input.password()
                    )
            );
        } catch (Exception e) {
            log.error("Erro na autenticação: {}", e.getMessage());
            Usuario usuarioTemp = repository.findByEmail(input.email());
            if (usuarioTemp != null) {
                log.debug("Usuário encontrado: {}", usuarioTemp.getEmail());
                log.debug("Senha no banco (hash): {}", usuarioTemp.getPassword());
                boolean senhaValida = passwordEncoder.matches(input.password(), usuarioTemp.getPassword());
                log.debug("Senha válida: {}", senhaValida);
            }
            throw e;
        }

        Usuario usuario = repository.findByEmail(input.email());
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não encontrado");
        }
        log.info("Autenticação bem-sucedida para: {}", input.email());
        return usuario;
    }

}
