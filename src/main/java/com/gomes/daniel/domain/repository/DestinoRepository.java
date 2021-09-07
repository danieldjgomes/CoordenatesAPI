package com.gomes.daniel.domain.repository;

import com.gomes.daniel.domain.model.Destino;
import com.gomes.daniel.domain.model.Parceiro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DestinoRepository extends JpaRepository<Destino, Long> {
}
