package com.gestao.gestaopresente.service.servidor;

import com.gestao.gestaopresente.presentation.controller.servidor.ServidorInput;
import com.gestao.gestaopresente.presentation.controller.servidor.ServidorResponse;

public interface ServidorService {
    ServidorResponse getServidorByEmail(String email);

    ServidorResponse createServidor(ServidorInput input);
}
