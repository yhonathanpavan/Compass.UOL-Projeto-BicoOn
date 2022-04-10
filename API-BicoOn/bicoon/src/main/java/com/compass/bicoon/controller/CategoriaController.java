package com.compass.bicoon.controller;

import com.compass.bicoon.dto.CategoriaDto;
import com.compass.bicoon.dto.CategoriaFormDto;
import com.compass.bicoon.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bicoon/categoria")
public class CategoriaController {

    @Autowired
    CategoriaService categoriaService;

    @GetMapping
    public ResponseEntity<Page<CategoriaDto>> listarCategorias(@PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable paginacao){
        return ResponseEntity.ok().body(categoriaService.listarCategorias(paginacao));
    }

    @PostMapping
    public ResponseEntity<CategoriaDto> cadastrarCategoria(@RequestBody CategoriaFormDto categoriaForm){
        return ResponseEntity.created(categoriaService.cadastrarCategoria(categoriaForm)).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaDto> atualizarCategoria(@PathVariable Long id, @RequestBody CategoriaFormDto categoriaForm){
        return ResponseEntity.ok().body(categoriaService.atualizarCategoria(id, categoriaForm));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarCategoria(@PathVariable Long id){
       return ResponseEntity.ok().body(categoriaService.deletarCategoria(id));
    }

}
