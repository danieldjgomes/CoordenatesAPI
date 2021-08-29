package com.gomes.daniel.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.List;

@Data
@Entity
@Table
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
public class Percurso {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @NonNull
    @OneToMany(cascade=CascadeType.ALL)
    private List<Coordinate> pontos;

    @NonNull
    @Enumerated(EnumType.STRING)
    private ModoPercurso modoPercurso;

    @ManyToOne
    @NonNull
    @JsonIgnore
    private Usuario usuario;

    @NonNull
    private String enderecoOrigem;

    @NonNull
    private String enderecoDestino;

    @NonNull
    private LocalTime horarioOrigem;

    @NonNull
    private LocalTime horarioDestino;


    public Percurso(@NonNull List<Coordinate> pontos, @NonNull ModoPercurso modoPercurso, @NonNull String enderecoOrigem, @NonNull String enderecoDestino, Usuario usuario, LocalTime horarioOrigem, LocalTime horarioDestino) {
        this.pontos = pontos;
        this.modoPercurso = modoPercurso;
        this.enderecoOrigem = enderecoOrigem;
        this.enderecoDestino = enderecoDestino;
        this.usuario = usuario;
        this.horarioOrigem = horarioOrigem;
        this.horarioDestino = horarioDestino;
    }
}
