package com.compass.bicoon.dto.servico;

import com.compass.bicoon.entities.Categoria;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ServicoDto {

    private Long id;
    private Categoria categoria;
    private String descricao;
}
