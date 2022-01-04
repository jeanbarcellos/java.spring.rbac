package com.jeanbarcellos.processmanager.controllers;

import javax.validation.Valid;

import com.jeanbarcellos.processmanager.dtos.AuthLoginRequest;
import com.jeanbarcellos.processmanager.dtos.AuthLoginResponse;
import com.jeanbarcellos.processmanager.dtos.AuthLoginWithTokenRequest;
import com.jeanbarcellos.processmanager.services.AuthenticationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<AuthLoginResponse> login(@RequestBody @Valid AuthLoginRequest request) {

        var response = authenticationService.login(request.getEmail(), request.getPassword());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/loginWithToken")
    public ResponseEntity<AuthLoginResponse> loginWithToken(@RequestBody @Valid AuthLoginWithTokenRequest request) {

        var response = authenticationService.loginWithToken(request.getToken());

        return ResponseEntity.ok(response);
    }
}
