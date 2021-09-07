package com.gomes.daniel.domain.exception;

import java.sql.SQLIntegrityConstraintViolationException;

//@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EntidadeDuplicadaException extends NegocioException {

    public EntidadeDuplicadaException() {
        super("As informações inseridas já estão presentes no banco de dados!");
    }

    public EntidadeDuplicadaException(String e) {
        super(e);
    }


}
