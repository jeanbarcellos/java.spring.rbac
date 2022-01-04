package com.jeanbarcellos.processmanager.controllers;

import java.util.List;

import javax.validation.Valid;

import com.jeanbarcellos.processmanager.config.Roles;
import com.jeanbarcellos.processmanager.dtos.PermissionRequest;
import com.jeanbarcellos.processmanager.dtos.PermissionResponse;
import com.jeanbarcellos.processmanager.dtos.SuccessResponse;
import com.jeanbarcellos.processmanager.services.PermissionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/permissions")
@PreAuthorize("hasRole('" + Roles.ADMINISTRATOR + "')")
public class PermissionController extends BaseController {

    @Autowired
    private PermissionService permissionService;

    @GetMapping
    public ResponseEntity<List<PermissionResponse>> index() {
        List<PermissionResponse> list = permissionService.getAll();

        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PermissionResponse> findById(@PathVariable Integer id) {
        PermissionResponse response = permissionService.getById(id);

        return ResponseEntity.ok(response);
    }

    @PostMapping()
    public ResponseEntity<PermissionResponse> insert(@RequestBody @Valid PermissionRequest request) {
        PermissionResponse response = permissionService.insert(request);

        return ResponseEntity.created(this.createUriLocation("/{id}", response.getId())).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PermissionResponse> update(@RequestBody @Valid PermissionRequest request,
            @PathVariable Integer id) {
        PermissionResponse response = permissionService.update(id, request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public SuccessResponse delete(@PathVariable Integer id) {
        return permissionService.delete(id);
    }
}
