package com.jeanbarcellos.processmanager.services;

import static org.springframework.util.ObjectUtils.isEmpty;

import java.util.List;
import java.util.stream.Collectors;

import com.jeanbarcellos.processmanager.domain.entities.Permission;
import com.jeanbarcellos.processmanager.domain.repositories.PermissionRepository;
import com.jeanbarcellos.processmanager.dtos.PermissionRequest;
import com.jeanbarcellos.processmanager.dtos.PermissionResponse;
import com.jeanbarcellos.processmanager.dtos.SuccessResponse;
import com.jeanbarcellos.processmanager.exceptions.NotFoundException;
import com.jeanbarcellos.processmanager.exceptions.ValidationException;
import com.jeanbarcellos.processmanager.mappers.PermissionMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermissionService {

    private static final String MSG_ERROR_PERMISSION_NOT_INFORMED = "O ID da permissão deve ser informado.";
    private static final String MSG_ERROR_PERMISSION_NOT_FOUND = "Não há permissão para o ID informado.";
    private static final String MSG_PERMISSION_DELETED_SUCCESSFULLY = "Permissão excluída com sucesso.";

    @Autowired
    private PermissionRepository permissionRepository;

    public List<PermissionResponse> getAll() {
        List<Permission> list = permissionRepository.findAll();

        return list.stream().map(PermissionResponse::from).collect(Collectors.toList());
    }

    public PermissionResponse getById(Integer id) {
        Permission result = permissionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(MSG_ERROR_PERMISSION_NOT_FOUND));

        return PermissionMapper.toResponse(result);
    }

    public PermissionResponse insert(PermissionRequest request) {
        Permission permission = PermissionMapper.toPermission(request);

        permissionRepository.save(permission);

        return PermissionMapper.toResponse(permission);
    }

    public PermissionResponse update(Integer id, PermissionRequest request) {
        this.validateExistsById(id);

        Permission permission = PermissionMapper.toPermission(id, request);

        permissionRepository.save(permission);

        return PermissionMapper.toResponse(permission);
    }

    public SuccessResponse delete(Integer id) {
        this.validateExistsById(id);

        permissionRepository.deleteById(id);

        return SuccessResponse.create(MSG_PERMISSION_DELETED_SUCCESSFULLY);
    }

    private void validateExistsById(Integer id) {
        if (isEmpty(id)) {
            throw new ValidationException(MSG_ERROR_PERMISSION_NOT_INFORMED);
        }

        if (!permissionRepository.existsById(id)) {
            throw new NotFoundException(MSG_ERROR_PERMISSION_NOT_FOUND);
        }
    }
}
