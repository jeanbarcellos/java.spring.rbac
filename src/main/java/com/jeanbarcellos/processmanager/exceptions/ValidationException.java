package com.jeanbarcellos.processmanager.exceptions;

/**
 * Validação simples
 */
public class ValidationException extends RuntimeException {

    public ValidationException(String message) {
        super(message);
    }
}
