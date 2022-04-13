package com.compass.bicoon.services;

import com.compass.bicoon.dto.CategoriaDto;
import com.compass.bicoon.dto.CategoriaFormDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.net.URI;

public interface CategoriaService {

    Page<CategoriaDto> listarCategorias(Pageable paginacao);

    URI cadastrarCategoria(CategoriaFormDto categoriaForm);

    CategoriaDto atualizarCategoria(Long id, CategoriaFormDto categoriaForm);

    void deletarCategoria(Long id);
}
