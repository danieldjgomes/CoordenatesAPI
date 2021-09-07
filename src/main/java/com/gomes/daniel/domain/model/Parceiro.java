package com.gomes.daniel.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.br.CNPJ;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Parceiro {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(nullable = false, unique = true)
    @CNPJ
    private String cnpj;

    @Column
    private String nome;

    @OneToMany
    @Column(nullable = true)
    @JoinColumn(name = "parceiro_id")
    private List<Destino> destino = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "parceiro_id")
    @JoinTable
    @Column(nullable = true)
    private List<Usuario> usuarios = new ArrayList<>();

}
