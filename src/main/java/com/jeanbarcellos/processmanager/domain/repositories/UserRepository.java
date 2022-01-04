package com.jeanbarcellos.processmanager.domain.repositories;

import java.util.List;

import com.jeanbarcellos.processmanager.domain.entities.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);

    List<User> findByIdIn(List<Integer> ids);
}
