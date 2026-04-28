package com.gestao.gestaopresente.presentation.controller.auth;

import com.gestao.gestaopresente.presentation.dto.Response;
import com.gestao.gestaopresente.service.auth.AuthService;
import com.gestao.gestaopresente.service.auth.JwtService.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Autenticação", description = "Endpoints para autenticação e registro de usuários")
public class AuthController {
    private final AuthService authService;
    private final JwtService jwtService;

    public AuthController(AuthService authService, JwtService jwtService) {
        this.authService = authService;
        this.jwtService = jwtService;
    }

    @Operation(
            summary = "Fazer login",
            description =
                    "Autentica um usuário e retorna um token JWT válido para acesso aos endpoints protegidos")
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Login realizado com sucesso",
                        content =
                                @Content(
                                        mediaType = "application/json",
                                        schema = @Schema(implementation = LoginResponse.class))),
                @ApiResponse(
                        responseCode = "401",
                        description = "Credenciais inválidas",
                        content = @Content)
            })
    @PostMapping("/login")
    public ResponseEntity<Response<LoginResponse>> authenticate(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                            description = "Credenciais de login",
                            required = true)
                    @Valid
                    @RequestBody
                    LoginDto input) {
        log.info("Recebendo request para /login");

        var usuario = authService.authenticate(input);
        String jwtToken = jwtService.generateToken(usuario);
        var loginResponse = new LoginResponse(jwtToken, jwtService.getExpirationTime());
        var response = new Response<>(loginResponse, "Succesfull");
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Registrar novo usuário",
            description = "Cria uma nova conta de usuário no sistema")
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "201",
                        description = "Usuário registrado com sucesso",
                        content = @Content(mediaType = "application/json")),
                @ApiResponse(
                        responseCode = "400",
                        description = "Dados inválidos ou email já cadastrado",
                        content = @Content)
            })
    @PostMapping("/register")
    public ResponseEntity signUp(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                            description = "Dados para registro do usuário",
                            required = true)
                    @Valid
                    @RequestBody
                    RegisterDto registerDto) {
        log.info("Recebendo request para /register");
        var usuario = authService.signup(registerDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
    }
}
