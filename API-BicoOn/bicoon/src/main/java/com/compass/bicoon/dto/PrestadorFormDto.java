package com.compass.bicoon.dto;

import com.compass.bicoon.constants.Sexo;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class PrestadorFormDto {

    @NotNull @NotEmpty
    private String nome;
    @NotNull @NotEmpty
    private String email;
    @NotNull @NotEmpty
    private String senha;
    @NotNull @NotEmpty
    private String cidade;
    @NotNull
    private Sexo sexo;
    @NotNull
    private String telefone;

}
