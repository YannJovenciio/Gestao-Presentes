package com.gestao.gestaopresente.domain.entity;

import com.gestao.gestaopresente.domain.e.Sexo;
import com.gestao.gestaopresente.domain.entity.many2many.Presente;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Avaliador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(mappedBy = "avaliador")
    private List<Presente> presentes = new ArrayList<>();
    private String nome;
    @Enumerated(EnumType.STRING)
    private Sexo sexo;
    private String endereco;
    private String telefone;
    private String email;

    public Avaliador(String Nome, Sexo Sexo, String Endereco, String Telefone, String Email) {
        nome = Nome;
        sexo = Sexo;
        endereco = Endereco;
        telefone = Telefone;
        email = Email;
    }
}
