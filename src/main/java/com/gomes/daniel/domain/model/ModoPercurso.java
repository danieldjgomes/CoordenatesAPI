package com.gomes.daniel.domain.model;

import com.gomes.daniel.domain.exception.ModoPercursoInvalidoException;
import lombok.Data;


public enum ModoPercurso {

    ANDANDO, DIRIGINDO, TRANSPORTE_PUBLICO;

    ModoPercurso() {
    }

    public static ModoPercurso stringToModo(String modo) throws ModoPercursoInvalidoException {

        switch (modo){
            case "walking":
                return ModoPercurso.ANDANDO;
            case "driving":
                return ModoPercurso.DIRIGINDO;
            case "transit":
                return ModoPercurso.TRANSPORTE_PUBLICO;

        }
        throw new ModoPercursoInvalidoException("Erro! Modo de Percurso inserido é inválido.");
    }


}
