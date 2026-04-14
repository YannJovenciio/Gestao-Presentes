package com.gestao.gestaopresente.domain.entity;

import com.gestao.gestaopresente.domain.e.Continente;
import com.gestao.gestaopresente.domain.entity.many2many.Presente;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Pais {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "paisId")
    private List<Presente> presentes = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Continente continente;

    private String nome;
}
