package com.jeanbarcellos.processmanager.config.exception;

import javax.validation.ValidationException;

import com.jeanbarcellos.processmanager.dtos.ErrorResponse;
import com.jeanbarcellos.processmanager.dtos.ValidationErrorListRespose;
import com.jeanbarcellos.processmanager.dtos.ValidationErrorMapRespose;
import com.jeanbarcellos.processmanager.exceptions.NotFoundException;
import com.jeanbarcellos.processmanager.exceptions.ValidationListException;
import com.jeanbarcellos.processmanager.exceptions.ValidationMapException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Manipula as exceções do App
 */
@ControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
public class GlobalExceptionHandler {

    private static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFoundException(NotFoundException exception) {
        var response = new ErrorResponse();
        response.setStatus(HttpStatus.NOT_FOUND.value());
        response.setMessage(exception.getMessage());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> handleValidationException(ValidationException exception) {
        var response = new ErrorResponse();
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setMessage(exception.getMessage());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ValidationMapException.class)
    public ResponseEntity<?> handleValidationMapException(ValidationMapException exception) {
        var response = new ValidationErrorMapRespose();
        response.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
        response.setMessage(exception.getMessage());
        response.setErrors(exception.getErrors());

        return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(ValidationListException.class)
    public ResponseEntity<?> handleValidationListException(ValidationListException exception) {
        var response = new ValidationErrorListRespose();
        response.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
        response.setMessage(exception.getMessage());
        response.setErrors(exception.getErrors());

        return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception exception) {

        logger.error(exception.getMessage());

        var response = new ErrorResponse();
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setMessage("Erro Interno do Servidor. Tente novamente mais tarde.");

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
