package com.compass.bicoon.dto;

import lombok.Data;

@Data
public class AvaliacaoDto {

    private Long id;
    private Long clienteId;
    private String comentario;
    private int nota;
}
