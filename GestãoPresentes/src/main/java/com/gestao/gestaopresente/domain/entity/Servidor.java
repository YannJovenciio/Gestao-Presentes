package com.gestao.gestaopresente.domain.entity;

import com.gestao.gestaopresente.domain.e.Sexo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
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
}
