package com.camel.arquitetura.atendimento.system.exceptions;

public class ServerNotFoundException extends RuntimeException {
    public ServerNotFoundException(String message) {
        super(message);
    }
}
