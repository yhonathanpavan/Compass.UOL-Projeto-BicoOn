package com.compass.bicoon.builder;

import com.compass.bicoon.dto.categoria.CategoriaDto;
import com.compass.bicoon.dto.categoria.CategoriaFormDto;
import com.compass.bicoon.entities.Categoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Arrays;

public final class CategoriaBuilder {

    public static final Long ID = 1L;
    public static final String NOME = "Babá";

    public static Categoria getCategoria(){
        return  Categoria.builder()
                .id(ID)
                .nome(NOME)
                .build();
    }

    public static CategoriaDto getCategoriaDto(){
        return  CategoriaDto.builder()
                .id(ID)
                .nome(NOME)
                .build();
    }

    public static CategoriaFormDto getCategoriaForm(){
        return  CategoriaFormDto.builder()
                .nome(NOME)
                .build();
    }

    public static Page<Categoria> getCategoriaPaginacao(){
        return new PageImpl<>(Arrays.asList(getCategoria(), getCategoria()));
    }

    public static Page<CategoriaDto> getCategoriaPaginacaoDto(){
        return new PageImpl<>(Arrays.asList(getCategoriaDto(), getCategoriaDto()));
    }

}
