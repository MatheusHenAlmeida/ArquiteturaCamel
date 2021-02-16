package com.camel.arquitetura.atendimento.system.exceptions;

public class OutsideClientException extends RuntimeException {
    public OutsideClientException(String message) {
        super(message);
    }
}
