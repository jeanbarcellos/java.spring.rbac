package com.jeanbarcellos.processmanager.config.exception;

import com.jeanbarcellos.processmanager.dtos.ErrorResponse;
import com.jeanbarcellos.processmanager.exceptions.AuthenticationException;
import com.jeanbarcellos.processmanager.exceptions.AuthorizationException;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Manipula as exceções de segurança
 */
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SecutiryHandler {

    // #region Autenticação

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<?> handleException(AuthenticationException exception) {
        var response = new ErrorResponse();
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setMessage(exception.getMessage());

        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(LockedException.class)
    public ResponseEntity<?> handleException(LockedException exception) {
        var response = new ErrorResponse();
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setMessage("A conta do usuário está bloqueada.");

        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<?> handleException(DisabledException exception) {
        var response = new ErrorResponse();
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setMessage("O usuário está desabilitado.");

        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(AccountStatusException.class)
    public ResponseEntity<?> handleException(AccountStatusException exception) {
        var response = new ErrorResponse();
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setMessage("Erro ao tentar autenticar com o usuário. Entre em contato com o administrador do sistema");

        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    // #endregion

    // #region Autorização

    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<?> handleException(AuthorizationException exception) {
        var response = new ErrorResponse();
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setMessage(exception.getMessage());

        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleException(AccessDeniedException exception) {
        var response = new ErrorResponse();
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setMessage("Acesso não autorizado.");

        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    // #endregion
}
