package com.gomes.daniel.domain.exception;

public class NegocioException extends Exception {
    public NegocioException(String e){
        super(e);
    }
    public NegocioException() {
        super("Ocorreu um erro de negócio!");
    }
}
