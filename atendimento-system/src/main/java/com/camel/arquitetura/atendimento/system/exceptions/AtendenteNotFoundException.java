package com.camel.arquitetura.atendimento.system.exceptions;

public class AtendenteNotFoundException extends RuntimeException {
    public AtendenteNotFoundException(String message) {
        super(message);
    }
}
