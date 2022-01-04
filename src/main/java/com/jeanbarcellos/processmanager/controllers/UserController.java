package com.jeanbarcellos.processmanager.controllers;

import java.util.List;

import javax.validation.Valid;

import com.jeanbarcellos.processmanager.config.Roles;
import com.jeanbarcellos.processmanager.dtos.SuccessResponse;
import com.jeanbarcellos.processmanager.dtos.UserRequest;
import com.jeanbarcellos.processmanager.dtos.UserResponse;
import com.jeanbarcellos.processmanager.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@PreAuthorize("hasRole('" + Roles.ROOT + "')")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponse>> findAll() {
        List<UserResponse> list = userService.getAll();

        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable Integer id) {
        UserResponse response = userService.getById(id);

        return ResponseEntity.ok(response);
    }

    @PostMapping()
    public ResponseEntity<UserResponse> insert(@RequestBody @Valid UserRequest request) {
        UserResponse response = userService.insert(request);

        return ResponseEntity.created(this.createUriLocation("/{id}", response.getId())).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> update(@RequestBody @Valid UserRequest request, @PathVariable Integer id) {
        UserResponse response = userService.update(id, request);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/activate")
    public ResponseEntity<SuccessResponse> activate(@PathVariable Integer id) {
        SuccessResponse response = userService.activate(id);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/inactivate")
    public ResponseEntity<SuccessResponse> inactivate(@PathVariable Integer id) {
        SuccessResponse response = userService.inactivate(id);

        return ResponseEntity.ok(response);
    }

}