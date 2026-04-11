package com.gestao.gestaopresente.infra.repository;

import com.gestao.gestaopresente.domain.entity.Funcao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuncaoRepository extends JpaRepository<Funcao, Long> {
}
