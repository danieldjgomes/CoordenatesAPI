package com.gomes.daniel.domain.model;

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
    @JoinTable
    private List<Coordenada> pontos;

    @NonNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ModoPercurso modoPercurso;

    @NonNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SentidoPercurso sentidoPercurso;

    @NonNull
    @Column(nullable = false)
    private String enderecoCasa;

    @ManyToOne
    @JoinColumn(name = "destino_id", nullable = false)
    private Destino destino;

    @NonNull
    @Column(nullable = false)
    private LocalTime horario;

    public Percurso(@NonNull List<Coordenada> pontos, @NonNull ModoPercurso modoPercurso, @NonNull SentidoPercurso sentidoPercurso, @NonNull String enderecoCasa, @NonNull Destino destino, @NonNull LocalTime horario) {
        this.pontos = pontos;
        this.modoPercurso = modoPercurso;
        this.sentidoPercurso = sentidoPercurso;
        this.enderecoCasa = enderecoCasa;
        this.destino = destino;
        this.horario = horario;
    }
}
