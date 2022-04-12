package com.compass.bicoon.services;

import com.compass.bicoon.dto.*;
import com.compass.bicoon.entities.Avaliacao;
import com.compass.bicoon.entities.Categoria;
import com.compass.bicoon.entities.Prestador;
import com.compass.bicoon.entities.Servico;
import com.compass.bicoon.repository.CategoriaRepository;
import com.compass.bicoon.repository.PrestadorRepository;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PrestadorServiceImpl implements PrestadorService{

    @Autowired
    PrestadorRepository prestadorRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    ServicoRepository servicoRepository;

    @Autowired
    CategoriaRepository categoriaRepository;

    @Override
    public URI cadastrarPrestador(PrestadorForm prestadorForm) {
        Prestador prestador = prestadorRepository.save(modelMapper.map(prestadorForm, Prestador.class));
        PrestadorDto prestadorDto = modelMapper.map(prestador, PrestadorDto.class);
        return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(prestadorDto.getId()).toUri();
    }

    @Override
    public PrestadorDto atualizarPrestador(Long id,PrestadorForm prestadorForm) {
        Prestador prestador = verificaExistenciaPrestador(id);
        try {
            prestador = modelMapper.map(prestadorForm, Prestador.class);
            prestador.setId(id);
            prestadorRepository.save(prestador);
            return modelMapper.map(prestador, PrestadorDto.class);

        }catch (Exception e){
            throw new RuntimeException();
        }
    }

    @Override
    public void deletaPrestador(Long id) {
        verificaExistenciaPrestador(id);
        prestadorRepository.deleteById(id);
    }

    @Override
    public Page<PrestadorDto> listarPrestadores(Pageable paginacao, String cidade, String categoria){

        Page<Prestador> prestador;
        prestador = defineRetorno(paginacao, cidade, categoria);

        Page<PrestadorDto> prestadorDto = new PageImpl<>(prestador.stream().map(element -> modelMapper.map(element
                , PrestadorDto.class)).collect(Collectors.toList()));

        return prestadorDto;
    }

    @Override
    public PrestadorDto listarPorId(Long id) {
        Prestador prestador = verificaExistenciaPrestador(id);
        return modelMapper.map(prestador, PrestadorDto.class);
    }

    @Override
    public List<ServicoDto> listarServicosPrestador(Long id) {
        Prestador prestador = verificaExistenciaPrestador(id);
        List<Servico> servicos = prestador.getServico();

        List<ServicoDto> servicoDto = servicos.stream()
                .map(servico -> modelMapper.map(servico, ServicoDto.class))
                .collect(Collectors.toList());

        return servicoDto;
    }

    @Override
    public List<AvaliacaoDto> listarAvaliacoesPrestador(Long id) {
        Prestador prestador = verificaExistenciaPrestador(id);
        List<Avaliacao> avaliacoes = prestador.getAvaliacao();

        List<AvaliacaoDto> avaliacaoDto = avaliacoes.stream()
                .map(avaliacao -> modelMapper.map(avaliacao, AvaliacaoDto.class))
                .collect(Collectors.toList());

        return avaliacaoDto;
    }

    @Override
    public ServicoDto cadastrarServico(Long id, ServicoFormDto servicoForm) {

        Prestador prestador = verificaExistenciaPrestador(id);
        Categoria categoria = verificaExistenciaCategoria(servicoForm.getCategoria());

        Servico servico = new Servico();
        servico.setCategoria(categoria);
        servico.setDescricao(servicoForm.getDescricao());
        servicoRepository.save(servico);

        prestador.getServico().add(servico);
        prestadorRepository.save(prestador);

        return modelMapper.map(servico, ServicoDto.class);
    }

    private Categoria verificaExistenciaCategoria(String categoria) {
        Optional<Categoria> categoriaOptional = categoriaRepository.findByNome(categoria);
        if(categoriaOptional.isPresent()){
            return  categoriaOptional.get();
        }else{
            throw new RuntimeException();
        }
    }


    private Prestador verificaExistenciaPrestador(Long id) {
        Optional<Prestador> prestadorOptional = prestadorRepository.findById(id);
        if(prestadorOptional.isPresent()){
            return prestadorOptional.get();
        }else {
            throw new RuntimeException();
        }
    }

    private Page<Prestador> defineRetorno(Pageable paginacao, String cidade, String categoria) {
        if(cidade==null && categoria==null){
            return prestadorRepository.findAll(paginacao);
        }else if(cidade != null && categoria == null){
            return prestadorRepository.findByCidade(paginacao, cidade);
        }else if(categoria != null && cidade == null){
            return prestadorRepository.findByServicoCategoriaNome(paginacao, categoria);
        }else{
            return prestadorRepository.findByCidadeAndServicoCategoriaNome(paginacao, cidade, categoria);
        }
    }
}