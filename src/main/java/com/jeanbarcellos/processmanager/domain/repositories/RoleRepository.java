package com.jeanbarcellos.processmanager.domain.repositories;

import java.util.List;

import com.jeanbarcellos.processmanager.domain.entities.Role;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    List<Role> findByIdIn(List<Integer> ids);
}
