package com.jeanbarcellos.processmanager.dtos;

import com.jeanbarcellos.processmanager.domain.entities.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleCollectionResponse {

    private Integer id;
    private String name;
    private String description;

    public static RoleCollectionResponse from(Role role) {
        return RoleCollectionResponse
                .builder()
                .id(role.getId())
                .name(role.getName())
                .description(role.getDescription())
                .build();
    }
}
