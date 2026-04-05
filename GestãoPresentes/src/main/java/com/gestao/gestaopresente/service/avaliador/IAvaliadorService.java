package com.gestao.gestaopresente.service.avaliador;

import com.gestao.gestaopresente.presentation.controller.avaliador.AvaliadorInput;
import com.gestao.gestaopresente.presentation.controller.avaliador.AvaliadorResponse;

import java.util.List;

public interface IAvaliadorService {


    AvaliadorResponse createAvaliador(AvaliadorInput input);

    void deleteAvaliador(Long id);

    AvaliadorResponse getAvaliadorById(Long id);

    /**
     * ⚠️ Pode caussar N+1, use com cautela em dados grandes
     */
    AvaliadorResponse getAvaliadorByIdWithPresentes(Long id);

    List<AvaliadorResponse> getAllAvaliador(int page, int size);

    /**
     * ⚠️ Pode causar N+1, considere usar projeções ou @EntityGraph
     */
    List<AvaliadorResponse> getAllAvaliadorWithPresentes(int page, int size);
}
