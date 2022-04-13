package com.compass.bicoon.dto;

import com.compass.bicoon.entities.Categoria;
import lombok.Data;


@Data
public class ServicoDto {

    private Long id;
    private Categoria categoria;
    private String descricao;
}
