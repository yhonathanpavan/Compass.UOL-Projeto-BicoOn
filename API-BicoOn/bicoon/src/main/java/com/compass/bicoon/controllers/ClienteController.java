package com.compass.bicoon.controllers;

import com.compass.bicoon.dto.ClienteDto;
import com.compass.bicoon.dto.ClienteFormDto;
import com.compass.bicoon.services.ClienteServiceImpl;
import org.modelmapper.ModelMapper;
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
@RequestMapping("/bicoon/clientes")
public class ClienteController {

    @Autowired
    ClienteServiceImpl clienteService;

    @Autowired
    ModelMapper mapper;

    @GetMapping
    public ResponseEntity<Page<ClienteDto>> listarClientes(@RequestParam(required = false) String cidade,
                                                          @PageableDefault(sort = "id", direction = Sort.Direction.ASC, size = 100) Pageable paginacao){
        return ResponseEntity.ok().body(clienteService.listarClientes(cidade, paginacao));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDto> detalhesCliente(@PathVariable Long id){
        return ResponseEntity.ok().body(clienteService.listarPorId(id));
    }

    @Transactional
    @PostMapping
    public ResponseEntity<ClienteFormDto> cadastrarCliente(@Valid  @RequestBody ClienteFormDto clienteFormDto){
        return ResponseEntity.created(clienteService.cadastrarCliente(clienteFormDto)).build();
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<ClienteDto> atualizarCliente(@PathVariable Long id, @Valid @RequestBody ClienteFormDto clienteFormDto){
        return ResponseEntity.ok().body(clienteService.atualizarCliente(id, clienteFormDto));
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarCliente(@PathVariable Long id){
        clienteService.deletarCliente(id);
        return ResponseEntity.ok().build();
    }
}
