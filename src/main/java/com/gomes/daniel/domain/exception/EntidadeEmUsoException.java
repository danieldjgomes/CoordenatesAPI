package com.gomes.daniel.domain.exception;

public class EntidadeEmUsoException extends NegocioException {
    public EntidadeEmUsoException() {
        super("A entidade está em uso, verifique a documentação para solucionar suas duvidas");
    }
}
