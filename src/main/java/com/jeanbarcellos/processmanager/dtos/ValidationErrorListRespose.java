package com.jeanbarcellos.processmanager.dtos;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidationErrorListRespose extends ErrorResponse {

    private List<String> errors = new ArrayList<String>();

    public ValidationErrorListRespose() {

    }

    public ValidationErrorListRespose(String message) {
        super(message);
    }

    public ValidationErrorListRespose(String message, List<String> errors) {
        super(message);
        this.errors = errors;
    }
}
