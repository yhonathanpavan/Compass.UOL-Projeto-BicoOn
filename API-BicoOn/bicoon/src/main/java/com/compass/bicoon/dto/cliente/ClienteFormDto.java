package com.compass.bicoon.dto.cliente;

import com.compass.bicoon.constants.Sexo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
public class ClienteFormDto {

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
}
