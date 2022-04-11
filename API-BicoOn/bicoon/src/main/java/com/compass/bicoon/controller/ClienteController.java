package com.compass.bicoon.controller;

import com.compass.bicoon.dto.ClienteDto;
import com.compass.bicoon.dto.ClienteFormDto;
import com.compass.bicoon.entities.Cliente;
import com.compass.bicoon.repository.ClienteRespository;
import com.compass.bicoon.services.ClienteService;
import com.compass.bicoon.services.ClienteServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/bicoon/cliente")
public class ClienteController {

    @Autowired
    ClienteServiceImpl clienteService;

    @Autowired
    ModelMapper mapper;

    @GetMapping
    public ResponseEntity<Page<ClienteDto>> listarClientes(@RequestParam(required = false) String cidade,
                                                          @PageableDefault(sort = "id", direction = Sort.Direction.ASC, size = 100) Pageable paginacao){
        return ResponseEntity.ok().body(clienteService.findAll(cidade, paginacao));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDto> detalhesCliente(@PathVariable Long id){
        return ResponseEntity.ok().body(clienteService.findById(id));
    }

    @Transactional
    @PostMapping
    public ResponseEntity<ClienteFormDto> cadastrarCliente(@RequestBody ClienteFormDto clienteFormDto){
        return ResponseEntity.created(clienteService.create(clienteFormDto)).build();
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<ClienteDto> atualizarCliente(@PathVariable Long id, @RequestBody ClienteFormDto clienteFormDto){
        return ResponseEntity.ok().body(clienteService.update(id, clienteFormDto));
    }

    @Transactional
    @DeleteMapping("/{id}")
    public String deletarCliente(@PathVariable Long id){
        return clienteService.delete(id);
    }
}
