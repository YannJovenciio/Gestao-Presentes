package com.gestao.gestaopresente.presentation.controller.servidor;

import com.gestao.gestaopresente.domain.e.Sexo;
import com.gestao.gestaopresente.presentation.validation.AdvancedInfo;
import com.gestao.gestaopresente.presentation.validation.BasicInfo;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record ServidorInput(
        @NotNull(groups = {BasicInfo.class, AdvancedInfo.class})
                @NotBlank(
                        message = "Funcao id is required",
                        groups = {BasicInfo.class, AdvancedInfo.class})
                Long funcaoId,
        @NotNull(groups = {BasicInfo.class, AdvancedInfo.class}) Sexo sexo,
        @NotNull(groups = {BasicInfo.class, AdvancedInfo.class}) LocalDate dataNasc,
        @NotNull(groups = {BasicInfo.class, AdvancedInfo.class})
                @Positive(groups = {BasicInfo.class, AdvancedInfo.class})
                Double salario,
        @Email(groups = {BasicInfo.class, AdvancedInfo.class})
                @NotNull(groups = {BasicInfo.class, AdvancedInfo.class})
                @NotBlank(
                        message = "Email is required",
                        groups = {BasicInfo.class, AdvancedInfo.class})
                String email,
        @NotNull(groups = {BasicInfo.class, AdvancedInfo.class})
                @Size(
                        min = 11,
                        max = 11,
                        groups = {BasicInfo.class, AdvancedInfo.class})
                @NotBlank(
                        message = "Cpf is required",
                        groups = {BasicInfo.class, AdvancedInfo.class})
                String cpf,
        @NotNull(groups = {BasicInfo.class, AdvancedInfo.class})
                @Size(
                        min = 3,
                        max = 50,
                        groups = {BasicInfo.class, AdvancedInfo.class})
                @NotBlank(
                        message = "Complete name is required",
                        groups = {BasicInfo.class, AdvancedInfo.class})
                String nomeCompleto) {}
