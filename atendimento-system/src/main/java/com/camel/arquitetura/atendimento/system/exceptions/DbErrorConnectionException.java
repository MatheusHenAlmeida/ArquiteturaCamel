package com.camel.arquitetura.atendimento.system.exceptions;

public class DbErrorConnectionException extends RuntimeException {
    public DbErrorConnectionException(String message) {
        super(message);
    }
}
