package com.gomes.daniel.domain.model;


public enum ModoPercurso {

    ANDANDO, DIRIGINDO, TRANSPORTE_PUBLICO;

    ModoPercurso() {
    }


    public static String toGoogleString(ModoPercurso modo) {
        return switch (modo){
            case ANDANDO -> "walking";
            case DIRIGINDO -> "driving";
            case TRANSPORTE_PUBLICO -> "transit";
        };

    }


}
