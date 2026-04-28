package com.gestao.gestaopresente.infra.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info =
                @Info(
                        title = "Gestão de Presentes API",
                        version = "1.0.0",
                        description =
                                """
                        API REST para gerenciamento de presentes com autenticação JWT.

                        ### Funcionalidades:
                        - 🔐 Autenticação e autorização com JWT
                        - 👥 Gerenciamento de Servidores
                        - 🎁 Gerenciamento de Presentes
                        - 📋 Gerenciamento de Avaliadores
                        - 🏢 Gerenciamento de Funções

                        ### Como usar:
                        1. Registre um usuário em `/api/auth/register`
                        2. Faça login em `/api/auth/login` para obter o token JWT
                        3. Use o token nos endpoints protegidos clicando em **Authorize** (🔒) no topo da página
                        4. Insira o token no formato: `Bearer seu_token_aqui`
                        """,
                        contact =
                                @Contact(
                                        name = "Yan Jovencio Miranda",
                                        email = "contato@gestaopresentes.com")),
        servers = {
            @Server(url = "http://localhost:8080", description = "Servidor de Desenvolvimento")
        })
@SecurityScheme(
        name = "bearer-jwt",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT",
        description =
                """
        Insira o token JWT obtido após o login.

        Formato: Bearer {token}

        Exemplo: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
        """)
public class OpenApiConfig {}
