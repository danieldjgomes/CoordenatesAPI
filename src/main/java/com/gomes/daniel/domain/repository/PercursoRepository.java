package com.gomes.daniel.domain.repository;

import com.gomes.daniel.domain.model.Coordinate;
import com.gomes.daniel.domain.model.Percurso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface PercursoRepository extends JpaRepository<Percurso, Long> {

}
