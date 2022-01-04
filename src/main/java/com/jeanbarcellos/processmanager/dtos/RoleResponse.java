package com.jeanbarcellos.processmanager.dtos;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.jeanbarcellos.processmanager.domain.entities.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleResponse {

        private Integer id;
        private String name;
        private String description;

        @Builder.Default
        private List<Integer> childRoleIds = new ArrayList<Integer>();

        @Builder.Default
        private List<Integer> parentRoleIds = new ArrayList<Integer>();

        public static RoleResponse from(Role role) {
                return RoleResponse
                                .builder()
                                .id(role.getId())
                                .name(role.getName())
                                .description(role.getDescription())
                                .childRoleIds(
                                                role.getChildRoles().stream().map(childRole -> childRole.getId())
                                                                .collect(Collectors.toList()))
                                .parentRoleIds(role.getParentRoles().stream().map(parentRole -> parentRole.getId())
                                                .collect(Collectors.toList()))
                                .build();
        }
}
