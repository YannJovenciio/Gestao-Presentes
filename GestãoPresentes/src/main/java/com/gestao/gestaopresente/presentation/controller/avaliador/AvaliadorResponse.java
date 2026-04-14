package com.gestao.gestaopresente.presentation.controller.avaliador;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gestao.gestaopresente.domain.e.Sexo;
import com.gestao.gestaopresente.domain.entity.many2many.Presente;
import java.util.List;

public record AvaliadorResponse(
        Long id,
        String nome,
        Sexo sexo,
        String endereco,
        String telefone,
        String email,
        @JsonProperty(required = false) List<Presente> presentes) {

    public AvaliadorResponse(
            Long id, String nome, Sexo sexo, String endereco, String telefone, String email) {
        this(id, nome, sexo, endereco, telefone, email, null);
    }
}
