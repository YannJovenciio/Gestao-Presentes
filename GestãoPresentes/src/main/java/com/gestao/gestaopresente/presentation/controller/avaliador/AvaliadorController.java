package com.gestao.gestaopresente.presentation.controller.avaliador;

import com.gestao.gestaopresente.presentation.dto.Response;
import com.gestao.gestaopresente.service.avaliador.IAvaliadorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/avaliadores")
@Tag(name = "Avaliadores", description = "Endpoints para gerenciamento de avaliadores")
@SecurityRequirement(name = "bearer-jwt")
public class AvaliadorController {

    private final IAvaliadorService avaliadorService;

    @GetMapping
    @Operation(
            summary = "Listar avaliadores",
            description =
                    "Retorna lista paginada de avaliadores. Pode incluir ou não os presentes associados")
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Lista retornada com sucesso",
                        content = @Content(mediaType = "application/json"))
            })
    public ResponseEntity<Response<List<AvaliadorResponse>>> listAll(
            @Parameter(description = "Número da página (começando do 0)", example = "0")
                    @RequestParam(defaultValue = "0")
                    int page,
            @Parameter(description = "Quantidade de registros por página", example = "10")
                    @RequestParam(defaultValue = "10")
                    int size,
            @Parameter(
                            description = "Se deve incluir presentes associados (atenção ao N+1)",
                            example = "false")
                    @RequestParam(defaultValue = "false")
                    boolean isWithPresente) {
        log.info(
                "Listando avaliadores: página={}, tamanho={}, comPresentes={}",
                page,
                size,
                isWithPresente);

        List<AvaliadorResponse> avaliadores =
                isWithPresente
                        ? avaliadorService.getAllWithPresentes(page, size)
                        : avaliadorService.getAll(page, size);

        var response =
                new Response<>(
                        avaliadores, "Avaliadores recuperados com sucesso", HttpStatus.OK.value());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar avaliador por ID",
            description =
                    "Retorna os dados de um avaliador específico. Pode incluir ou não os presentes associados")
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Avaliador encontrado com sucesso",
                        content =
                                @Content(
                                        mediaType = "application/json",
                                        schema =
                                                @Schema(implementation = AvaliadorResponse.class))),
                @ApiResponse(
                        responseCode = "404",
                        description = "Avaliador não encontrado",
                        content = @Content)
            })
    public ResponseEntity<Response<AvaliadorResponse>> getAvaliadorById(
            @Parameter(description = "ID do avaliador", required = true, example = "1")
                    @PathVariable
                    Long id,
            @Parameter(
                            description = "Se deve incluir presentes associados (atenção ao N+1)",
                            example = "false")
                    @RequestParam(defaultValue = "false")
                    boolean isWithPresente) {

        log.info("Buscando avaliador: id={}, comPresentes={}", id, isWithPresente);

        AvaliadorResponse avaliador =
                isWithPresente
                        ? avaliadorService.getByIdWithPresentes(id)
                        : avaliadorService.getById(id);

        var response =
                new Response<>(
                        avaliador, "Avaliador recuperado com sucesso", HttpStatus.OK.value());

        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(
            summary = "Criar avaliador",
            description = "Cria um novo avaliador no sistema com os dados fornecidos")
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "201",
                        description = "Avaliador criado com sucesso",
                        content =
                                @Content(
                                        mediaType = "application/json",
                                        schema =
                                                @Schema(implementation = AvaliadorResponse.class))),
                @ApiResponse(
                        responseCode = "400",
                        description = "Dados inválidos",
                        content = @Content)
            })
    public ResponseEntity<Response<AvaliadorResponse>> createAvaliador(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                            description = "Dados do avaliador a ser criado",
                            required = true)
                    @Valid
                    @RequestBody
                    AvaliadorInput input) {

        log.info("Criando novo avaliador: {}", input);
        var avaliador = avaliadorService.create(input);

        var response =
                new Response<>(
                        avaliador, "Avaliador criado com sucesso", HttpStatus.CREATED.value());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
