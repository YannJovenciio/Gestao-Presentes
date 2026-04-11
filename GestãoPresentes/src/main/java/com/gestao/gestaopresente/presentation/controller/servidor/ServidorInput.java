package com.gestao.gestaopresente.presentation.controller.servidor;

import com.gestao.gestaopresente.domain.e.Sexo;
import com.gestao.gestaopresente.domain.entity.Funcao;

import java.time.LocalDate;

public record ServidorInput(
        Funcao funcao
        , Sexo sexo
        , LocalDate dataNasc
        , Double salario
        , String email
        , String cpf
        , String nomeCompleto
) {
}
