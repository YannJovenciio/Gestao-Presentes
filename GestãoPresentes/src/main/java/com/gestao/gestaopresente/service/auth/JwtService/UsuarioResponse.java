package com.gestao.gestaopresente.service.auth.JwtService;

import java.util.Date;

public record UsuarioResponse(
        String fullName,
        String email,
        Date createdAt) {
}
