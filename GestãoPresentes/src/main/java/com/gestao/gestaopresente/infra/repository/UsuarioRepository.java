package com.gestao.gestaopresente.infra.repository;

import com.gestao.gestaopresente.domain.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    public Usuario findByEmail(String email);
}
