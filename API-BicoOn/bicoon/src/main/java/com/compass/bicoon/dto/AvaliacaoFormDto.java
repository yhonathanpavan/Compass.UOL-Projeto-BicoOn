package com.compass.bicoon.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class AvaliacaoFormDto {

    @NotNull @NotEmpty
    private String comentario;
    @NotNull
    private int nota;
}
