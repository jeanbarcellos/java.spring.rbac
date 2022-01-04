package com.jeanbarcellos.processmanager.exceptions;

import java.util.HashMap;
import java.util.Map;

/**
 * Valivação com a possibilidade de adicionar um dicionário de errors
 *
 * Esta exception é usada geralmente para apontar erros em propriedades de algum
 * model (Entity, DTO, etc...).
 * Onde a propriedade é a chave, e o(s) erro(s) são os valores
 */
public class ValidationMapException extends RuntimeException {

    private Map<String, Object> errors = new HashMap<>();

    public ValidationMapException(String message) {
        super(message);
    }

    public ValidationMapException(String message, Map<String, Object> errors) {
        super(message);
        this.errors = errors;
    }

    public Map<String, Object> getErrors() {
        return this.errors;
    }

}
