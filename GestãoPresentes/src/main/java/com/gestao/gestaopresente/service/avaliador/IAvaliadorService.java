package com.gestao.gestaopresente.service.avaliador;

import com.gestao.gestaopresente.presentation.controller.avaliador.AvaliadorInput;
import com.gestao.gestaopresente.presentation.controller.avaliador.AvaliadorResponse;
import java.util.List;

public interface IAvaliadorService {

    AvaliadorResponse create(AvaliadorInput input);

    void delete(Long id);

    AvaliadorResponse getById(Long id);

    AvaliadorResponse getByIdWithPresentes(Long id);

    List<AvaliadorResponse> getAll(int page, int size);

    List<AvaliadorResponse> getAllWithPresentes(int page, int size);
}
