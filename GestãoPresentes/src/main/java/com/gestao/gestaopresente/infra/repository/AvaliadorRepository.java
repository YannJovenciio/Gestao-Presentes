package com.gestao.gestaopresente.infra.repository;

import com.gestao.gestaopresente.domain.entity.Avaliador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AvaliadorRepository extends JpaRepository<Avaliador, Long> {

    @Query("SELECT a FROM Avaliador a LEFT JOIN FETCH a.presentes WHERE a.id = :id")
    Optional<Avaliador> findByIdWithPresentes(@Param("id") Long id);

    @Query("SELECT DISTINCT a FROM Avaliador a LEFT JOIN FETCH a.presentes")
    List<Avaliador> findAllWithPresentes();
}
