package com.gestao.gestaopresente.infra.repository;

import com.gestao.gestaopresente.domain.entity.many2many.Presente;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PresenteRepository extends JpaRepository<Presente, Long> {
    @Query("SELECT p FROM Presente p LEFT JOIN FETCH p.servidor")
    List<Presente> findAllWithServidor();
}
