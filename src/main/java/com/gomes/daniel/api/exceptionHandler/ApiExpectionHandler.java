package com.gomes.daniel.api.exceptionHandler;

import com.gomes.daniel.domain.exception.*;
import org.springframework.beans.factory.parsing.Problem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;
import java.time.LocalTime;


@ControllerAdvice
public class ApiExpectionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

        if (body == null) {
            body = RespostaException.builder()
                    .dataResposta(LocalDateTime.now())
                    .mensagem(status.getReasonPhrase())
                    .build();
        } else if (body instanceof String) {
            body = RespostaException.builder()
                    .dataResposta(LocalDateTime.now())
                    .mensagem((String) body)
                    .build();
        }

            return super.handleExceptionInternal(ex, body, headers, status, request);
    }
    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<?> trataEntidadeNaoEncontradaException  (EntidadeNaoEncontradaException ex, WebRequest webRequest){
        return handleExceptionInternal(ex,ex.getMessage(),new HttpHeaders(),HttpStatus.NOT_FOUND, webRequest);
    }

    @ExceptionHandler(RecursoMalInseridoException.class)
    public ResponseEntity<?> trataRecursoMalInseridoException  (RecursoMalInseridoException ex, WebRequest webRequest){
        return handleExceptionInternal(ex,ex.getMessage(),new HttpHeaders(),HttpStatus.BAD_REQUEST, webRequest);
    }

    @ExceptionHandler(EntidadeEmUsoException.class)
    public ResponseEntity<?> trataEntidadeEmUsoException  (EntidadeEmUsoException ex, WebRequest webRequest){
        return handleExceptionInternal(ex,ex.getMessage(),new HttpHeaders(),HttpStatus.CONFLICT, webRequest);
    }

    @ExceptionHandler(EntidadeDuplicadaException.class)
    public ResponseEntity<?> trataEntidadeDuplicadaException  (EntidadeDuplicadaException ex, WebRequest webRequest){
        return handleExceptionInternal(ex,ex.getMessage(),new HttpHeaders(),HttpStatus.CONFLICT, webRequest);
    }

    //SQLIntegrityConstraintViolationException




}
