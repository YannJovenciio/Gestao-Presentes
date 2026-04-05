package com.gestao.gestaopresente.service.presente;

import com.gestao.gestaopresente.presentation.controller.presente.PresenteInput;
import com.gestao.gestaopresente.presentation.controller.presente.PresenteResponse;

import java.util.List;

public interface IPresenteService {
    PresenteResponse getPresenteById(Long id);

    List<PresenteResponse> getAllPresentes(int page, int size);

    List<PresenteResponse> getAllPresentesWithServidor(int page, int size);

    PresenteResponse createPresente(PresenteInput input);
}
