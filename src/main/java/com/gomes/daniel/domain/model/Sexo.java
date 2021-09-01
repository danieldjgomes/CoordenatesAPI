package com.gomes.daniel.domain.model;

import com.gomes.daniel.domain.exception.SentidoPercursoException;
import com.gomes.daniel.domain.exception.SexoInseridoException;

public enum Sexo {

    MASC,FEM,OUTRO;

    Sexo(){}

//    public static Sexo stringToSexo(String sexo) throws SexoInseridoException {
//
//        return switch (sexo) {
//            case "masc" -> Sexo.MASC;
//            case "fem" -> Sexo.FEM;
//            case "outro" -> Sexo.OUTRO;
//            default -> throw new SexoInseridoException("Erro! O sexo inserido é inválido.");
//        };
//    }


}
