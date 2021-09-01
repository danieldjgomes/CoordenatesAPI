package com.gomes.daniel.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.List;

@Data
@Entity
@Table
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
public class Percurso implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @NonNull
    @OneToMany(cascade=CascadeType.ALL)
    private List<Coordinate> pontos;

    @NonNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ModoPercurso modoPercurso;

    @NonNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SentidoPercurso sentidoPercurso;

    @ManyToOne
    @NonNull
    @JsonIgnore
    private Usuario usuario;

    @NonNull
    @Column(nullable = false)
    private String enderecoOrigem;

    @NonNull
    @Column(nullable = false)
    private String enderecoDestino;

    @NonNull
    @Column(nullable = false)
    private LocalTime horario;


    public Percurso(@NonNull List<Coordinate> pontos, @NonNull ModoPercurso modoPercurso, @NonNull String enderecoOrigem, @NonNull String enderecoDestino, Usuario usuario, LocalTime horarioOrigem, SentidoPercurso sentidoPercurso) {
        this.pontos = pontos;
        this.modoPercurso = modoPercurso;
        this.enderecoOrigem = enderecoOrigem;
        this.enderecoDestino = enderecoDestino;
        this.usuario = usuario;
        this.horario = horarioOrigem;
        this.sentidoPercurso = sentidoPercurso;
    }
}
