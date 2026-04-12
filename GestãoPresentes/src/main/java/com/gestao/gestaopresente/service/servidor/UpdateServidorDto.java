package com.gestao.gestaopresente.service.servidor;

import com.gestao.gestaopresente.domain.entity.Funcao;

public record UpdateServidorDto(UpdateCheck Check, Funcao funcao, double salario, String email) {
}
