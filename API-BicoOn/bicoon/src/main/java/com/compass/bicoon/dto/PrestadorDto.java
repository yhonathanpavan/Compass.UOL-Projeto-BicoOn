package com.compass.bicoon.dto;

import com.compass.bicoon.entities.Servico;
import com.compass.bicoon.model.Usuario;
import lombok.Data;

import java.util.List;

@Data
public class PrestadorDto {

    private Long id;
    private String nome;
    private String telefone;
    private Boolean disponivel;
    private List<Servico> servico;

}
