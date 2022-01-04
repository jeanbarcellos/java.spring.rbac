package com.jeanbarcellos.processmanager.services;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ValidationException;

import com.jeanbarcellos.processmanager.domain.entities.Permission;
import com.jeanbarcellos.processmanager.domain.repositories.PermissionRepository;
import com.jeanbarcellos.processmanager.dtos.PermissionRequest;
import com.jeanbarcellos.processmanager.dtos.PermissionResponse;
import com.jeanbarcellos.processmanager.dtos.SuccessResponse;
import com.jeanbarcellos.processmanager.mappers.PermissionMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermissionService {

    private static final String MSG_ERROR_ROLE_NOT_FOUND = "Não há permissão para o ID informado.";
    private static final String MSG_ROLE_DELETED_SUCCESSFULLY = "Permissão excluída com sucesso.";

    @Autowired
    private PermissionRepository permissionRepository;

    public List<PermissionResponse> getAll() {
        List<Permission> list = permissionRepository.findAll();

        return list.stream().map(PermissionResponse::from).collect(Collectors.toList());
    }

    public PermissionResponse getById(Integer id) {
        Permission result = permissionRepository.findById(id)
                .orElseThrow(() -> new ValidationException(MSG_ERROR_ROLE_NOT_FOUND));

        return PermissionMapper.toResponse(result);
    }

    public PermissionResponse insert(PermissionRequest request) {
        Permission permission = PermissionMapper.toPermission(request);

        permission = permissionRepository.save(permission);

        return PermissionMapper.toResponse(permission);
    }

    public PermissionResponse update(Integer id, PermissionRequest request) {
        if (permissionRepository.existsById(id)) {
            new ValidationException(MSG_ERROR_ROLE_NOT_FOUND);
        }

        Permission permission = PermissionMapper.toPermission(id, request);

        permission = permissionRepository.save(permission);

        return PermissionMapper.toResponse(permission);
    }

    public SuccessResponse delete(Integer id) {
        if (permissionRepository.existsById(id)) {
            new ValidationException(MSG_ERROR_ROLE_NOT_FOUND);
        }

        permissionRepository.deleteById(id);

        return SuccessResponse.create(MSG_ROLE_DELETED_SUCCESSFULLY);
    }
}
