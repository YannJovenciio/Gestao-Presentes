package com.gestao.gestaopresente.presentation.controller.funcao;

import com.gestao.gestaopresente.domain.entity.Funcao;
import com.gestao.gestaopresente.presentation.dto.Response;
import com.gestao.gestaopresente.service.funcao.IFuncaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
@Tag(name = "Função", description = "Endpoints para gerenciamento de funções dos servidores")
@SecurityRequirement(name = "bearer-jwt")
public class FuncaoController {
    private final IFuncaoService funcaoService;

    public FuncaoController(IFuncaoService funcaoService) {
        this.funcaoService = funcaoService;
    }

    @Operation(
            summary = "Criar função",
            description =
                    "Cria uma nova função no sistema. Funções são os cargos que podem ser atribuídos aos servidores")
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "201",
                        description = "Função criada com sucesso",
                        content =
                                @Content(
                                        mediaType = "application/json",
                                        schema = @Schema(implementation = Funcao.class))),
                @ApiResponse(
                        responseCode = "400",
                        description = "Dados inválidos",
                        content = @Content)
            })
    @PostMapping
    public ResponseEntity<Response<FuncaoResponse>> createFuncao(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                            description = "Dados da função a ser criada",
                            required = true)
                    @Valid
                    @RequestBody
                    FuncaoInput input) {
        log.info("Iniciando criação de nova função: {}", input.nome());
        var response = funcaoService.create(input);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new Response<>(response, "Função criada com sucesso"));
    }
}
