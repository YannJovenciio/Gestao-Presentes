package com.gestao.gestaopresente.presentation.controller.presente;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gestao.gestaopresente.domain.entity.Pais;
import com.gestao.gestaopresente.domain.entity.Servidor;

import java.time.LocalDate;

public record PresenteResponse(Long id, Pais paisId, LocalDate dataEntrega, String observacao,
                               double valor, @JsonProperty(required = false) Servidor Servidor) {
    public PresenteResponse(Long id, Pais paisId, LocalDate dataEntrega, String observacao, double valor) {
        this(id, paisId, dataEntrega, observacao, valor, null);
    }
}