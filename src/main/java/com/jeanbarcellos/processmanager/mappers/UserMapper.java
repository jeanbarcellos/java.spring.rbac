package com.jeanbarcellos.processmanager.mappers;

import java.util.List;

import com.jeanbarcellos.processmanager.domain.entities.Role;
import com.jeanbarcellos.processmanager.domain.entities.User;
import com.jeanbarcellos.processmanager.dtos.UserRequest;
import com.jeanbarcellos.processmanager.dtos.UserResponse;

public class UserMapper {

    public static User toUser(UserRequest request) {
        var user = new User(
                request.getName(),
                request.getEmail(),
                request.getPassword(),
                request.getStatus());

        return user;
    }

    public static User toUser(UserRequest request, List<Role> roles) {
        var user = new User(
                request.getName(),
                request.getEmail(),
                request.getPassword(),
                request.getStatus());

        for (Role role : roles) {
            user.addRole(role);
        }

        return user;
    }

    public static User toUser(Integer id, UserRequest request, List<Role> roles) {
        var user = new User(
                id,
                request.getName(),
                request.getEmail(),
                request.getPassword(),
                request.getStatus());

        for (Role role : roles) {
            user.addRole(role);
        }

        return user;
    }

    public static UserResponse toResponse(User user) {
        return UserResponse.from(user);
    }

}
