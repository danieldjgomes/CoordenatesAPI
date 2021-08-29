package com.gomes.daniel.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.validator.constraints.Email;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Data
@Entity
@Table
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
public class Usuario implements Serializable {

	@Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column
    private Long id;
	
	@Column
    private String nome;

    @Email
    @Column
    private String email;

    @OneToMany(cascade= CascadeType.ALL)
    @OrderColumn
    @JsonIgnore

    private List<Percurso> percursos = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @NonNull
    @Column
    private TipoUsuario tipoUsuario;

    @NonNull
    @Column
    private Long toleranciaDistancia;

    @NonNull
    @Column
    private LocalTime toleranciaTempo;


}
