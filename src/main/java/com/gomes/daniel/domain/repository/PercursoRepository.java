package com.gomes.daniel.domain.repository;

import com.gomes.daniel.domain.model.Percurso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PercursoRepository extends JpaRepository<Percurso, Long> {

}
