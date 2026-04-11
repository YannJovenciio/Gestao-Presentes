package com.gestao.gestaopresente.infra.repository;

import com.gestao.gestaopresente.domain.entity.Servidor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServidorRepository extends JpaRepository<Servidor, Long> {
    Servidor findByEmail(String email);
}
