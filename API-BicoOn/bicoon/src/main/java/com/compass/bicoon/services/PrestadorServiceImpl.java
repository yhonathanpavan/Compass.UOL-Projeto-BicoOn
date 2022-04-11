package com.compass.bicoon.services;

import com.compass.bicoon.dto.AvaliacaoDto;
import com.compass.bicoon.dto.PrestadorDto;
import com.compass.bicoon.dto.PrestadorForm;
import com.compass.bicoon.dto.ServicoDto;
import com.compass.bicoon.entities.Avaliacao;
import com.compass.bicoon.entities.Prestador;
import com.compass.bicoon.entities.Servico;
import com.compass.bicoon.repository.PrestadorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PrestadorServiceImpl implements PrestadorService{

    @Autowired
    PrestadorRepository prestadorRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public PrestadorDto cadastrarPrestador(PrestadorForm prestadorForm) {
        Prestador prestador = prestadorRepository.save(modelMapper.map(prestadorForm, Prestador.class));
        return modelMapper.map(prestador, PrestadorDto.class);
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
    public Page<PrestadorDto> listarPrestadores(Pageable paginacao, String cidade){


        Page<Prestador> associado = prestadorRepository.findByCidade(paginacao, cidade);
        //Page<Prestador> associado = prestadorRepository.findAll(paginacao);


        Page<PrestadorDto> associadoDTO = new PageImpl<>(associado.stream().map(element -> modelMapper.map(element
                , PrestadorDto.class)).collect(Collectors.toList()));
        return associadoDTO;
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


    private Prestador verificaExistenciaPrestador(Long id) {
        Optional<Prestador> prestadorOptional = prestadorRepository.findById(id);
        if(prestadorOptional.isPresent()){
            return prestadorOptional.get();
        }else {
            throw new RuntimeException();
        }

    }


}