package com.compass.bicoon.dto.cliente;

import com.compass.bicoon.constants.Sexo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDto {

    private Long id;
    private String nome;
    private String cidade;
    @Enumerated(EnumType.STRING)
    private Sexo sexo;
}
