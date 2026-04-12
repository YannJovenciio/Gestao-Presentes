package com.gestao.gestaopresente.presentation.controller.avaliador;

import com.gestao.gestaopresente.presentation.dto.Response;
import com.gestao.gestaopresente.service.avaliador.IAvaliadorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/avaliadores")
@Tag(name = "Avaliadores", description = "Gerenciamento de Avaliadores")
public class AvaliadorController {

    private final IAvaliadorService avaliadorService;

    @GetMapping
    @Operation(summary = "Listar avaliadores", description = "Retorna lista paginada de avaliadores")
    public ResponseEntity<Response<List<AvaliadorResponse>>> listAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "false") boolean isWithPresente
    ) {
        log.info("Listando avaliadores: página={}, tamanho={}, comPresentes={}",
                page, size, isWithPresente);

        List<AvaliadorResponse> avaliadores = isWithPresente
                ? avaliadorService.getAllWithPresentes(page, size)
                : avaliadorService.getAll(page, size);

        var response = new Response<>(
                avaliadores,
                "Avaliadores recuperados com sucesso",
                HttpStatus.OK.value()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar avaliador", description = "Retorna um avaliador específico por ID")
    public ResponseEntity<Response<AvaliadorResponse>> getAvaliadorById(
            @PathVariable Long id,
            @RequestParam(defaultValue = "false") boolean isWithPresente) {

        log.info("Buscando avaliador: id={}, comPresentes={}", id, isWithPresente);

        AvaliadorResponse avaliador = isWithPresente
                ? avaliadorService.getByIdWithPresentes(id)
                : avaliadorService.getById(id);

        var response = new Response<>(
                avaliador,
                "Avaliador recuperado com sucesso",
                HttpStatus.OK.value()
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(summary = "Criar avaliador", description = "Cria um novo avaliador no sistema")
    public ResponseEntity<Response<AvaliadorResponse>> createAvaliador(
            @Valid @RequestBody AvaliadorInput input) {

        log.info("Criando novo avaliador: {}", input);
        var avaliador = avaliadorService.create(input);

        var response = new Response<>(
                avaliador,
                "Avaliador criado com sucesso",
                HttpStatus.CREATED.value()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}


