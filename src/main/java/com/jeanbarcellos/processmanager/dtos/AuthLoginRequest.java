package com.jeanbarcellos.processmanager.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class AuthLoginRequest {

    @NotNull(message = "Campo não informado")
    @NotBlank(message = "Não deve estar em branco")
    @Email
    private String email;

    @NotNull(message = "Campo não informado")
    @NotBlank(message = "Não deve estar em branco")
    private String password;
}
