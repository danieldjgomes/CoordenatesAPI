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
    @JoinTable(name = "usuario_percurso")
    @JsonIgnore
    private List<Percurso> percursos = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "_tipoUsuario", nullable = true)
    private ModoLocomocao modoLocomocao;

    @Column(name = "_toleranciaDistancia", nullable = true)
    private Long toleranciaDistancia;

    @Column(name = "_toleranciaTempo", nullable = true)
    private int toleranciaTempo;

}
