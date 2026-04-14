package com.gestao.gestaopresente.presentation.controller.avaliador;

import com.gestao.gestaopresente.domain.e.Sexo;
import jakarta.validation.constraints.*;

public record AvaliadorInput(
        @NotBlank(message = "Nome é obrigatório")
                @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
                String nome,
        @NotNull(message = "Sexo é obrigatório") Sexo sexo,
        @NotBlank(message = "Endereço é obrigatório")
                @Size(min = 5, max = 200, message = "Endereço deve ter entre 5 e 200 caracteres")
                String endereco,
        @NotBlank(message = "Telefone é obrigatório")
                @Pattern(
                        regexp = "^\\(\\d{2}\\)\\d{4,5}-\\d{4}$",
                        message = "Telefone inválido: use formato (XX)XXXXX-XXXX")
                String telefone,
        @NotBlank(message = "Email é obrigatório") @Email(message = "Email inválido")
                String email) {}
