package com.compass.bicoon.builder;

import com.compass.bicoon.dto.servico.ServicoDto;
import com.compass.bicoon.dto.servico.ServicoFormDto;
import com.compass.bicoon.entities.Categoria;
import com.compass.bicoon.entities.Servico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Arrays;

public final class ServicoBuilder {

    public static final Long ID = 1L;
    public static final Categoria CATEGORIA = Categoria.builder().id(ID).nome("Marido de Aluguel").build();
    public static final String DESCRICAO = "Teste";

    public static Servico getServico(){
        return  Servico.builder()
                .id(ID)
                .categoria(CATEGORIA)
                .descricao(DESCRICAO)
                .build();
    }

    public static ServicoDto getServicoDto(){
        return  ServicoDto.builder()
                .id(ID)
                .categoria(CATEGORIA)
                .descricao(DESCRICAO)
                .build();
    }

    public static ServicoFormDto getServicoForm(){
        return  ServicoFormDto.builder()
                .categoria("Marido de Aluguel")
                .descricao(DESCRICAO)
                .build();
    }

    public static Page<Servico> getServicoPaginacao(){
        return new PageImpl<>(Arrays.asList(getServico(), getServico()));
    }

    public static Page<ServicoDto> getServicoPaginacaoDto(){
        return new PageImpl<>(Arrays.asList(getServicoDto(), getServicoDto()));
    }


}
