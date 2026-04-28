package com.gestao.gestaopresente.service.funcao;

import com.gestao.gestaopresente.domain.entity.Funcao;
import com.gestao.gestaopresente.infra.repository.FuncaoRepository;
import com.gestao.gestaopresente.presentation.controller.funcao.FuncaoInput;
import com.gestao.gestaopresente.presentation.controller.funcao.FuncaoResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FuncaoServiceImpl implements IFuncaoService {
    private final FuncaoRepository funcaoRepository;

    public FuncaoServiceImpl(FuncaoRepository funcaoRepository) {
        this.funcaoRepository = funcaoRepository;
    }

    @Override
    public FuncaoResponse create(FuncaoInput input) {
        var funcao = new Funcao(input.nome(), input.descricao());
        funcaoRepository.save(funcao);
        return new FuncaoResponse(funcao.getId(), funcao.getNome(), funcao.getDescricao());
    }

    @Override
    public void delete(Long id) {
        if (!funcaoRepository.existsById(id)) {
            throw new EntityNotFoundException("Função com ID " + id + " não encontrada");
        }
        funcaoRepository.deleteById(id);
    }

    @Override
    public FuncaoResponse update(Long id, FuncaoInput input) {
        var funcao =
                funcaoRepository
                        .findById(id)
                        .orElseThrow(
                                () ->
                                        new EntityNotFoundException(
                                                "Função com ID " + id + " não encontrada"));

        funcao.setNome(input.nome());
        funcao.setDescricao(input.descricao());

        funcaoRepository.save(funcao);
        return new FuncaoResponse(funcao.getId(), funcao.getNome(), funcao.getDescricao());
    }
}
