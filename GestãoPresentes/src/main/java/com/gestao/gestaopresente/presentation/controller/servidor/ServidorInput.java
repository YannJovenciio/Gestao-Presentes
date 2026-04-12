package com.gestao.gestaopresente.presentation.controller.servidor;

import com.gestao.gestaopresente.domain.e.Sexo;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ServidorInput(
        long funcaoId
        , Sexo sexo
        , LocalDate dataNasc
        , @NotNull Double salario
        , @Email @NotNull @NotBlank(message = "Email is required") String email
        , @NotNull @NotBlank(message = "Cpf is required") String cpf
        , @NotNull @NotBlank(message = "Complete name is required") String nomeCompleto
) {
}
