package com.gestao.gestaopresente.presentation.controller.presente;

import java.time.LocalDate;

public record PresenteInput(
        Long paisId, Long servidorId, LocalDate dataEntrega, String observacao, double valor) {}
