package com.gomes.daniel.api.exceptionHandler;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class RespostaException {

    private LocalDateTime dataResposta;
    private String mensagem;

}
