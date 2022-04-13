package com.compass.bicoon.services;

import com.compass.bicoon.config.ValidaConsulta;
import com.compass.bicoon.dto.ClienteDto;
import com.compass.bicoon.dto.ClienteFormDto;
import com.compass.bicoon.entities.Cliente;
import com.compass.bicoon.repository.ClienteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.stream.Collectors;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    ModelMapper mapper;

    @Autowired
    ValidaConsulta validaConsulta;

    @Override
    public Page<ClienteDto> findAll(String cidade, Pageable paginacao) {
        Page<Cliente> cliente;

        if(cidade == null){
            cliente = clienteRepository.findAll(paginacao);
        } else {
            cliente = clienteRepository.findByCidade(cidade, paginacao);
        }
        Page<ClienteDto> clienteDto = new PageImpl<>(cliente.stream()
                .map(e -> mapper.map(e, ClienteDto.class)).collect(Collectors.toList()));

        return clienteDto;
    }

    @Override
    public ClienteDto findById(Long id){
        Cliente cliente = validaConsulta.verificaExistenciaCliente(id);

        return mapper.map(cliente, ClienteDto.class);
    }

    @Override
    public URI cadastrarCliente(ClienteFormDto clienteFormDto){
        Cliente cliente = mapper.map(clienteFormDto, Cliente.class);
        clienteRepository.save(cliente);
        return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(cliente.getId()).toUri();
    }

    @Override
    public ClienteDto atualizarCliente(Long id, ClienteFormDto clienteFormDto) {
        validaConsulta.verificaExistenciaCliente(id);

        Cliente cliente = mapper.map(clienteFormDto, Cliente.class);
        cliente.setId(id);
        clienteRepository.save(cliente);

        return mapper.map(cliente, ClienteDto.class);
    }

    @Override
    public void deletarCliente(Long id){
        Cliente cliente = validaConsulta.verificaExistenciaCliente(id);

        clienteRepository.deleteById(id);
    }
}
