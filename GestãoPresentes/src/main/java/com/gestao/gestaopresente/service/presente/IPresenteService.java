package com.gestao.gestaopresente.service.presente;

import com.gestao.gestaopresente.presentation.controller.presente.PresenteInput;
import com.gestao.gestaopresente.presentation.controller.presente.PresenteResponse;

import java.util.List;

public interface IPresenteService {
    PresenteResponse getById(Long id);

    List<PresenteResponse> getAll(int page, int size);

    List<PresenteResponse> getAllWithServidor(int page, int size);

    PresenteResponse create(PresenteInput input, String servidorEmail);
}
