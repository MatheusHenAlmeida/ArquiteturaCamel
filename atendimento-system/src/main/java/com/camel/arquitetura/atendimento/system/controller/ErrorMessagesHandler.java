package com.camel.arquitetura.atendimento.system.controller;

import com.camel.arquitetura.atendimento.system.exceptions.*;
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

    @ExceptionHandler(ClientNotFoundException.class)
    public ResponseEntity<ErrorMessageDTO> clienteNaoEncontrado(ClientNotFoundException ex) {
        ErrorMessageDTO errorMessageDTO = buildErrorMessage(ex, CLIENT_NOT_FOUND);

        return new ResponseEntity(errorMessageDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OutsideClientException.class)
    public ResponseEntity<ErrorMessageDTO> clienteNaoEdaBaseAtendente(OutsideClientException ex) {
        ErrorMessageDTO errorMessageDTO = buildErrorMessage(ex, CLIENT_NOT_FOUND_IN_ATENDENTE_AREA);

        return new ResponseEntity(errorMessageDTO, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(OutsideProviderException.class)
    public ResponseEntity<ErrorMessageDTO> prestadorNaoEdaBaseAtendente(OutsideProviderException ex) {
        ErrorMessageDTO errorMessageDTO = buildErrorMessage(ex, PRESTADOR_NOT_FOUND_IN_ATENDENTE_AREA);

        return new ResponseEntity(errorMessageDTO, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(CredentialsInvalidException.class)
    public ResponseEntity<ErrorMessageDTO> credenciaisInvalidas(CredentialsInvalidException ex) {
        ErrorMessageDTO errorMessageDTO = buildErrorMessage(ex, CREDENTIAL_INVALID);

        return new ResponseEntity(errorMessageDTO, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ServerNotFoundException.class)
    public ResponseEntity<ErrorMessageDTO> servidorNaoEncontrado(ServerNotFoundException ex) {
        ErrorMessageDTO errorMessageDTO = buildErrorMessage(ex, SERVER_NOT_FOUND);

        return new ResponseEntity(errorMessageDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DbErrorConnectionException.class)
    public ResponseEntity<ErrorMessageDTO> dbNaoEncontrado(DbErrorConnectionException ex) {
        ErrorMessageDTO errorMessageDTO = buildErrorMessage(ex, DATABASE_NOT_FOUND);

        return new ResponseEntity(errorMessageDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnknownException.class)
    public ResponseEntity<ErrorMessageDTO> prestadorNaoEncontrado(UnknownException ex) {
        ErrorMessageDTO errorMessageDTO = buildErrorMessage(ex, UNKNOWN_ERROR);

        return new ResponseEntity(errorMessageDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ErrorMessageDTO buildErrorMessage(RuntimeException ex, String errorCode) {
        ErrorMessageDTO errorMessageDTO = new ErrorMessageDTO();
        errorMessageDTO.setErrorCode(errorCode);
        errorMessageDTO.setErrorMessage(ex.getMessage());
        errorMessageDTO.setTimestamp(LocalDateTime.now());

        return errorMessageDTO;
    }
}
