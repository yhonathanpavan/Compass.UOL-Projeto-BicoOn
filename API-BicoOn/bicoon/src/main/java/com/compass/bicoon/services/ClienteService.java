package com.compass.bicoon.services;

import com.compass.bicoon.dto.ClienteDto;
import com.compass.bicoon.dto.ClienteFormDto;
import com.compass.bicoon.entities.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

public interface ClienteService {
    Page<ClienteDto> findAll(String cidade, Pageable paginacao);

    ClienteDto findById(Long id);

    ClienteDto create(ClienteFormDto clienteFormDto, UriComponentsBuilder uriBuilder);

    ClienteDto update(Long id, ClienteFormDto clienteFormDto);

    String delete(Long id);
}
