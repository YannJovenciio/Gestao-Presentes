package com.gestao.gestaopresente.presentation.controller.servidor;

import com.gestao.gestaopresente.presentation.dto.Response;
import com.gestao.gestaopresente.presentation.validation.AdvancedInfo;
import com.gestao.gestaopresente.presentation.validation.BasicInfo;
import com.gestao.gestaopresente.service.servidor.IServidorService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/servidor")
public class ServidorController {
    private final IServidorService servidorService;

    public ServidorController(IServidorService servidorService) {
        this.servidorService = servidorService;
    }

    @GetMapping("/{email}")
    public ResponseEntity<Response<ServidorResponse>> findByEmail(@PathVariable String email) {
        log.info("Iniciando busca de servidor por email: {}", email);

        var servidor = servidorService.getByEmail(email);
        log.info("Servidor encontrado com sucesso para o email: {}", email);
        return ResponseEntity.ok(new Response<>(servidor, "Succesfull found servidor by email"));
    }

    @PostMapping
    public ResponseEntity<Response<ServidorResponse>> create(
            @RequestBody @Validated(BasicInfo.class) ServidorInput input) {
        log.info("Iniciando criação de novo servidor com dados: {}", input);

        var response = servidorService.create(input);
        log.info("Servidor criado com sucesso: {}", response);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new Response<>(response, "Succesfull created servidor"));
    }

    @GetMapping
    public ResponseEntity<Response<List<ServidorResponse>>> getAll() {
        var response = servidorService.getAll();
        return ResponseEntity.ok(new Response<>(response, "Succesful found all servidores"));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Response<ServidorResponse>> update(
            @RequestBody @Validated(AdvancedInfo.class) ServidorInput input,
            @PathVariable Long id) {
        log.info("Iniciando atualização de servidor com ID: {}", id);
        var response = servidorService.update(id, input);
        log.info("Servidor atualizado com sucesso: {}", id);
        return ResponseEntity.ok(new Response<>(response, "Succesfull updated servidor"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<String>> delete(@PathVariable Long id) {
        log.info("Iniciando exclusão de servidor com ID: {}", id);
        servidorService.delete(id);
        log.info("Servidor excluído com sucesso: {}", id);
        return ResponseEntity.ok(new Response<>("", "Succesfull deleted servidor"));
    }
}
