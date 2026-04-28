package com.gestao.gestaopresente.service.funcao;

import com.gestao.gestaopresente.presentation.controller.funcao.FuncaoInput;
import com.gestao.gestaopresente.presentation.controller.funcao.FuncaoResponse;

public interface IFuncaoService {
    FuncaoResponse create(FuncaoInput input);

    void delete(Long id);

    FuncaoResponse update(Long id, FuncaoInput input);
}
