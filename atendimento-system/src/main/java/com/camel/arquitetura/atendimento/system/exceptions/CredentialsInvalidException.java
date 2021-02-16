package com.camel.arquitetura.atendimento.system.exceptions;

public class CredentialsInvalidException extends RuntimeException {
    public CredentialsInvalidException(String message) {
        super(message);
    }
}
