package com.compass.bicoon.services.categoria;

import com.compass.bicoon.dto.categoria.CategoriaDto;
import com.compass.bicoon.dto.categoria.CategoriaFormDto;
import com.compass.bicoon.entities.Categoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.net.URI;

public interface CategoriaService {

    Page<CategoriaDto> listarCategorias(Pageable paginacao);

    URI cadastrarCategoria(CategoriaFormDto categoriaForm);

    CategoriaDto atualizarCategoria(Long id, CategoriaFormDto categoriaForm);

    void deletarCategoria(Long id);

    Categoria verificaExistenciaCategoria(String categoria);

    Categoria verificaExistenciaCategoria(Long id);
}
