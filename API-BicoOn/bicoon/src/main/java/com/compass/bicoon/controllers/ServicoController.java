package com.compass.bicoon.controllers;

import com.compass.bicoon.dto.servico.ServicoDto;
import com.compass.bicoon.dto.servico.ServicoFormDto;
import com.compass.bicoon.services.servico.ServicoService;
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
@RequestMapping("/bicoon/servicos")
public class ServicoController {

    @Autowired
    ServicoService servicoService;

    @GetMapping
    public ResponseEntity<Page<ServicoDto>> listarServicos(@PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable paginacao){
        return ResponseEntity.ok().body(servicoService.listarServicos(paginacao));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<ServicoDto> atualizarServico(@PathVariable Long id, @Valid @RequestBody ServicoFormDto servicoFormDto){
        return ResponseEntity.ok().body(servicoService.atualizarServico(id, servicoFormDto));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> deletarServico(@PathVariable Long id){
        servicoService.deletarServico(id);
        return ResponseEntity.ok().build();
    }

}
