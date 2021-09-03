package com.gomes.daniel.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RecursoMalInseridoException extends NegocioException {

    public RecursoMalInseridoException() {
        super("As informações inseridas precisam ser revistas!");
    }

    public RecursoMalInseridoException(String e) {
        super(e);
    }
}
