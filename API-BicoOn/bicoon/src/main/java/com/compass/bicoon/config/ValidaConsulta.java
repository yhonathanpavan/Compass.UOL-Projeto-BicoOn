package com.compass.bicoon.config;

import com.compass.bicoon.entities.Categoria;
import com.compass.bicoon.entities.Prestador;
import com.compass.bicoon.entities.Servico;
import com.compass.bicoon.repository.CategoriaRepository;
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


    public Categoria verificaExistenciaCategoria(String categoria) {
        Optional<Categoria> categoriaOptional = categoriaRepository.findByNome(categoria);
        if(categoriaOptional.isPresent()){
            return  categoriaOptional.get();
        }else{
            throw new RuntimeException("Categoria não existe");
        }
    }

    public Servico verificaExistenciaServico(Long id) {
        Optional<Servico> servicoOptional = servicoRepository.findById(id);
        if(servicoOptional.isPresent()){
            return servicoOptional.get();
        }else {
            throw new RuntimeException();
        }
    }

    public Prestador verificaExistenciaPrestador(Long id) {
        Optional<Prestador> prestadorOptional = prestadorRepository.findById(id);
        if(prestadorOptional.isPresent()){
            return prestadorOptional.get();
        }else {
            throw new RuntimeException();
        }

    }

}
