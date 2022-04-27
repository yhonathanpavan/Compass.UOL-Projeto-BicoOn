package com.compass.bicoon.services.cliente;

import com.compass.bicoon.dto.cliente.ClienteDto;
import com.compass.bicoon.dto.cliente.ClienteFormDto;
import com.compass.bicoon.entities.Cliente;
import com.compass.bicoon.exceptions.objectNotFound.ObjectNotFoundException;
import com.compass.bicoon.repository.ClienteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    ModelMapper mapper;

    @Override
    public Page<ClienteDto> listarClientes(String cidade, Pageable paginacao) {
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
    public ClienteDto listarPorId(Long id){
        Cliente cliente = verificaExistenciaCliente(id);

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
        verificaExistenciaCliente(id);

        Cliente cliente = mapper.map(clienteFormDto, Cliente.class);
        cliente.setId(id);
        clienteRepository.save(cliente);

        return mapper.map(cliente, ClienteDto.class);
    }

    @Override
    public void deletarCliente(Long id){
        verificaExistenciaCliente(id);

        clienteRepository.deleteById(id);
    }

    public Cliente verificaExistenciaCliente(Long id) {
        Optional<Cliente> clienteOptional = clienteRepository.findById(id);
        if(clienteOptional.isPresent()){
            return clienteOptional.get();
        }
        throw new ObjectNotFoundException("Cliente não encontrado");
    }
}
