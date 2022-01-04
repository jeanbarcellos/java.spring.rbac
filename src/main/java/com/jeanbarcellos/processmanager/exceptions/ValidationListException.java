package com.jeanbarcellos.processmanager.exceptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Valivação com a possibilidade de adicionar uma lista de erros
 */
public class ValidationListException extends RuntimeException {

    private List<String> errors = new ArrayList<String>();

    public ValidationListException(String message) {
        super(message);
    }

    public ValidationListException(String message, List<String> errors) {
        super(message);
        this.errors = errors;
    }

    public List<String> getErrors() {
        return this.errors;
    }

}
