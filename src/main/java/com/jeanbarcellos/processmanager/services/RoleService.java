package com.jeanbarcellos.processmanager.services;

import static org.springframework.util.ObjectUtils.isEmpty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.jeanbarcellos.processmanager.domain.entities.Permission;
import com.jeanbarcellos.processmanager.domain.entities.Role;
import com.jeanbarcellos.processmanager.domain.enums.PermissionType;
import com.jeanbarcellos.processmanager.domain.repositories.RoleRepository;
import com.jeanbarcellos.processmanager.dtos.RoleCollectionResponse;
import com.jeanbarcellos.processmanager.dtos.RoleRequest;
import com.jeanbarcellos.processmanager.dtos.RoleResponse;
import com.jeanbarcellos.processmanager.dtos.SuccessResponse;
import com.jeanbarcellos.processmanager.exceptions.NotFoundException;
import com.jeanbarcellos.processmanager.exceptions.ValidationException;
import com.jeanbarcellos.processmanager.mappers.RoleMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private static final String MSG_ERROR_ROLE_NOT_INFORMED = "O ID do perfil deve ser informado.";
    private static final String MSG_ERROR_ROLE_NOT_FOUND = "Não há perfil para o ID informado.";
    private static final String MSG_ERROR_ROLE_INHERIT_NOT_FOUND = "Perfil para herdar não encontrada.";
    private static final String MSG_ROLE_DELETED_SUCCESSFULLY = "O perfil excluído com sucesso.";

    @Autowired
    private RoleRepository roleRepository;

    public List<RoleCollectionResponse> getAll() {
        List<Role> list = roleRepository.findAll();

        return list.stream().map(RoleCollectionResponse::from).collect(Collectors.toList());
    }

    public RoleResponse getById(Integer id) {
        Role result = roleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(MSG_ERROR_ROLE_NOT_FOUND));

        return RoleMapper.toResponse(result);
    }

    public RoleResponse insert(RoleRequest request) {
        Role role = RoleMapper.toRole(request);

        this.assignChildRoles(role, request.getChildRoles());

        roleRepository.save(role);

        return RoleMapper.toResponse(role);
    }

    public RoleResponse update(Integer id, RoleRequest request) {
        this.validateExistsById(id);

        Role role = RoleMapper.toRole(id, request);

        this.assignChildRoles(role, request.getChildRoles());

        roleRepository.save(role);

        return RoleMapper.toResponse(role);
    }

    private void assignChildRoles(Role role, List<Integer> rolesIds) {
        role.clearChildRoles();

        for (Integer roleId : rolesIds) {
            var childRole = this.roleRepository.findById(roleId).get();

            if (childRole == null) {
                throw new NotFoundException(MSG_ERROR_ROLE_INHERIT_NOT_FOUND);
            }

            role.addChild(childRole);
        }
    }

    public SuccessResponse delete(Integer id) {
        this.validateExistsById(id);

        roleRepository.deleteById(id);

        return SuccessResponse.create(MSG_ROLE_DELETED_SUCCESSFULLY);
    }

    public Map<String, String> getPermisssions(Integer id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(MSG_ERROR_ROLE_NOT_FOUND));

        Map<Permission, PermissionType> effectivePermissions = role.getPermissionsWithTypes();

        Map<String, String> list = new HashMap<String, String>();
        for (Permission key : effectivePermissions.keySet()) {
            list.put(key.getName(), effectivePermissions.get(key).label);
        }

        return list;
    }

    private void validateExistsById(Integer id) {
        if (isEmpty(id)) {
            throw new ValidationException(MSG_ERROR_ROLE_NOT_INFORMED);
        }

        if (!roleRepository.existsById(id)) {
            throw new NotFoundException(MSG_ERROR_ROLE_NOT_FOUND);
        }
    }
}
