package com.jeanbarcellos.processmanager.services;

import com.jeanbarcellos.processmanager.domain.entities.User;
import com.jeanbarcellos.processmanager.dtos.AuthLoginResponse;
import com.jeanbarcellos.processmanager.exceptions.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * Autentication Application Application
 */
@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationUserService repository;

    public AuthLoginResponse login(String email, String password) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                email, password);

        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        User user = (User) authentication.getPrincipal();

        String token = jwtService.generateToken(user);

        AuthLoginResponse response = AuthLoginResponse.from(user, token);

        return response;
    }

    public AuthLoginResponse loginWithToken(String token) {

        boolean tokenValid = jwtService.isTokenValid(token);

        if (!tokenValid) {
            throw new AuthenticationException("Token de autenticação inválido");
        }

        String username = jwtService.getTokenUsername(token);

        UserDetails user = repository.loadUserByUsername(username);

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                user, null, user.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        AuthLoginResponse response = AuthLoginResponse.from((User) user, token);

        return response;
    }

}
