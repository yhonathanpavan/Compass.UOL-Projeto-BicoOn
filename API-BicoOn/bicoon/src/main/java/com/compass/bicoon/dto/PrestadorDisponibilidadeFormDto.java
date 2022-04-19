package com.compass.bicoon.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class PrestadorDisponibilidadeFormDto {
    @NotNull
    private Boolean disponivel;
}
