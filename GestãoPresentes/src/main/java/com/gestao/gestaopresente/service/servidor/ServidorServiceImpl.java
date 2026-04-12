package com.gestao.gestaopresente.service.servidor;

import com.gestao.gestaopresente.domain.entity.Servidor;
import com.gestao.gestaopresente.infra.repository.FuncaoRepository;
import com.gestao.gestaopresente.infra.repository.ServidorRepository;
import com.gestao.gestaopresente.presentation.controller.servidor.ServidorInput;
import com.gestao.gestaopresente.presentation.controller.servidor.ServidorResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ServidorServiceImpl implements IServidorService {
    private final ServidorRepository servidorRepository;
    private final FuncaoRepository funcaoRepository;

    public ServidorServiceImpl(ServidorRepository servidorRepository, FuncaoRepository funcaoRepository) {
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
        return servidores.stream().<ServidorResponse>map(s -> new ServidorResponse(s.getId(), s.getFuncao(), s.getSexo(), s.getDataNasc(), s.getSalario(), s.getEmail(), s.getCpf(), s.getCpf())).toList();
    }

    @Override
    public ServidorResponse create(ServidorInput input) {
        log.info("Iniciando criação de novo servidor. Email: {}, Nome: {}", input.email(), input.nomeCompleto());

        var funcao = funcaoRepository.findById(input.funcaoId())
                .orElseThrow(() -> new EntityNotFoundException("Função com ID " + input.funcaoId() + " não encontrada"));

        var servidor = new Servidor(funcao, input.sexo(), input.dataNasc(), input.salario(), input.email(), input.cpf(), input.nomeCompleto());
        servidorRepository.save(servidor);

        log.info("Servidor persistido com sucesso. ID: {}, Email: {}", servidor.getId(), servidor.getEmail());
        return servidor.toResponse();
    }

    @Override
    public ServidorResponse update(UpdateServidorDto input, Long id) {

        var check = input.Check();
        log.info("Iniciando request para para atualizar Servidor com dados:{}", check.toString());
        var servidorToUpdate = servidorRepository.getById(id);

        if (check.updateEmail())
            servidorToUpdate.setEmail(input.email());

        if (check.updateFuncao())
            servidorToUpdate.setFuncao(input.funcao());

        if (check.updateSalario())
            servidorToUpdate.setSalario(input.salario());

        servidorRepository.save(servidorToUpdate);

        return servidorToUpdate.toResponse();
    }

    @Override
    public void delete(Long id) {

        if (!servidorRepository.existsById(id))
            throw new IllegalArgumentException("Nenhum servidor encontrado para esse id");
        servidorRepository.deleteById(id);
    }
}



