package com.jeanbarcellos.processmanager.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.jeanbarcellos.processmanager.domain.entities.Role;
import com.jeanbarcellos.processmanager.domain.entities.User;

import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class JwtServiceTest {

    String secret = "bWFuYWdlci1wcm9jZXNzLXNlY3JldC10ZXN0LTEyMzQ1"; // manager-process-secret-test-12345 em base64
    Integer expiration = 1440; // manager-process-secret-test-12345 em base64
    String tokenValid = "";
    String tokenInvalid = "xxxx.yyyyy.zzzz";

    JwtService jwtService;
    User user;

    JwtServiceTest() {
        resolveJwtService();
        resolveUser();
        resolveToken();
    }

    void resolveUser() {
        var user = new User("Jean Barcellos", "jeanbarcellos@hotmail.com", "teste@123465");
        user.addRole(new Role("admin", "usuário administrador"));
        this.user = user;
    }

    void resolveJwtService() {
        var jwtService = new JwtService();

        ReflectionTestUtils.setField(jwtService, "secret", secret);
        ReflectionTestUtils.setField(jwtService, "expiration", expiration);

        this.jwtService = jwtService;
    }

    void resolveToken() {
        this.tokenValid = jwtService.generateToken(user);
    }

    @Test
    void generateToken_whenInputUser_shouldReturnToken() {
        // Arrange && Act
        String token = jwtService.generateToken(user);

        // Assert
        assertNotNull(token);
    }

    @Test
    void isTokenValid_tokenValid_shouldReturnTrue() {
        // Arrange && Act
        var actual = jwtService.isTokenValid(this.tokenValid);

        // Assert
        assertTrue(actual);
    }

    @Test
    void isTokenValid_tokenInvalid_shouldReturnFalse() {
        // Arrange && Act
        var actual = jwtService.isTokenValid(this.tokenInvalid);

        // Assert
        assertFalse(actual);
    }

    @Test
    void validateToken_tokenValid_shouldReturnNothing() {
        // Arrange && Act && Assert
        jwtService.validateToken(this.tokenValid);
    }

    @Test
    void validateToken_tojenInvalid_shouldThrowException() {
        // Arrange && Act && Assert
        assertThrows(Exception.class, () -> {
            jwtService.validateToken(this.tokenInvalid);
        });
    }

    @Test
    void getTokenUsername_tokenValid_shouldReturnUsername() {
        // Arrange && Act
        var actual = jwtService.getTokenUsername(this.tokenValid);

        // Assert
        assertEquals(user.getUsername(), actual);
    }

    @Test
    void getTokenUsername_tokenInvalid_shouldThrowException() {
        // Arrange && Act && Assert
        assertThrows(Exception.class, () -> {
            jwtService.getTokenUsername(this.tokenInvalid);
        });
    }

}
