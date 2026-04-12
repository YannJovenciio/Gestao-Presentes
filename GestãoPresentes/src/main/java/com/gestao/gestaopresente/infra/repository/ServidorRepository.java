package com.gestao.gestaopresente.infra.repository;

import com.gestao.gestaopresente.domain.entity.Servidor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ServidorRepository extends JpaRepository<Servidor, Long> {
    @Query("SELECT s FROM Servidor s JOIN FETCH s.funcao WHERE s.email = :email")
    Servidor findByEmail(@Param("email") String email);
}
