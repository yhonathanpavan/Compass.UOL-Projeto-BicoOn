package com.compass.bicoon.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AvaliacaoDto {

    private Long id;
    private String comentario;
    private int nota;
    private LocalDate data;
}
