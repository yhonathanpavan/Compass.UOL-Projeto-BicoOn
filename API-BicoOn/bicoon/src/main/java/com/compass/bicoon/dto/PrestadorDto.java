package com.compass.bicoon.dto;

import com.compass.bicoon.constants.Sexo;
import com.compass.bicoon.entities.Servico;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrestadorDto {

    private Long id;
    private String nome;
    private String cidade;
    private String telefone;
    private Sexo sexo;
    private Boolean disponivel;
    private List<Servico> servico;

}
