package com.jeanbarcellos.processmanager.controllers;

import java.util.List;

import javax.validation.Valid;

import com.jeanbarcellos.processmanager.config.Roles;
import com.jeanbarcellos.processmanager.dtos.RoleCollectionResponse;
import com.jeanbarcellos.processmanager.dtos.RoleRequest;
import com.jeanbarcellos.processmanager.dtos.RoleResponse;
import com.jeanbarcellos.processmanager.dtos.SuccessResponse;
import com.jeanbarcellos.processmanager.services.RoleService;

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
@RequestMapping("/roles")
@PreAuthorize("hasRole('" + Roles.ADMINISTRATOR + "')")
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;

    @GetMapping
    public ResponseEntity<List<RoleCollectionResponse>> index() {
        List<RoleCollectionResponse> response = roleService.getAll();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleResponse> findById(@PathVariable Integer id) {
        RoleResponse response = roleService.getById(id);

        return ResponseEntity.ok(response);
    }

    @PostMapping()
    public ResponseEntity<RoleResponse> insert(@RequestBody @Valid RoleRequest request) {
        RoleResponse response = roleService.insert(request);

        return ResponseEntity.created(this.createUriLocation("/{id}", response.getId())).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleResponse> update(@RequestBody @Valid RoleRequest request, @PathVariable Integer id) {
        RoleResponse response = roleService.update(id, request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public SuccessResponse delete(@PathVariable Integer id) {
        return roleService.delete(id);
    }

    @GetMapping("/{id}/permissions")
    public ResponseEntity<?> showPemissions(@PathVariable Integer id) {
        var response = roleService.getPermisssions(id);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/permissions")
    public ResponseEntity<?> savePemissions(@PathVariable Integer id) {

        // TO-DO ...

        return ResponseEntity.ok("");
    }
}
