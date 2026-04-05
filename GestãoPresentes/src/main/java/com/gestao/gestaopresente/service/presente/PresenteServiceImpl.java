package com.gestao.gestaopresente.service.presente;

import com.gestao.gestaopresente.infra.repository.PresenteRepository;
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

    public PresenteServiceImpl(PresenteRepository presenteRepository) {
        this.presenteRepository = presenteRepository;
    }

    @Override
    public PresenteResponse getPresenteById(Long id) {
        var presente = presenteRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Entidade não encontrada para o id fornecido"));
        return new PresenteResponse(presente.getId(), presente.getPaisId(), presente.getDataEntrega(), presente.getObservacao(), presente.getValor());
    }

    @Override
    public List<PresenteResponse> getAllPresentes(int page, int size) {
        log.info("Buscando todos os presentes com paginação");
        var pageable = PageRequest.of(page, size);
        var presentes = presenteRepository.findAll(pageable);
        return presentes.map(p -> new PresenteResponse(p.getId(), p.getPaisId(), p.getDataEntrega(), p.getObservacao(), p.getValor())).toList();
    }

    @Override
    public List<PresenteResponse> getAllPresentesWithServidor(int page, int size) {

        log.info("Buscando todos os presentes com paginação");
        var pageable = PageRequest.of(page, size);
        var presentes = presenteRepository.findAllWithServidor(pageable);

        return presentes.stream().map(p -> new PresenteResponse(p.getId(), p.getPaisId(), p.getDataEntrega(), p.getObservacao(), p.getValor())).toList();
    }

    @Override

    public PresenteResponse createPresente(PresenteInput input) {
        return null;
    }
}
