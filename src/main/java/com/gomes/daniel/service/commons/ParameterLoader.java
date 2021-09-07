package com.gomes.daniel.service.commons;

import com.gomes.daniel.domain.model.ParametroServico;
import com.gomes.daniel.domain.repository.ParametroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.Query;

import javax.persistence.*;

@Service
public class ParameterLoader {


    @Autowired
    private ParametroRepository parametroRepository;

    public String loadById(Long id) {
        return parametroRepository.findById(id).get().getValor().toString();
    }
}
