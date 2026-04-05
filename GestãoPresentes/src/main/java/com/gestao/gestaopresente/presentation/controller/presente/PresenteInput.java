package com.gestao.gestaopresente.presentation.controller.presente;

import com.gestao.gestaopresente.domain.entity.Pais;
import com.gestao.gestaopresente.domain.entity.Servidor;

import java.time.LocalDate;

public record PresenteInput(Pais paisId, Servidor Servidor, LocalDate dataEntrega, String observacao, double valor) {
}
