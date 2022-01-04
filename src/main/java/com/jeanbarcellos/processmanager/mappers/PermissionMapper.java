package com.jeanbarcellos.processmanager.mappers;

import com.jeanbarcellos.processmanager.domain.entities.Permission;
import com.jeanbarcellos.processmanager.dtos.PermissionRequest;
import com.jeanbarcellos.processmanager.dtos.PermissionResponse;

public class PermissionMapper {

    public static Permission toPermission(PermissionRequest request) {
        return new Permission(request.getName(), request.getDescription());
    }

    public static Permission toPermission(Integer id, PermissionRequest request) {
        return new Permission(id, request.getName(), request.getDescription());
    }

    public static PermissionResponse toResponse(Permission permission) {
        return PermissionResponse.from(permission);
    }

}
