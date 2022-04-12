package com.compass.bicoon.controllers;

import com.compass.bicoon.dto.*;
import com.compass.bicoon.entities.Servico;
import com.compass.bicoon.services.PrestadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/bicoon/prestador")
public class PrestadorController {

    @Autowired
    PrestadorService prestadorService;

    @PostMapping
    public ResponseEntity<URI> cadastrarPrestador(@RequestBody PrestadorForm prestador){
        return ResponseEntity.created(prestadorService.cadastrarPrestador(prestador)).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<PrestadorDto> atualizarPrestador(@PathVariable Long id, @Valid @RequestBody PrestadorForm prestadorForm){
        return ResponseEntity.ok().body(prestadorService.atualizarPrestador(id, prestadorForm));
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity deletarPrestador(@PathVariable Long id){
        prestadorService.deletaPrestador(id);
        return ResponseEntity.ok().body("Deletado Com Sucesso");
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

    @GetMapping("/{id}/servico")
    public ResponseEntity<List<ServicoDto>> listarCategoriaPrestador(@PathVariable Long id){
        return ResponseEntity.ok().body(prestadorService.listarServicosPrestador(id));
    }

    @GetMapping("/{id}/avaliacoes")
    public ResponseEntity<List<AvaliacaoDto>> listarAvaliacoesPrestador(@PathVariable Long id){
        return ResponseEntity.ok().body(prestadorService.listarAvaliacoesPrestador(id));
    }

    @PutMapping ("/{id}/servico")
    public ResponseEntity<ServicoDto> cadastraServicosPrestador(@PathVariable Long id, @Valid @RequestBody ServicoFormDto servicoForm){
        return ResponseEntity.ok().body(prestadorService.cadastrarServico(id,servicoForm));

    }

}
