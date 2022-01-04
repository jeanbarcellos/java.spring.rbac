package com.jeanbarcellos.processmanager.dtos;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.jeanbarcellos.processmanager.domain.entities.User;
import com.jeanbarcellos.processmanager.domain.enums.UserStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private Integer id;

    private String name;

    private String email;

    private UserStatus status;

    @Builder.Default
    private List<RoleCollectionResponse> roles = new ArrayList<RoleCollectionResponse>();

    public static UserResponse from(User user) {
        return UserResponse
                .builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .status(user.getStatus())
                .roles(user.getRoles().stream().map(RoleCollectionResponse::from).collect(Collectors.toList()))
                .build();
    }
}
