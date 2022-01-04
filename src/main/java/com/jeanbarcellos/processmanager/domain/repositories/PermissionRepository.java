package com.jeanbarcellos.processmanager.domain.repositories;

import java.util.List;

import com.jeanbarcellos.processmanager.domain.entities.Permission;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Integer> {

    List<Permission> findByIdIn(List<Integer> ids);
}
