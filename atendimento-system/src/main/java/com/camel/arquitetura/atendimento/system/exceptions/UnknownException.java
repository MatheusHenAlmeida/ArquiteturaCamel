package com.camel.arquitetura.atendimento.system.exceptions;

public class UnknownException extends RuntimeException{
    public UnknownException(String message) {
        super(message);
    }
}
