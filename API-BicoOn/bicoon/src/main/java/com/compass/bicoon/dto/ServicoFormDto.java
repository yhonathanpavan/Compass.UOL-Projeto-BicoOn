package com.compass.bicoon.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class ServicoFormDto {

    @NotNull @NotEmpty
    private String categoria;
    @NotNull @NotEmpty
    private String descricao;

}
