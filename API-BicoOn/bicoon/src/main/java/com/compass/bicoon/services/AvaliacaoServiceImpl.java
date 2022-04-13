package com.compass.bicoon.services;

import com.compass.bicoon.config.ValidaConsulta;
import com.compass.bicoon.dto.AvaliacaoFormDto;
import com.compass.bicoon.entities.Avaliacao;
import com.compass.bicoon.entities.Cliente;
import com.compass.bicoon.entities.Prestador;
import com.compass.bicoon.repository.AvaliacaoRepository;
import com.compass.bicoon.repository.ClienteRepository;
import com.compass.bicoon.repository.PrestadorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Service
public class AvaliacaoServiceImpl implements AvaliacaoService{

    @Autowired
    PrestadorRepository prestadorRepository;

    @Autowired
    ClienteRepository clienteRespository;

    @Autowired
    AvaliacaoRepository avaliacaoRepository;

    @Autowired
    ModelMapper mapper;

    @Autowired
    ValidaConsulta validaConsulta;

    @Override
    public URI criarAvaliacao(Long clienteId, Long prestadorId, AvaliacaoFormDto avaliacaoFormDto){
        Cliente cliente = clienteRespository.getById(clienteId);
        Prestador prestador = prestadorRepository.getById(prestadorId);

        Avaliacao avaliacao = mapper.map(avaliacaoFormDto, Avaliacao.class);
        avaliacaoRepository.save(avaliacao);
        avaliacao.setClienteId(cliente.getId());

        prestador.getAvaliacao().add(avaliacao);
        prestadorRepository.save(prestador);

        return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(avaliacao.getId()).toUri();
    }

    @Override
    public AvaliacaoFormDto atualizarAvaliacao(Long id, AvaliacaoFormDto avaliacaoFormDto){
        Avaliacao avaliacao = validaConsulta.verificaExistenciaAvaliacao(id);
        Long clienteId = avaliacao.getClienteId();

        avaliacao = mapper.map(avaliacaoFormDto, Avaliacao.class);
        avaliacao.setId(id);

        avaliacao.setClienteId(clienteId);

        avaliacaoRepository.save(avaliacao);

        return mapper.map(avaliacao, AvaliacaoFormDto.class);
    }

    @Override
    public void deletarAvaliacao(Long id){
        Avaliacao avaliacao = validaConsulta.verificaExistenciaAvaliacao(id);

        avaliacaoRepository.deleteById(id);
    }
}
