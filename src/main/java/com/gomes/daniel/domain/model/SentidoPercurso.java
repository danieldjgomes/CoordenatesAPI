package com.gomes.daniel.domain.model;

import com.gomes.daniel.domain.exception.ModoPercursoInvalidoException;
import com.gomes.daniel.domain.exception.SentidoPercursoException;


public enum SentidoPercurso {

    CASA, DESTINO;

    SentidoPercurso() {
    }

    public static SentidoPercurso stringToPercurso(String modo) throws SentidoPercursoException {

        return switch (modo) {
            case "casa" -> SentidoPercurso.CASA;
            case "destino" -> SentidoPercurso.DESTINO;
            default -> throw new SentidoPercursoException("Erro! Modo de Percurso inserido é inválido.");
        };
    }


}
