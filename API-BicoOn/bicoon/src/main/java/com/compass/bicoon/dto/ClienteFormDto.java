package com.compass.bicoon.dto;

import com.compass.bicoon.constants.Sexo;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
public class ClienteFormDto {

    private String nome;
    private String cidade;
    @Enumerated(EnumType.STRING)
    private Sexo sexo;
}
