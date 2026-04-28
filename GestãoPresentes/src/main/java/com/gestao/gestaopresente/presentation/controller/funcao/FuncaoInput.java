package com.gestao.gestaopresente.presentation.controller.funcao;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record FuncaoInput(
        @NotBlank(message = "Nome da função é obrigatório")
                @Size(min = 3, max = 50, message = "Nome deve ter entre 3 e 50 caracteres")
                String nome,
        @Size(max = 255, message = "Descrição deve ter no máximo 255 caracteres")
                String descricao) {}
