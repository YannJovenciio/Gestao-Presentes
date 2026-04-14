package com.gestao.gestaopresente.service.servidor;

import com.gestao.gestaopresente.presentation.controller.servidor.ServidorInput;
import com.gestao.gestaopresente.presentation.controller.servidor.ServidorResponse;
import java.util.List;

public interface IServidorService {
    ServidorResponse getByEmail(String email);

    List<ServidorResponse> getAll();

    ServidorResponse create(ServidorInput input);

    ServidorResponse update(Long id, ServidorInput input);

    void delete(Long id);
}
