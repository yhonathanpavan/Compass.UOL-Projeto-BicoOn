package com.compass.bicoon.dto.avaliacao;

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
public class AvaliacaoFormDto {

    @NotNull @NotEmpty
    private String comentario;
    @NotNull
    private int nota;
}
