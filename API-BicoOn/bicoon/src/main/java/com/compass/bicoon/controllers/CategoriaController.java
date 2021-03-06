package com.compass.bicoon.controllers;

import com.compass.bicoon.dto.categoria.CategoriaDto;
import com.compass.bicoon.dto.categoria.CategoriaFormDto;
import com.compass.bicoon.services.categoria.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/bicoon/categorias")
public class CategoriaController {

    @Autowired
    CategoriaService categoriaService;

    @GetMapping
    public ResponseEntity<Page<CategoriaDto>> listarCategorias(@PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable paginacao){
        return ResponseEntity.ok().body(categoriaService.listarCategorias(paginacao));
    }

    @PostMapping
    @Transactional
    public ResponseEntity<CategoriaDto> cadastrarCategoria(@Valid @RequestBody CategoriaFormDto categoriaForm){
        return ResponseEntity.created(categoriaService.cadastrarCategoria(categoriaForm)).build();
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<CategoriaDto> atualizarCategoria(@PathVariable Long id, @Valid @RequestBody CategoriaFormDto categoriaForm){
        return ResponseEntity.ok().body(categoriaService.atualizarCategoria(id, categoriaForm));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> deletarCategoria(@PathVariable Long id){
        categoriaService.deletarCategoria(id);
        return ResponseEntity.ok().build();
    }

}
