package com.compass.bicoon.dto;


import com.compass.bicoon.constants.Sexo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
public class ClienteDto {

    private Long id;
    private String nome;
    private String cidade;
    @Enumerated(EnumType.STRING)
    private Sexo sexo;
}
