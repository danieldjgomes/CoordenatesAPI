package com.gomes.daniel.domain.model;


import com.gomes.daniel.domain.exception.SentidoPercursoInvalidoException;


public enum SentidoPercurso {

    CASA, DESTINO;

    SentidoPercurso() {
    }

    public static SentidoPercurso stringToPercurso(String modo) throws SentidoPercursoInvalidoException {

        return switch (modo) {
            case "casa" -> SentidoPercurso.CASA;
            case "destino" -> SentidoPercurso.DESTINO;
            default -> throw new SentidoPercursoInvalidoException("Erro! Modo de Percurso inserido é inválido.");
        };
    }


}
