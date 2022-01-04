package com.jeanbarcellos.processmanager.config.exception;

import java.util.HashMap;
import java.util.Map;

import com.jeanbarcellos.processmanager.dtos.ValidationErrorMapRespose;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Manipula as exceções componente Validation
 */
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ValidationHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException exception,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {

        Map<String, Object> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error) -> {

            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });

        var response = new ValidationErrorMapRespose();
        response.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
        response.setMessage("A requisição possui dados inválidos.");
        response.setErrors(errors);

        return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
    }

}
