package com.jeanbarcellos.processmanager.mappers;

import com.jeanbarcellos.processmanager.domain.entities.Role;
import com.jeanbarcellos.processmanager.dtos.RoleCollectionResponse;
import com.jeanbarcellos.processmanager.dtos.RoleRequest;
import com.jeanbarcellos.processmanager.dtos.RoleResponse;

import org.springframework.beans.BeanUtils;

public class RoleMapper {

    public static Role toRole(RoleRequest request) {
        return new Role(request.getName(), request.getDescription());
    }

    public static Role toRole(Integer id, RoleRequest request) {
        return new Role(id, request.getName(), request.getDescription());
    }

    public static RoleRequest toRequest(Role role) {
        var request = new RoleRequest();
        BeanUtils.copyProperties(role, request);
        return request;
    }

    public static RoleResponse toResponse(Role role) {
        return RoleResponse.from(role);
    }

    public static RoleCollectionResponse toCollectionResponse(Role role) {
        return RoleCollectionResponse.from(role);
    }

}
