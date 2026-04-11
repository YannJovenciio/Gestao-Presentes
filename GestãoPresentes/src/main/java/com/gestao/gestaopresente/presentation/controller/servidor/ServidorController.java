package com.gestao.gestaopresente.presentation.controller.servidor;

import com.gestao.gestaopresente.presentation.dto.Response;
import com.gestao.gestaopresente.service.servidor.ServidorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/servidor")
public class ServidorController {
    private final ServidorService servidorService;

    public ServidorController(ServidorService servidorService) {
        this.servidorService = servidorService;
    }

    @GetMapping
    public ResponseEntity<Response> findByEmail(@RequestParam(required = true) String email) {
        log.info("Iniciando busca de servidor por email: {}", email);

        var servidor = servidorService.getServidorByEmail(email);
        log.info("Servidor encontrado com sucesso para o email: {}", email);
        return ResponseEntity.ok(new Response<>(servidor, "Succesfull found servidor by email"));

    }

    @PostMapping
    public ResponseEntity<Response> create(@RequestBody ServidorInput input) {
        log.info("Iniciando criação de novo servidor com dados: {}", input);

        var response = servidorService.createServidor(input);
        log.info("Servidor criado com sucesso: {}", response);
        return ResponseEntity.status(HttpStatus.CREATED).body(new Response(response, "Succesfull created servidor"));

    }
}
