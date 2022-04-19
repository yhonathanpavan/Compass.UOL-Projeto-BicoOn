package com.compass.bicoon.services;

import com.compass.bicoon.dto.*;
import com.compass.bicoon.entities.Avaliacao;
import com.compass.bicoon.entities.Categoria;
import com.compass.bicoon.entities.Prestador;
import com.compass.bicoon.entities.Servico;
import com.compass.bicoon.exceptions.ObjectNotFound.ObjectNotFoundException;
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
    ModelMapper mapper;

    @Autowired
    ServicoRepository servicoRepository;

    @Autowired
    CategoriaService categoriaService;

    @Override
    public URI cadastrarPrestador(PrestadorFormDto prestadorFormDto) {
        Prestador prestador = mapper.map(prestadorFormDto, Prestador.class);
        prestador.setDisponivel(true);
        prestadorRepository.save(prestador);
        PrestadorDto prestadorDto = mapper.map(prestador, PrestadorDto.class);

        return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(prestadorDto.getId()).toUri();
    }

    @Override
    public PrestadorDto atualizarPrestador(Long id, PrestadorFormDto prestadorFormDto) {
        verificaExistenciaPrestador(id);
        Prestador prestador = mapper.map(prestadorFormDto, Prestador.class);
        prestador.setId(id);
        prestadorRepository.save(prestador);

        return mapper.map(prestador, PrestadorDto.class);
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
        Page<PrestadorDto> prestadorDto = new PageImpl<>(prestador.stream().map(element -> mapper.map(element
                , PrestadorDto.class)).collect(Collectors.toList()));

        return prestadorDto;
    }

    @Override
    public PrestadorDto listarPorId(Long id) {
        Prestador prestador = verificaExistenciaPrestador(id);
        return mapper.map(prestador, PrestadorDto.class);
    }

    @Override
    public Page<ServicoDto> listarServicosPrestador(Long id) {
        Prestador prestador = verificaExistenciaPrestador(id);
        List<Servico> servicos = prestador.getServico();
        Page<ServicoDto> servicosDto = new PageImpl<>(servicos.stream().map(element -> mapper.map(element
                , ServicoDto.class)).collect(Collectors.toList()));

        return servicosDto;
    }

    @Override
    public Page<AvaliacaoDto> listarAvaliacoesPrestador(Long id) {
        Prestador prestador = verificaExistenciaPrestador(id);
        List<Avaliacao> avaliacoes = prestador.getAvaliacao();
        Page<AvaliacaoDto> avaliacoesDto = new PageImpl<>(avaliacoes.stream().map(element -> mapper.map(element
                , AvaliacaoDto.class)).collect(Collectors.toList()));

        return avaliacoesDto;
    }

    @Override
    public ServicoDto cadastrarServico(Long id, ServicoFormDto servicoForm) {

        Prestador prestador = verificaExistenciaPrestador(id);
        Categoria categoria = categoriaService.verificaExistenciaCategoria(servicoForm.getCategoria());

        Servico servico = mapper.map(servicoForm, Servico.class);
        servico.setCategoria(categoria);
        servicoRepository.save(servico);

        prestador.getServico().add(servico); //Adicionando na lista
        prestadorRepository.save(prestador);

        return mapper.map(servico, ServicoDto.class);
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


    public Prestador verificaExistenciaPrestador(Long id) {
        Optional<Prestador> prestadorOptional = prestadorRepository.findById(id);
        if(prestadorOptional.isPresent()){
            return prestadorOptional.get();
        }
        throw new ObjectNotFoundException("Prestador não encontrado");
    }

    @Override
    public PrestadorDto atualizarDisponibilidadePrestador(Long id, PrestadorDisponibilidadeFormDto prestadorDispForm) {
        Prestador prestador = verificaExistenciaPrestador(id);
        prestador.setDisponivel(prestadorDispForm.getDisponivel());
        prestadorRepository.save(prestador);

        return mapper.map(prestador, PrestadorDto.class);
    }
}