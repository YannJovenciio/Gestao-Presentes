package com.gestao.gestaopresente.presentation.controller.presente;

import com.gestao.gestaopresente.presentation.dto.Response;
import com.gestao.gestaopresente.service.presente.IPresenteService;
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
public class PresenteController {
    private final IPresenteService presenteService;

    public PresenteController(IPresenteService presenteService) {
        this.presenteService = presenteService;
    }

    @PostMapping
    public ResponseEntity<Response> createPresente(@RequestBody PresenteInput input, Authentication authentication) {

        if (authentication != null && authentication.isAuthenticated()) {
            authentication = SecurityContextHolder.getContext().getAuthentication();
            var email = authentication.getName();
            var response = presenteService.create(input, email);


            return ResponseEntity.status(HttpStatus.CREATED).body(new Response<PresenteResponse>(response, "Succesfull created presente"));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("Um erro ocorreu"));
    }
}
