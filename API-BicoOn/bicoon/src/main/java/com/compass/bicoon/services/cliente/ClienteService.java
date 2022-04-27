package com.compass.bicoon.services.cliente;

import com.compass.bicoon.dto.cliente.ClienteDto;
import com.compass.bicoon.dto.cliente.ClienteFormDto;
import com.compass.bicoon.entities.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.net.URI;

public interface ClienteService {
    Page<ClienteDto> listarClientes(String cidade, Pageable paginacao);

    ClienteDto listarPorId(Long id);

    URI cadastrarCliente(ClienteFormDto clienteFormDto);

    ClienteDto atualizarCliente(Long id, ClienteFormDto clienteFormDto);

    void deletarCliente(Long id);

    Cliente verificaExistenciaCliente(Long id);
    void verificaLogado(Long id);
}
