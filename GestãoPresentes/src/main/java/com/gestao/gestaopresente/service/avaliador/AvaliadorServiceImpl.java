package com.gestao.gestaopresente.service.avaliador;

import com.gestao.gestaopresente.domain.entity.Avaliador;
import com.gestao.gestaopresente.infra.repository.AvaliadorRepository;
import com.gestao.gestaopresente.presentation.controller.avaliador.AvaliadorInput;
import com.gestao.gestaopresente.presentation.controller.avaliador.AvaliadorResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class AvaliadorServiceImpl implements IAvaliadorService {

    private final AvaliadorRepository repository;

    public AvaliadorServiceImpl(AvaliadorRepository repository) {
        this.repository = repository;
    }

    public AvaliadorResponse create(AvaliadorInput input) {
        log.info("Criando novo Avaliador: {}", input.nome());

        if (input.nome() == null || input.nome().isEmpty()) {
            log.error("Tentativa de criar Avaliador com dados inválidos");
            throw new IllegalArgumentException("Nome do Avaliador não pode ser nulo ou vazio");
        }

        var avaliador = new Avaliador(
                input.nome(),
                input.sexo(),
                input.endereco(),
                input.telefone(),
                input.email()
        );
        var avaliadorSalvo = repository.save(avaliador);
        log.info("Avaliador criado com sucesso. ID: {}", avaliadorSalvo.getId());

        return new AvaliadorResponse(
                avaliadorSalvo.getId(),
                avaliadorSalvo.getNome(),
                avaliadorSalvo.getSexo(),
                avaliadorSalvo.getEndereco(),
                avaliadorSalvo.getTelefone(),
                avaliadorSalvo.getEmail()
        );

    }

    @Override
    public void delete(Long id) {
        log.info("Deletando avaliador com ID: {}", id);

        if (!repository.existsById(id)) {
            log.warn("Tentativa de deletar avaliador inexistente. ID: {}", id);
            throw new IllegalArgumentException("Avaliador com ID " + id + " não encontrado");
        }
        repository.deleteById(id);
        log.info("Avaliador deletado com sucesso. ID: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public AvaliadorResponse getById(Long id) {
        log.info("Buscando avaliador com ID: {}", id);
        var avaliador = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Avaliador com ID " + id + " não encontrado"));

        return new AvaliadorResponse(
                avaliador.getId(),
                avaliador.getNome(),
                avaliador.getSexo(),
                avaliador.getEndereco(),
                avaliador.getTelefone(),
                avaliador.getEmail()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public AvaliadorResponse getByIdWithPresentes(Long id) {
        log.info("Buscando avaliador com ID e Presentes: {}", id);
        var avaliador = repository.findByIdWithPresentes(id)
                .orElseThrow(() -> new EntityNotFoundException("Avaliador com ID " + id + " não encontrado"));

        return new AvaliadorResponse(
                avaliador.getId(),
                avaliador.getNome(),
                avaliador.getSexo(),
                avaliador.getEndereco(),
                avaliador.getTelefone(),
                avaliador.getEmail(),
                avaliador.getPresentes()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<AvaliadorResponse> getAll(int page, int size) {
        log.info("Buscando todos os avaliadores com paginação");
        var pageable = PageRequest.of(page, size);
        var avaliadores = repository.findAll(pageable);
        return avaliadores.stream()
                .map(a -> new AvaliadorResponse(
                        a.getId(),
                        a.getNome(),
                        a.getSexo(),
                        a.getEndereco(),
                        a.getTelefone(),
                        a.getEmail()
                ))
                .toList();
    }

    // ✅ SOBRECARGA: Lista com Presentes (evita N+1)
    @Transactional(readOnly = true)
    public List<AvaliadorResponse> getAllWithPresentes(int page, int size) {
        log.info("Buscando todos os avaliadores com Presentes");
        var avaliadores = repository.findAllWithPresentes();

        return avaliadores.stream()
                .map(a -> new AvaliadorResponse(
                        a.getId(),
                        a.getNome(),
                        a.getSexo(),
                        a.getEndereco(),
                        a.getTelefone(),
                        a.getEmail(),
                        a.getPresentes()
                ))
                .toList();
    }
}
