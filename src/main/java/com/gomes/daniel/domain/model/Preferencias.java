package com.gomes.daniel.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Embeddable
@Data
public class Preferencias implements Serializable {

    @OneToMany(cascade= CascadeType.ALL)
    @OrderColumn(name = "_percursos")
    @JsonIgnore
    private List<Percurso> percursos = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "_tipoUsuario")
    private ModoLocomocao modoLocomocao;

    @Column(name = "_toleranciaDistancia")
    private Long toleranciaDistancia;

    @Column(name = "_toleranciaTempo")
    private int toleranciaTempo;

}
