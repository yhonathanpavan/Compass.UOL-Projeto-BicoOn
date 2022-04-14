package com.compass.bicoon.controllers;

import com.compass.bicoon.dto.*;
import com.compass.bicoon.services.PrestadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;


@RestController
@RequestMapping("/bicoon/prestadores")
public class PrestadorController {

    @Autowired
    PrestadorService prestadorService;

    @PostMapping
    @Transactional
    public ResponseEntity<URI> cadastrarPrestador(@Valid @RequestBody PrestadorFormDto prestador){
        return ResponseEntity.created(prestadorService.cadastrarPrestador(prestador)).build();
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<PrestadorDto> atualizarPrestador(@PathVariable Long id, @Valid @RequestBody PrestadorFormDto prestadorFormDto){
        return ResponseEntity.ok().body(prestadorService.atualizarPrestador(id, prestadorFormDto));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public  ResponseEntity<?> deletarPrestador(@PathVariable Long id){
        prestadorService.deletaPrestador(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Page<PrestadorDto>> listarPrestadores(@PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable paginacao
            , @RequestParam(required = false) String cidade, @RequestParam(required = false) String categoria){
        return ResponseEntity.ok().body(prestadorService.listarPrestadores(paginacao, cidade, categoria));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PrestadorDto> listarPrestadores(@PathVariable Long id){
        return ResponseEntity.ok().body(prestadorService.listarPorId(id));
    }

    @GetMapping("/{id}/servicos")
    public ResponseEntity<Page<ServicoDto>> listarCategoriaPrestador(@PathVariable Long id){
        return ResponseEntity.ok().body(prestadorService.listarServicosPrestador(id));
    }

    @GetMapping("/{id}/avaliacoes")
    public ResponseEntity<Page<AvaliacaoDto>> listarAvaliacoesPrestador(@PathVariable Long id){
        return ResponseEntity.ok().body(prestadorService.listarAvaliacoesPrestador(id));
    }

    @PutMapping ("/{id}/servicos")
    @Transactional
    public ResponseEntity<ServicoDto> cadastraServicosPrestador(@PathVariable Long id, @Valid @RequestBody ServicoFormDto servicoForm){
        return ResponseEntity.ok().body(prestadorService.cadastrarServico(id,servicoForm));

    }

    @PutMapping ("/{id}/disponibilidade")
    @Transactional
    public ResponseEntity<PrestadorDto> atualizarDisponibilidadePrestador(@PathVariable Long id, @Valid @RequestBody PrestadorDisponibilidadeFormDto prestadorDispForm){
        return ResponseEntity.ok().body(prestadorService.atualizarDisponibilidadePrestador(id, prestadorDispForm));
    }

}
