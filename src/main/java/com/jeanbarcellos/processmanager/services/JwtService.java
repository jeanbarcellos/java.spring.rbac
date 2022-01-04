package com.jeanbarcellos.processmanager.services;

import static org.springframework.util.ObjectUtils.isEmpty;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import com.jeanbarcellos.processmanager.exceptions.AuthenticationException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    private static String USER_NAME = "user_name";
    private static String USER_ROLES = "roles";

    @Value("${app-config.jwt.secret}")
    private String secret;

    @Value("${app-config.jwt.expiration}")
    private Integer expiration;

    public String generateToken(UserDetails user) {
        return Jwts
                .builder()
                .setClaims(this.generateClaims(user))
                .setSubject(user.getUsername())
                .setIssuedAt(this.generateIssuedAt())
                .setExpiration(this.generateExpiration())
                .setNotBefore(this.generateNotBefore())
                .signWith(this.generateKey())
                .compact();
    }

    private Map<String, Object> generateClaims(UserDetails user) {
        HashMap<String, Object> claims = new HashMap<>();
        claims.put(USER_NAME, user.getUsername());
        claims.put(USER_ROLES, user.getAuthorities().stream().map(x -> x.getAuthority()).collect(Collectors.toList()));

        return claims;
    }

    private Date generateIssuedAt() {
        return Date.from(
                LocalDateTime.now()
                        .atZone(ZoneId.systemDefault()).toInstant());
    }

    private Date generateExpiration() {
        return Date.from(
                LocalDateTime.now()
                        .plusMinutes(expiration)
                        .atZone(ZoneId.systemDefault()).toInstant());
    }

    private Date generateNotBefore() {
        return Date.from(
                LocalDateTime.now()
                        .plusSeconds(-1)
                        .atZone(ZoneId.systemDefault()).toInstant());
    }

    private SecretKey generateKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public boolean isTokenValid(String token) {
        try {
            this.validateToken(token);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public void validateToken(String token) {
        if (isEmpty(this.getTokenUsername(token))) {
            throw new AuthenticationException("Token de acesso inválido: Usuário não identificado.");
        }
    }

    public String getTokenUsername(String token) {
        Claims claims = descriptografarJwt(token).getBody();

        Object userId = claims.get(USER_NAME);

        return !isEmpty(userId) ? userId.toString() : null;
        // return claims.getSubject();
    }

    private Jws<Claims> descriptografarJwt(String token) {
        try {
            return Jwts
                    .parserBuilder()
                    .setSigningKey(generateKey())
                    .build()
                    .parseClaimsJws(token);
        } catch (Exception ex) {
            throw new AuthenticationException("Token de acesso inválido.");
        }
    }
}
