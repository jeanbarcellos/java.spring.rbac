package com.jeanbarcellos.processmanager.dtos;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidationErrorMapRespose extends ErrorResponse {

    private Map<String, Object> errors = new HashMap<>();

    public ValidationErrorMapRespose() {

    }

    public ValidationErrorMapRespose(String message) {
        super(message);
    }

    public ValidationErrorMapRespose(String message, Map<String, Object> errors) {
        super(message);
        this.errors = errors;
    }
}
