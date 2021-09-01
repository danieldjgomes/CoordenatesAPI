package com.gomes.daniel.domain.model;

import com.gomes.daniel.domain.exception.ModoPercursoInvalidoException;
import lombok.Data;


public enum ModoPercurso {

    ANDANDO, DIRIGINDO, TRANSPORTE_PUBLICO;

    ModoPercurso() {
    }

    public static ModoPercurso stringToModo(String modo) throws ModoPercursoInvalidoException {

        return switch (modo) {
            case "walking" -> ModoPercurso.ANDANDO;
            case "driving" -> ModoPercurso.DIRIGINDO;
            case "transit" -> ModoPercurso.TRANSPORTE_PUBLICO;
            default -> throw new ModoPercursoInvalidoException("Erro! Modo de Percurso inserido é inválido.");
        };
    }


}
