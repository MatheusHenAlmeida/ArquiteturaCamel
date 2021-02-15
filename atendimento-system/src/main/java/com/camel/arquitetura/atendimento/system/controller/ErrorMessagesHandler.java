package com.camel.arquitetura.atendimento.system.controller;

import com.camel.arquitetura.atendimento.system.exceptions.AtendenteNotFoundException;
import com.camel.arquitetura.atendimento.system.exceptions.PrestadorNotFoundException;
import com.camel.arquitetura.atendimento.system.model.dto.ErrorMessageDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

import static com.camel.arquitetura.atendimento.system.constants.ErrorsCodes.*;

@ControllerAdvice
public class ErrorMessagesHandler {

    @ExceptionHandler(AtendenteNotFoundException.class)
    public ResponseEntity<ErrorMessageDTO> atendenteNaoEncontrado(AtendenteNotFoundException ex) {
        ErrorMessageDTO errorMessageDTO = buildErrorMessage(ex, ATENDENTE_NOT_FOUND);

        return new ResponseEntity(errorMessageDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PrestadorNotFoundException.class)
    public ResponseEntity<ErrorMessageDTO> prestadorNaoEncontrado(PrestadorNotFoundException ex) {
        ErrorMessageDTO errorMessageDTO = buildErrorMessage(ex, PRESTADOR_NOT_FOUND);

        return new ResponseEntity(errorMessageDTO, HttpStatus.NOT_FOUND);
    }

    private ErrorMessageDTO buildErrorMessage(RuntimeException ex, String errorCode) {
        ErrorMessageDTO errorMessageDTO = new ErrorMessageDTO();
        errorMessageDTO.setErrorCode(errorCode);
        errorMessageDTO.setErrorMessage(ex.getMessage());
        errorMessageDTO.setTimestamp(LocalDateTime.now());

        return errorMessageDTO;
    }
}
