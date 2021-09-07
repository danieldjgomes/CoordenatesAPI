package com.gomes.daniel.domain.repository;

import com.gomes.daniel.domain.model.Parceiro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParceiroRepository extends JpaRepository<Parceiro, Long> {


    Parceiro findByCnpj(String cnpj);
}
