package com.gestao.gestaopresente.domain.entity;

import com.gestao.gestaopresente.domain.e.Sexo;
import com.gestao.gestaopresente.presentation.controller.servidor.ServidorResponse;
import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Servidor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "funcao_id", referencedColumnName = "id", nullable = false)
    private Funcao funcao;

    @Enumerated(EnumType.STRING)
    private Sexo sexo;

    private LocalDate dataNasc;
    private Double salario;
    private String email;
    private String cpf;
    private String nomeCompleto;

    public Servidor(
            Funcao Funcao,
            Sexo Sexo,
            LocalDate DataNasc,
            Double Salario,
            String Email,
            String Cpf,
            String NomeCompleto) {
        funcao = Funcao;
        sexo = Sexo;
        dataNasc = DataNasc;
        salario = Salario;
        email = Email;
        cpf = Cpf;
        nomeCompleto = NomeCompleto;
    }

    public ServidorResponse toResponse() {
        return new ServidorResponse(id, funcao, sexo, dataNasc, salario, email, cpf, nomeCompleto);
    }
}
