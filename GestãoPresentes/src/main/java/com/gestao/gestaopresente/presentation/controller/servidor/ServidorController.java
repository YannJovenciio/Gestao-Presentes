package com.gestao.gestaopresente.presentation.controller.servidor;

import com.gestao.gestaopresente.presentation.dto.Response;
import com.gestao.gestaopresente.presentation.validation.AdvancedInfo;
import com.gestao.gestaopresente.presentation.validation.BasicInfo;
import com.gestao.gestaopresente.service.servidor.IServidorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/servidor")
@Tag(name = "Servidor", description = "Endpoints para gerenciamento de servidores")
@SecurityRequirement(name = "bearer-jwt")
public class ServidorController {
    private final IServidorService servidorService;

    public ServidorController(IServidorService servidorService) {
        this.servidorService = servidorService;
    }

    @Operation(
            summary = "Buscar servidor por email",
            description = "Retorna os dados de um servidor específico através do seu email")
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Servidor encontrado com sucesso",
                        content =
                                @Content(
                                        mediaType = "application/json",
                                        schema = @Schema(implementation = ServidorResponse.class))),
                @ApiResponse(
                        responseCode = "404",
                        description = "Servidor não encontrado",
                        content = @Content)
            })
    @GetMapping("/{email}")
    public ResponseEntity<Response<ServidorResponse>> findByEmail(
            @Parameter(
                            description = "Email do servidor",
                            required = true,
                            example = "servidor@email.com")
                    @PathVariable
                    String email) {
        log.info("Iniciando busca de servidor por email: {}", email);

        var servidor = servidorService.getByEmail(email);
        log.info("Servidor encontrado com sucesso para o email: {}", email);
        return ResponseEntity.ok(new Response<>(servidor, "Succesfull found servidor by email"));
    }

    @Operation(
            summary = "Criar novo servidor",
            description =
                    "Cria um novo servidor no sistema. Valida campos obrigatórios para criação (BasicInfo)")
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "201",
                        description = "Servidor criado com sucesso",
                        content =
                                @Content(
                                        mediaType = "application/json",
                                        schema = @Schema(implementation = ServidorResponse.class))),
                @ApiResponse(
                        responseCode = "400",
                        description = "Dados inválidos",
                        content = @Content)
            })
    @PostMapping
    public ResponseEntity<Response<ServidorResponse>> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                            description = "Dados do servidor a ser criado",
                            required = true)
                    @RequestBody
                    @Validated(BasicInfo.class)
                    ServidorInput input) {
        log.info("Iniciando criação de novo servidor com dados: {}", input);

        var response = servidorService.create(input);
        log.info("Servidor criado com sucesso: {}", response);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new Response<>(response, "Succesfull created servidor"));
    }

    @Operation(
            summary = "Listar todos os servidores",
            description = "Retorna uma lista com todos os servidores cadastrados no sistema")
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Lista retornada com sucesso",
                        content = @Content(mediaType = "application/json"))
            })
    @GetMapping
    public ResponseEntity<Response<List<ServidorResponse>>> getAll() {
        var response = servidorService.getAll();
        return ResponseEntity.ok(new Response<>(response, "Succesful found all servidores"));
    }

    @Operation(
            summary = "Atualizar servidor",
            description =
                    "Atualiza os dados de um servidor existente. O ID vem do path. Valida com AdvancedInfo")
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Servidor atualizado com sucesso",
                        content =
                                @Content(
                                        mediaType = "application/json",
                                        schema = @Schema(implementation = ServidorResponse.class))),
                @ApiResponse(
                        responseCode = "400",
                        description = "Dados inválidos",
                        content = @Content),
                @ApiResponse(
                        responseCode = "404",
                        description = "Servidor não encontrado",
                        content = @Content)
            })
    @PatchMapping("/{id}")
    public ResponseEntity<Response<ServidorResponse>> update(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                            description = "Dados atualizados do servidor",
                            required = true)
                    @RequestBody
                    @Validated(AdvancedInfo.class)
                    ServidorInput input,
            @Parameter(
                            description = "ID do servidor a ser atualizado",
                            required = true,
                            example = "1")
                    @PathVariable
                    Long id) {
        log.info("Iniciando atualização de servidor com ID: {}", id);
        var response = servidorService.update(id, input);
        log.info("Servidor atualizado com sucesso: {}", id);
        return ResponseEntity.ok(new Response<>(response, "Succesfull updated servidor"));
    }

    @Operation(
            summary = "Deletar servidor",
            description = "Remove um servidor do sistema pelo seu ID")
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Servidor deletado com sucesso",
                        content = @Content(mediaType = "application/json")),
                @ApiResponse(
                        responseCode = "404",
                        description = "Servidor não encontrado",
                        content = @Content)
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<Response<String>> delete(
            @Parameter(
                            description = "ID do servidor a ser deletado",
                            required = true,
                            example = "1")
                    @PathVariable
                    Long id) {
        log.info("Iniciando exclusão de servidor com ID: {}", id);
        servidorService.delete(id);
        log.info("Servidor excluído com sucesso: {}", id);
        return ResponseEntity.ok(new Response<>("", "Succesfull deleted servidor"));
    }
}
