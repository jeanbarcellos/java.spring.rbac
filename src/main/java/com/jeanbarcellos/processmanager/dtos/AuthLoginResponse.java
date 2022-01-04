package com.jeanbarcellos.processmanager.dtos;

import java.util.ArrayList;
import java.util.List;

import com.jeanbarcellos.processmanager.domain.entities.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthLoginResponse {

    private Integer id;

    private String name;

    private String email;

    @Builder.Default
    private List<String> roles = new ArrayList<String>();

    private String token;

    public static AuthLoginResponse from(User user) {
        return AuthLoginResponse
                .builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .roles(user.getRoleNames())
                .build();
    }

    public static AuthLoginResponse from(User user, String token) {
        return AuthLoginResponse
                .builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .token(token)
                .roles(user.getRoleNames())
                .build();
    }
}
