package com.compass.bicoon.config;

import com.compass.bicoon.entities.Categoria;
import com.compass.bicoon.entities.Cliente;
import com.compass.bicoon.entities.Prestador;
import com.compass.bicoon.entities.Servico;
import com.compass.bicoon.exceptions.ObjectNotFound.ObjectNotFoundException;
import com.compass.bicoon.repository.CategoriaRepository;
import com.compass.bicoon.repository.ClienteRepository;
import com.compass.bicoon.repository.PrestadorRepository;
import com.compass.bicoon.repository.ServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

@Configuration
public class ValidaConsulta {

    @Autowired
    CategoriaRepository categoriaRepository;

    @Autowired
    ServicoRepository servicoRepository;

    @Autowired
    PrestadorRepository prestadorRepository;

    @Autowired
    ClienteRepository clienteRepository;

    public Categoria verificaExistenciaCategoria(String categoria) {
        Optional<Categoria> categoriaOptional = categoriaRepository.findByNome(categoria);
        if(categoriaOptional.isPresent()){
            return  categoriaOptional.get();
        }else{
            throw new ObjectNotFoundException("Categoria não encontrada");
        }
    }

    public Categoria verificaExistenciaCategoria(Long id) {
        Optional<Categoria> categoriaOptional = categoriaRepository.findById(id);
        if(categoriaOptional.isPresent()){
            return categoriaOptional.get();
        }else {
            throw new ObjectNotFoundException("Categoria não encontrada");
        }
    }

    public Servico verificaExistenciaServico(Long id) {
        Optional<Servico> servicoOptional = servicoRepository.findById(id);
        if(servicoOptional.isPresent()){
            return servicoOptional.get();
        }else {
            throw new ObjectNotFoundException("Serviço não encontrado");
        }
    }

    public Prestador verificaExistenciaPrestador(Long id) {
        Optional<Prestador> prestadorOptional = prestadorRepository.findById(id);
        if(prestadorOptional.isPresent()){
            return prestadorOptional.get();
        }else {
            throw new ObjectNotFoundException("Prestador não encontrado");
        }
    }

    public Cliente verificaExistenciaCliente(Long id) {
        Optional<Cliente> clienteOptional = clienteRepository.findById(id);
        if(clienteOptional.isPresent()){
            return clienteOptional.get();
        }else {
            throw new ObjectNotFoundException("Cliente não encontrado");
        }
    }

}
