package com.compass.bicoon.services;

import com.compass.bicoon.dto.ServicoDto;
import com.compass.bicoon.dto.ServicoFormDto;
import com.compass.bicoon.entities.Categoria;
import com.compass.bicoon.entities.Prestador;
import com.compass.bicoon.entities.Servico;
import com.compass.bicoon.repository.CategoriaRepository;
import com.compass.bicoon.repository.ServicoRepository;
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
public class ServicoServiceImpl implements ServicoService{

    @Autowired
    ServicoRepository servicoRepository;

    @Autowired
    CategoriaRepository categoriaRepository;

    @Autowired
    ModelMapper mapper;


    @Override
    public Page<ServicoDto> listarServicos(Pageable paginacao) {
        Page<Servico> categoria = servicoRepository.findAll(paginacao);
        Page<ServicoDto> servicosDto = new PageImpl<>(categoria.stream().map(e -> mapper.map(e, ServicoDto.class)).collect(Collectors.toList()));
        return servicosDto;
    }

    @Override
    public URI cadastrarServico(ServicoFormDto servicoForm) {

        Optional<Categoria> categoria = categoriaRepository.findByNome(servicoForm.getCategoria());

        if(categoria.isPresent()) {
            Servico servico = mapper.map(servicoForm, Servico.class);
            servico.setCategoria(categoria.get());
            servicoRepository.save(servico);
            return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(servico.getId()).toUri();
        }

        throw new RuntimeException("Categoria não existe!");
    }

    @Override
    public ServicoDto atualizarServico(Long id, ServicoFormDto servicoForm) {
        Optional<Servico> servicoOptional = servicoRepository.findById(id);

        if(servicoOptional.isPresent()){
            Servico servico = mapper.map(servicoForm, Servico.class);
            servico.setId(id);
            servicoRepository.save(servico);
            return mapper.map(servico, ServicoDto.class);
        }

        //Jogar exception
        return null;
    }

    @Override
    public String deletarServico(Long id) {
        Optional<Servico> servico = servicoRepository.findById(id);

        if(servico.isPresent()){
            servicoRepository.deleteById(id);
            return "Serviço excluído com sucesso!";
        }

        //Jogar exception
        return null;
    }

    @Override
    public String deletarCategoriaDoServico(Long idServico, Long idCategoria) {
        Optional<Categoria> categoria = categoriaRepository.findById(idCategoria);
        Optional<Servico> servico = servicoRepository.findById(idServico);

        if(servico.isPresent() && categoria.isPresent() && servico.get().getCategoria() != null){
            servico.get().setCategoria(null);
            servicoRepository.save(servico.get());
            return "Categoria excluída com sucesso!";
        }
        throw new RuntimeException("Não foi encontrado vínculo entre categoria eserviço selecionado!");
    }


}
