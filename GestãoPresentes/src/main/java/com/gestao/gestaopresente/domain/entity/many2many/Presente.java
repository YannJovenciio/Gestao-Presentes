package com.gestao.gestaopresente.domain.entity.many2many;

import com.gestao.gestaopresente.domain.entity.Avaliador;
import com.gestao.gestaopresente.domain.entity.Pais;
import com.gestao.gestaopresente.domain.entity.Servidor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class Presente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "pais_id")
    private Pais paisId;
    @ManyToOne
    @JoinColumn(name = "avaliador_id")
    private Avaliador avaliador;
    @ManyToOne
    @JoinColumn(name = "servidor_id", nullable = false)
    private Servidor servidor;
    private LocalDate dataEntrega;
    private String observacao;
    private double valor;

    public Presente(Servidor Servidor, LocalDate DataEntrega, String Observacao, double Valor) {
        servidor = Servidor;
        dataEntrega = DataEntrega;
        observacao = Observacao;
        valor = Valor;
    }
}