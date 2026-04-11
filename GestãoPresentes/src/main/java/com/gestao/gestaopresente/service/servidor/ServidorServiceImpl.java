package com.gestao.gestaopresente.service.servidor;

import com.gestao.gestaopresente.domain.entity.Servidor;
import com.gestao.gestaopresente.infra.repository.ServidorRepository;
import com.gestao.gestaopresente.presentation.controller.servidor.ServidorInput;
import com.gestao.gestaopresente.presentation.controller.servidor.ServidorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ServidorServiceImpl implements ServidorService {
    private final ServidorRepository servidorRepository;

    public ServidorServiceImpl(ServidorRepository servidorRepository) {
        this.servidorRepository = servidorRepository;
    }

    @Override
    public ServidorResponse getServidorByEmail(String email) {
        var servidor = servidorRepository.findByEmail(email);
        return new ServidorResponse(servidor.getId(), servidor.getFuncao(), servidor.getSexo(), servidor.getDataNasc(), servidor.getSalario(), servidor.getEmail(), servidor.getCpf(), servidor.getNomeCompleto());
    }

    @Override
    public ServidorResponse createServidor(ServidorInput input) {
        var servidor = new Servidor(input.funcao(), input.sexo(), input.dataNasc(), input.salario(), input.email(), input.cpf(), input.nomeCompleto());
        servidorRepository.save(servidor);
        return new ServidorResponse(servidor.getId(), servidor.getFuncao(), servidor.getSexo(), servidor.getDataNasc(), servidor.getSalario(), servidor.getEmail(), servidor.getCpf(), servidor.getNomeCompleto());
    }

}
