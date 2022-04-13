package com.compass.bicoon.dto;

import com.compass.bicoon.constants.Sexo;
import lombok.Data;
import javax.validation.constraints.NotNull;

@Data
public class PrestadorForm {

    @NotNull
    private String nome;
    @NotNull
    private String email;
    @NotNull
    private String senha;
    @NotNull
    private String cidade;
    @NotNull
    private Sexo sexo;
    @NotNull
    private String telefone;

}
