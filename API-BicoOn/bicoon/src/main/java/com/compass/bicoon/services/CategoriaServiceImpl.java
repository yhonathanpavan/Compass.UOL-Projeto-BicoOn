package com.compass.bicoon.services;

import com.compass.bicoon.config.ValidaConsulta;
import com.compass.bicoon.dto.CategoriaDto;
import com.compass.bicoon.dto.CategoriaFormDto;
import com.compass.bicoon.entities.Categoria;
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
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriaServiceImpl implements CategoriaService{

    @Autowired
    CategoriaRepository categoriaRepository;

    @Autowired
    ServicoRepository servicoRepository;

    @Autowired
    ModelMapper mapper;

    @Autowired
    ValidaConsulta validaConsulta;

    @Override
    public Page<CategoriaDto> listarCategorias(Pageable paginacao) {
        Page<Categoria> categoria = categoriaRepository.findAll(paginacao);
        Page<CategoriaDto> categoriaDto = new PageImpl<>(categoria.stream().map(e -> mapper.map(e, CategoriaDto.class)).collect(Collectors.toList()));
        return categoriaDto;
    }

    @Override
    public URI cadastrarCategoria(CategoriaFormDto categoriaForm) {
        Categoria categoria = mapper.map(categoriaForm, Categoria.class);
        categoriaRepository.save(categoria);
        return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(categoria.getId()).toUri();
    }

    @Override
    public CategoriaDto atualizarCategoria(Long id, CategoriaFormDto categoriaForm){
            validaConsulta.verificaExistenciaCategoria(id);

            Categoria categoria = mapper.map(categoriaForm, Categoria.class);
            categoria.setId(id);
            categoriaRepository.save(categoria);
            return mapper.map(categoria, CategoriaDto.class);
    }

    @Override
    public void deletarCategoria(Long id) {

        Categoria categoria = validaConsulta.verificaExistenciaCategoria(id);
        List<Servico> servico = servicoRepository.findByCategoriaId(id);

        if(!servico.isEmpty()){ //Desvinculando categorias dos serviços
            servico.stream().forEach(e -> e.setCategoria(null));
        }

        categoriaRepository.deleteById(id);

    }

}
