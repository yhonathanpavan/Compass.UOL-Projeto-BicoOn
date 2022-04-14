package com.compass.bicoon.controllers;

import com.compass.bicoon.dto.AvaliacaoFormDto;
import com.compass.bicoon.services.AvaliacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/bicoon/avaliacoes")
public class AvaliacaoController {

    @Autowired
    AvaliacaoService avaliacaoService;

    @Transactional
    @PostMapping("/clientes/{clienteId}/prestadores/{prestadorId}")
    public ResponseEntity<AvaliacaoFormDto> adicionarAvaliacao(@PathVariable Long clienteId, @PathVariable Long prestadorId,
                                                               @Valid @RequestBody AvaliacaoFormDto avaliacaoFormDto){

        return ResponseEntity.created(avaliacaoService.criarAvaliacao(clienteId, prestadorId, avaliacaoFormDto)).build();
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<AvaliacaoFormDto> atualizarAvaliacao(@PathVariable Long id, @Valid @RequestBody AvaliacaoFormDto avaliacaoFormDto){
        return ResponseEntity.ok().body(avaliacaoService.atualizarAvaliacao(id, avaliacaoFormDto));
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarAvaliacao(@PathVariable Long id){
        avaliacaoService.deletarAvaliacao(id);

        return ResponseEntity.ok().build();
    }
}
