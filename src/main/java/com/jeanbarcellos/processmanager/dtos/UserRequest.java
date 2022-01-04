package com.jeanbarcellos.processmanager.dtos;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.jeanbarcellos.processmanager.domain.enums.UserStatus;

import lombok.Data;

@Data
public class UserRequest {

    @NotNull(message = "Campo não informado")
    @NotBlank
    @Size(min = 4, max = 128, message = "O tamanho deve ser entre 4 e 128 caracteres")
    private String name;

    @NotNull(message = "Campo não informado")
    @NotBlank(message = "Não deve estar em branco")
    @Email
    private String email;

    @NotNull(message = "Campo não informado")
    @NotBlank(message = "Não deve estar em branco")
    private String password;

    @NotNull(message = "Campo não informado")
    private UserStatus status;

    @NotNull(message = "Campo não informado")
    private List<Integer> roles;
}
