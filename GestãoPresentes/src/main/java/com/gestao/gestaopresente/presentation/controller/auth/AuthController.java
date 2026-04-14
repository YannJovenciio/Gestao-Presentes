package com.gestao.gestaopresente.presentation.controller.auth;

import com.gestao.gestaopresente.presentation.dto.Response;
import com.gestao.gestaopresente.service.auth.AuthService;
import com.gestao.gestaopresente.service.auth.JwtService.JwtService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    private final JwtService jwtService;

    public AuthController(AuthService authService, JwtService jwtService) {
        this.authService = authService;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<Response<LoginResponse>> authenticate(
            @Valid @RequestBody LoginDto input) {
        log.info("Recebendo request para /login");

        var usuario = authService.authenticate(input);
        String jwtToken = jwtService.generateToken(usuario);
        var loginResponse = new LoginResponse(jwtToken, jwtService.getExpirationTime());
        var response = new Response<>(loginResponse, "Succesfull");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity signUp(@Valid @RequestBody RegisterDto registerDto) {
        log.info("Recebendo request para /register");
        var usuario = authService.signup(registerDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
    }
}
