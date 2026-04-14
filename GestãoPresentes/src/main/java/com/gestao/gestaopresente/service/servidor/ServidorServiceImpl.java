package com.gestao.gestaopresente.service.servidor;

import com.gestao.gestaopresente.domain.entity.Servidor;
import com.gestao.gestaopresente.infra.repository.FuncaoRepository;
import com.gestao.gestaopresente.infra.repository.ServidorRepository;
import com.gestao.gestaopresente.presentation.controller.servidor.ServidorInput;
import com.gestao.gestaopresente.presentation.controller.servidor.ServidorResponse;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ServidorServiceImpl implements IServidorService {
    private final ServidorRepository servidorRepository;
    private final FuncaoRepository funcaoRepository;

    public ServidorServiceImpl(
            ServidorRepository servidorRepository, FuncaoRepository funcaoRepository) {
        this.servidorRepository = servidorRepository;
        this.funcaoRepository = funcaoRepository;
    }

    @Override
    public ServidorResponse getByEmail(String email) {
        log.info("Iniciando busca de servidor por email: {}", email);
        var servidor = servidorRepository.findByEmail(email);
        log.info("Servidor recuperado com sucesso. Email: {}, ID: {}", email, servidor.getId());
        return servidor.toResponse();
    }

    @Override
    public List<ServidorResponse> getAll() {
        log.info("Iniciando busca para todos servidores");
        var servidores = servidorRepository.findAll();
        log.info(" quantidade de servidores encontrados:{}", servidores.size());
        return servidores.stream()
                .<ServidorResponse>map(
                        s ->
                                new ServidorResponse(
                                        s.getId(),
                                        s.getFuncao(),
                                        s.getSexo(),
                                        s.getDataNasc(),
                                        s.getSalario(),
                                        s.getEmail(),
                                        s.getCpf(),
                                        s.getCpf()))
                .toList();
    }

    @Override
    public ServidorResponse create(ServidorInput input) {
        log.info(
                "Iniciando criação de novo servidor. Email: {}, Nome: {}",
                input.email(),
                input.nomeCompleto());

        var funcao =
                funcaoRepository
                        .findById(input.funcaoId())
                        .orElseThrow(
                                () ->
                                        new EntityNotFoundException(
                                                "Função com ID "
                                                        + input.funcaoId()
                                                        + " não encontrada"));

        var servidor =
                new Servidor(
                        funcao,
                        input.sexo(),
                        input.dataNasc(),
                        input.salario(),
                        input.email(),
                        input.cpf(),
                        input.nomeCompleto());
        servidorRepository.save(servidor);

        log.info(
                "Servidor persistido com sucesso. ID: {}, Email: {}",
                servidor.getId(),
                servidor.getEmail());
        return servidor.toResponse();
    }

    @Override
    public ServidorResponse update(Long id, ServidorInput input) {

        log.info(
                "Iniciando request para atualizar Servidor com ID: {} e dados: {}",
                id,
                input.toString());
        var servidorToUpdate = servidorRepository.getReferenceById(id);
        var funcao =
                funcaoRepository
                        .findById(input.funcaoId())
                        .orElseThrow(
                                () ->
                                        new EntityNotFoundException(
                                                "Função com ID "
                                                        + input.funcaoId()
                                                        + " não encontrada"));

        servidorToUpdate.setEmail(input.email());
        servidorToUpdate.setFuncao(funcao);
        servidorToUpdate.setSalario(input.salario());

        servidorRepository.save(servidorToUpdate);

        log.info(
                "Servidor atualizado com sucesso. ID: {}, Email: {}",
                servidorToUpdate.getId(),
                servidorToUpdate.getEmail());
        return servidorToUpdate.toResponse();
    }

    @Override
    public void delete(Long id) {

        if (!servidorRepository.existsById(id))
            throw new IllegalArgumentException("Nenhum servidor encontrado para esse id");
        servidorRepository.deleteById(id);
    }
}
