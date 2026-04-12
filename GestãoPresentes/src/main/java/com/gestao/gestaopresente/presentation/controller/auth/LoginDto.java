package com.gestao.gestaopresente.presentation.controller.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record LoginDto(
        @NotBlank(message = "Email é obrigatório")
        @JsonProperty("email")
        String email,
        @NotBlank(message = "Senha é obrigatório")
        @JsonProperty("password")
        String password
) {
}
