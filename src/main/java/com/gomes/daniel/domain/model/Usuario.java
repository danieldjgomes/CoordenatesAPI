package com.gomes.daniel.domain.model;


import lombok.*;
import org.hibernate.validator.constraints.Email;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.Pattern;

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

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String sobrenome;

    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @Pattern(regexp = "\\d{11}")
    @Column(nullable = false, unique = true)
    private String telefone;

    @Column(nullable = false)
    private Sexo sexo;

    @Embedded
    private Preferencias preferencias;

}
