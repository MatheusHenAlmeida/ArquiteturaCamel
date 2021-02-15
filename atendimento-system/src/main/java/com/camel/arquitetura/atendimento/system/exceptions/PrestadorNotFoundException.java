package com.camel.arquitetura.atendimento.system.exceptions;

public class PrestadorNotFoundException extends RuntimeException {
    public PrestadorNotFoundException(String message) {
        super(message);
    }
}
