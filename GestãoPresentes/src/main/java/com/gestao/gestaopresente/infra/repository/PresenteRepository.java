package com.gestao.gestaopresente.infra.repository;

import com.gestao.gestaopresente.domain.entity.many2many.Presente;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PresenteRepository extends JpaRepository<Presente, Long> {
    @Query("SELECT DISTINCT p FROM Presente a LEFT JOIN FETCH a.servidor")
    List<Presente> findAllWithServidor(PageRequest pageable);
}
