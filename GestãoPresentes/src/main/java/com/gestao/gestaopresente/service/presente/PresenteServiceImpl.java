package com.gestao.gestaopresente.service.presente;

import com.gestao.gestaopresente.domain.entity.many2many.Presente;
import com.gestao.gestaopresente.infra.repository.PresenteRepository;
import com.gestao.gestaopresente.infra.repository.ServidorRepository;
import com.gestao.gestaopresente.presentation.controller.presente.PresenteInput;
import com.gestao.gestaopresente.presentation.controller.presente.PresenteResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Log
@Service
public class PresenteServiceImpl implements IPresenteService {
    private final PresenteRepository presenteRepository;
    private final ServidorRepository servidorRepository;


    public PresenteServiceImpl(PresenteRepository presenteRepository, ServidorRepository servidorRepository) {
        this.presenteRepository = presenteRepository;
        this.servidorRepository = servidorRepository;
    }

    @Override
    public PresenteResponse getById(Long id) {
        var presente = presenteRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Entidade não encontrada para o id fornecido"));
        return new PresenteResponse(presente.getId(), presente.getPaisId(), presente.getDataEntrega(), presente.getObservacao(), presente.getValor());
    }

    @Override
    public List<PresenteResponse> getAll(int page, int size) {
        log.info("Buscando todos os presentes com paginação");
        var pageable = PageRequest.of(page, size);
        var presentes = presenteRepository.findAll(pageable);
        return presentes.map(p -> new PresenteResponse(p.getId(), p.getPaisId(), p.getDataEntrega(), p.getObservacao(), p.getValor())).toList();
    }

    @Override
    public List<PresenteResponse> getAllWithServidor(int page, int size) {

        log.info("Buscando todos os presentes com paginação");
        var presentes = presenteRepository.findAllWithServidor();
        return presentes.stream().map(p -> new PresenteResponse(p.getId(), p.getPaisId(), p.getDataEntrega(), p.getObservacao(), p.getValor())).toList();
    }

    @Override
    public PresenteResponse create(PresenteInput input, String servidorEmail) {

        var servidor = servidorRepository.findByEmail(servidorEmail);
        log.info("Criando presente");
        var presente = new Presente(servidor, input.dataEntrega(), input.observacao(), input.valor());
        presenteRepository.save(presente);
        return new PresenteResponse(presente.getId(), presente.getPaisId(), presente.getDataEntrega(), presente.getObservacao(), presente.getValor());
    }
}
