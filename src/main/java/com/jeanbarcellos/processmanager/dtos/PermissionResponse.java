package com.jeanbarcellos.processmanager.dtos;

import com.jeanbarcellos.processmanager.domain.entities.Permission;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PermissionResponse {

    private Integer id;
    private String name;
    private String description;

    public static PermissionResponse from(Permission permission) {
        return PermissionResponse
                .builder()
                .id(permission.getId())
                .name(permission.getName())
                .description(permission.getDescription())
                .build();
    }
}
