package com.compass.bicoon.services.cliente;

import com.compass.bicoon.dto.cliente.ClienteDto;
import com.compass.bicoon.dto.cliente.ClienteFormDto;
import com.compass.bicoon.entities.Cliente;
import com.compass.bicoon.entities.Prestador;
import com.compass.bicoon.exceptions.forbiddenAccess.ForbiddenAccessException;
import com.compass.bicoon.exceptions.objectNotFound.ObjectNotFoundException;
import com.compass.bicoon.repository.ClienteRepository;
import com.compass.bicoon.services.token.TokenService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    ModelMapper mapper;

    @Autowired
    TokenService tokenService;

    @Override
    public Page<ClienteDto> listarClientes(String cidade, Pageable paginacao) {
        Page<Cliente> cliente;

        if(cidade == null){
            cliente = clienteRepository.findAll(paginacao);
        } else {
            cliente = clienteRepository.findByCidade(cidade, paginacao);
        }

        Page<ClienteDto> clienteDto = new PageImpl<>(cliente.stream()                   //Sublista para não exibir o próprio administrador
                .map(e -> mapper.map(e, ClienteDto.class)).collect(Collectors.toList()).subList(1, cliente.stream().toList().size()));

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
        cliente.setSenha(new BCryptPasswordEncoder().encode(cliente.getSenha()));
        clienteRepository.save(cliente);
        return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(cliente.getId()).toUri();
    }

    @Override
    public ClienteDto atualizarCliente(Long id, ClienteFormDto clienteFormDto) {
        verificaExistenciaCliente(id);
        verificaLogado(id);
        Cliente cliente = mapper.map(clienteFormDto, Cliente.class);
        cliente.setId(id);
        cliente.setSenha(new BCryptPasswordEncoder().encode(cliente.getSenha()));
        clienteRepository.save(cliente);

        return mapper.map(cliente, ClienteDto.class);
    }

    @Override
    public void deletarCliente(Long id){
        verificaExistenciaCliente(id);
        verificaLogado(id);
        clienteRepository.deleteById(id);
    }

    public Cliente verificaExistenciaCliente(Long id) {
        Optional<Cliente> clienteOptional = clienteRepository.findById(id);
        if(clienteOptional.isPresent()){
            return clienteOptional.get();
        }
        throw new ObjectNotFoundException("Cliente não encontrado");
    }

    public void verificaLogado(Long id) {
        if(tokenService.getIdLogado() == id && tokenService.getTipoUsuarioLogado().equals(Cliente.class.toString())){
            return;
        }else{
            throw new ForbiddenAccessException("Usuário atual não está autorizado");
        }
    }
}
