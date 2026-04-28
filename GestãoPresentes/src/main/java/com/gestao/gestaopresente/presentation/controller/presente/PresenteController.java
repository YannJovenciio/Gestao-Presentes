package com.gestao.gestaopresente.presentation.controller.presente;

import com.gestao.gestaopresente.presentation.dto.Response;
import com.gestao.gestaopresente.service.presente.IPresenteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/presente")
@Slf4j
@Tag(name = "Presente", description = "Endpoints para gerenciamento de presentes")
@SecurityRequirement(name = "bearer-jwt")
public class PresenteController {
    private final IPresenteService presenteService;

    public PresenteController(IPresenteService presenteService) {
        this.presenteService = presenteService;
    }

    @Operation(
            summary = "Criar presente",
            description =
                    "Cria um novo presente vinculado ao usuário autenticado (servidor). O email do servidor é extraído automaticamente do token JWT")
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "201",
                        description = "Presente criado com sucesso",
                        content =
                                @Content(
                                        mediaType = "application/json",
                                        schema = @Schema(implementation = PresenteResponse.class))),
                @ApiResponse(
                        responseCode = "400",
                        description = "Dados inválidos ou erro na criação",
                        content = @Content),
                @ApiResponse(
                        responseCode = "401",
                        description = "Usuário não autenticado",
                        content = @Content)
            })
    @PostMapping
    public ResponseEntity<Response> createPresente(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                            description = "Dados do presente a ser criado",
                            required = true)
                    @RequestBody
                    PresenteInput input,
            Authentication authentication) {

        if (authentication != null && authentication.isAuthenticated()) {
            authentication = SecurityContextHolder.getContext().getAuthentication();
            var email = authentication.getName();
            var response = presenteService.create(input, email);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new Response<PresenteResponse>(response, "Succesfull created presente"));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("Um erro ocorreu"));
    }
}
