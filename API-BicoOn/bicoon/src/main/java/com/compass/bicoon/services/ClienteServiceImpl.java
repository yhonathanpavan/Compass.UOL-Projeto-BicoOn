package com.compass.bicoon.services;

import ch.qos.logback.core.net.server.Client;
import com.compass.bicoon.dto.ClienteDto;
import com.compass.bicoon.dto.ClienteFormDto;
import com.compass.bicoon.entities.Cliente;
import com.compass.bicoon.repository.ClienteRespository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    ClienteRespository clienteRepository;

    @Autowired
    ModelMapper mapper;

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
        Optional<Cliente> cliente = clienteRepository.findById(id);
        if(cliente.isPresent()){
            return mapper.map(cliente.get(), ClienteDto.class);
        }
        throw new RuntimeException("Não foi possível encontrar o ID desejado");
    }

    @Override
    public ClienteDto create(ClienteFormDto clienteFormDto, UriComponentsBuilder uriBuilder){
        Cliente cliente = mapper.map(clienteFormDto, Cliente.class);
        clienteRepository.save(cliente);


    }

    @Override
    public ClienteDto update(Long id, ClienteFormDto clienteFormDto) {
        Optional<Cliente> clienteOpcional= clienteRepository.findById(id);
        if(clienteOpcional.isPresent()){
            Cliente cliente = mapper.map(clienteFormDto, Cliente.class);
            cliente.setId(id);
            clienteRepository.save(cliente);

            return mapper.map(cliente, ClienteDto.class);
        }else{
            throw new RuntimeException("Não foi possível encontrar o ID desejado");
        }
    }

    @Override
    public String delete(Long id){
        Optional<Cliente> clienteOpcional = clienteRepository.findById(id);
        if(clienteOpcional.isPresent()){
            clienteRepository.deleteById(id);
            return("Cliente deletado com sucesso!");
        }else{
            return("Cliente não encontrado");
        }

    }

}
