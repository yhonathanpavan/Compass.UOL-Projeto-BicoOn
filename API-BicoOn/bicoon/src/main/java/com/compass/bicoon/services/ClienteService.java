package com.compass.bicoon.services;

import com.compass.bicoon.dto.ClienteDto;
import com.compass.bicoon.dto.ClienteFormDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.net.URI;

public interface ClienteService {
    Page<ClienteDto> findAll(String cidade, Pageable paginacao);

    ClienteDto findById(Long id);

    URI cadastrarCliente(ClienteFormDto clienteFormDto);

    ClienteDto atualizarCliente(Long id, ClienteFormDto clienteFormDto);

    void deletarCliente(Long id);
}
