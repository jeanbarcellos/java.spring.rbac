package com.jeanbarcellos.processmanager.services;

import static org.springframework.util.ObjectUtils.isEmpty;

import java.util.List;
import java.util.stream.Collectors;

import com.jeanbarcellos.processmanager.domain.entities.Role;
import com.jeanbarcellos.processmanager.domain.entities.User;
import com.jeanbarcellos.processmanager.domain.repositories.RoleRepository;
import com.jeanbarcellos.processmanager.domain.repositories.UserRepository;
import com.jeanbarcellos.processmanager.dtos.SuccessResponse;
import com.jeanbarcellos.processmanager.dtos.UserRequest;
import com.jeanbarcellos.processmanager.dtos.UserResponse;
import com.jeanbarcellos.processmanager.exceptions.NotFoundException;
import com.jeanbarcellos.processmanager.exceptions.ValidationException;
import com.jeanbarcellos.processmanager.mappers.UserMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private static final String MSG_ERROR_USER_NOT_INFORMED = "O ID do usuário deve ser informado.";
    private static final String MSG_ERROR_USER_NOT_FOUND = "Não há usário para o ID informado.";
    private static final String MSG_USER_ACTIVATED_SUCCESSFULLY = "Usuário ativado com sucesso.";
    private static final String MSG_USER_INACTIVATED_SUCCESSFULLY = "Usuário desativado com sucesso.";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public List<UserResponse> getAll() {
        List<User> list = userRepository.findAll();

        return list.stream().map(UserResponse::from).collect(Collectors.toList());
    }

    public UserResponse getById(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(MSG_ERROR_USER_NOT_FOUND));

        return UserMapper.toResponse(user);
    }

    public UserResponse insert(UserRequest request) {
        List<Role> roles = roleRepository.findByIdIn(request.getRoles());

        User user = UserMapper.toUser(request, roles);

        var passwordHash = passwordEncoder.encode(request.getPassword());
        user.setPassword(passwordHash);

        user = userRepository.save(user);

        return UserMapper.toResponse(user);
    }

    public UserResponse update(Integer id, UserRequest request) {
        this.validateExistsById(id);

        List<Role> roles = roleRepository.findByIdIn(request.getRoles());

        User user = UserMapper.toUser(id, request, roles);

        var passwordHash = passwordEncoder.encode(request.getPassword());
        user.setPassword(passwordHash);

        user = userRepository.save(user);

        return UserMapper.toResponse(user);
    }

    public SuccessResponse activate(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ValidationException(MSG_ERROR_USER_NOT_FOUND));

        user.activate();

        userRepository.save(user);

        return SuccessResponse.create(MSG_USER_ACTIVATED_SUCCESSFULLY);
    }

    public SuccessResponse inactivate(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ValidationException(MSG_ERROR_USER_NOT_FOUND));

        user.inactivate();

        userRepository.save(user);

        return SuccessResponse.create(MSG_USER_INACTIVATED_SUCCESSFULLY);
    }

    private void validateExistsById(Integer id) {
        if (isEmpty(id)) {
            throw new ValidationException(MSG_ERROR_USER_NOT_INFORMED);
        }

        if (!userRepository.existsById(id)) {
            throw new NotFoundException(MSG_ERROR_USER_NOT_FOUND);
        }
    }
}
