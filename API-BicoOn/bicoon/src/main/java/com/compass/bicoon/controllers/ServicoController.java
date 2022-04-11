package com.compass.bicoon.controllers;

import com.compass.bicoon.dto.ServicoDto;
import com.compass.bicoon.dto.ServicoFormDto;
import com.compass.bicoon.services.ServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bicoon/servico")
public class ServicoController {

    @Autowired
    ServicoService servicoService;

    @GetMapping
    public ResponseEntity<Page<ServicoDto>> listarServicos(@PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable paginacao){
        return ResponseEntity.ok().body(servicoService.listarServicos(paginacao));
    }

    @PostMapping
    public ResponseEntity<ServicoDto> cadastrarServico(@RequestBody ServicoFormDto servicoFormDto){
        return ResponseEntity.created(servicoService.cadastrarServico(servicoFormDto)).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServicoDto> atualizarServico(@PathVariable Long id, @RequestBody ServicoFormDto servicoFormDto){
        return ResponseEntity.ok().body(servicoService.atualizarServico(id, servicoFormDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarServico(@PathVariable Long id){
        return ResponseEntity.ok().body(servicoService.deletarServico(id));
    }

    @DeleteMapping("/{idServico}/categoria/{idCategoria}")
    public ResponseEntity<String> deletarCategoriaDoServico(@PathVariable Long idServico, @PathVariable Long idCategoria){
        return ResponseEntity.ok().body(servicoService.deletarCategoriaDoServico(idServico, idCategoria));
    }
}