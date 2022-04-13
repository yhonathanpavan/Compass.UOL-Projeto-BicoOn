package com.compass.bicoon.dto;

import com.compass.bicoon.constants.Sexo;
import com.compass.bicoon.entities.Servico;
import lombok.Data;

import java.util.List;

@Data
public class PrestadorDto {

    private Long id;
    private String nome;
    private String cidade;
    private String telefone;
    private Sexo sexo;
    private Boolean disponivel;
    private List<Servico> servico;

}
