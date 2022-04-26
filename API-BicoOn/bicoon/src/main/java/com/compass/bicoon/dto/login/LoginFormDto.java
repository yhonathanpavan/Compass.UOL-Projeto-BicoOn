package com.compass.bicoon.dto.login;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class LoginFormDto {

    @NotNull @NotEmpty
    private String email;
    @NotNull @NotEmpty
    private String senha;
}
