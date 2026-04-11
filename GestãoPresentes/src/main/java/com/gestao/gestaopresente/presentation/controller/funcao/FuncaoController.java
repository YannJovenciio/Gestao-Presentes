package com.gestao.gestaopresente.presentation.controller.funcao;

import com.gestao.gestaopresente.domain.entity.Funcao;
import com.gestao.gestaopresente.infra.repository.FuncaoRepository;
import com.gestao.gestaopresente.presentation.dto.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/funcao")
@Slf4j
public class FuncaoController {
    public FuncaoRepository funcaoRepository;

    public FuncaoController(FuncaoRepository funcaoRepository) {
        this.funcaoRepository = funcaoRepository;
    }

    @PostMapping
    public ResponseEntity<Response> createFuncao(@RequestBody Funcao funcao) {
        log.info("Iniciando criação de nova função: {}", funcao.getNome());
        try {
            var funcaoCriada = funcaoRepository.save(funcao);
            log.info("Função criada com sucesso. ID: {}", funcaoCriada.getId());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new Response<>(funcaoCriada, "Função criada com sucesso"));
        } catch (Exception e) {
            log.error("Erro ao criar função", e);
            throw e;
        }
    }
}
