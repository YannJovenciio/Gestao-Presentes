package com.gestao.gestaopresente.presentation.controller.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterDto(
        @NotBlank(message = "Nome é obrigatório")
        @JsonProperty("fullName")
        String fullName,
        @NotBlank(message = "Email é obrigatório")
        @Email(message = "Email invalido")
        @JsonProperty("email")
        String email,
        @NotBlank(message = "Senha é obrigatório")
        @JsonProperty("password")
        String password
) {
}
