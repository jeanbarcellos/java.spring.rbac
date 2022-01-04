package com.jeanbarcellos.processmanager.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class AuthLoginWithTokenRequest {

    @NotNull(message = "Campo não informado")
    @NotBlank(message = "Não deve estar em branco")
    private String token;
}
