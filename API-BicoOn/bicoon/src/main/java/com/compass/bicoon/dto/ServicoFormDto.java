package com.compass.bicoon.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ServicoFormDto {

    @NotNull @NotEmpty
    private String categoria;
    @NotNull @NotEmpty
    private String descricao;

}
