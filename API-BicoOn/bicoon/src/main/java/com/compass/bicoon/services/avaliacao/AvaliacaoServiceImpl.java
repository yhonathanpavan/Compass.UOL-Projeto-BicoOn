package com.compass.bicoon.exceptions.services.avaliacao;

import com.compass.bicoon.dto.avaliacao.AvaliacaoFormDto;
import com.compass.bicoon.entities.Avaliacao;
import com.compass.bicoon.entities.Cliente;
import com.compass.bicoon.entities.Prestador;
import com.compass.bicoon.exceptions.forbiddenAccess.ForbiddenAccessException;
import com.compass.bicoon.exceptions.objectNotFound.ObjectNotFoundException;
import com.compass.bicoon.repository.AvaliacaoRepository;
import com.compass.bicoon.repository.PrestadorRepository;
import com.compass.bicoon.exceptions.services.cliente.ClienteService;
import com.compass.bicoon.exceptions.services.prestador.PrestadorService;
import com.compass.bicoon.exceptions.services.token.TokenService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class AvaliacaoServiceImpl implements AvaliacaoService{

    public static final String ADMINISTRADOR = "ROLE_ADMINISTRADOR";
    @Autowired
    PrestadorRepository prestadorRepository;

    @Autowired
    AvaliacaoRepository avaliacaoRepository;

    @Autowired
    ModelMapper mapper;

    @Autowired
    TokenService tokenService;

    @Autowired
    ClienteService clienteService;

    @Autowired
    PrestadorService prestadorService;


    @Override
    public URI criarAvaliacao(Long clienteId, Long prestadorId, AvaliacaoFormDto avaliacaoFormDto){
        Cliente cliente = clienteService.verificaExistenciaCliente(clienteId);
        Prestador prestador = prestadorService.verificaExistenciaPrestador(prestadorId);
        clienteService.verificaLogado(clienteId);

        if(avaliacaoFormDto.getNota() > 5){
            avaliacaoFormDto.setNota(5);
        }else if(avaliacaoFormDto.getNota() < 1){
            avaliacaoFormDto.setNota(1);
        }

        Avaliacao avaliacao = mapper.map(avaliacaoFormDto, Avaliacao.class);
        avaliacao.setClienteId(cliente.getId());
        avaliacao.setData(LocalDate.now());
        avaliacaoRepository.save(avaliacao);

        prestador.getAvaliacao().add(avaliacao);
        prestadorRepository.save(prestador);

        return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(avaliacao.getId()).toUri();
    }

    @Override
    public AvaliacaoFormDto atualizarAvaliacao(Long id, AvaliacaoFormDto avaliacaoFormDto){
        Avaliacao avaliacao = verificaExistenciaAvaliacao(id);
        verificaPermissao();
        Long clienteId = avaliacao.getClienteId();

        avaliacao = mapper.map(avaliacaoFormDto, Avaliacao.class);
        avaliacao.setId(id);
        avaliacao.setData(LocalDate.now());
        avaliacao.setClienteId(clienteId);

        avaliacaoRepository.save(avaliacao);

        return mapper.map(avaliacao, AvaliacaoFormDto.class);
    }

    @Override
    public void deletarAvaliacao(Long id){
        verificaExistenciaAvaliacao(id);
        verificaPermissao();
        avaliacaoRepository.deleteById(id);
    }

    @Override
    public Avaliacao verificaExistenciaAvaliacao(Long id) {
        Optional<Avaliacao> avaliacaoOptional = avaliacaoRepository.findById(id);
        if(avaliacaoOptional.isPresent()){
            return avaliacaoOptional.get();
        }
        throw new ObjectNotFoundException("Avaliação não encontrada");
    }

    private void verificaPermissao() {
        if(tokenService.getTipoPerfilLogado().equals(ADMINISTRADOR)){
            return;
        }else {
            throw new ForbiddenAccessException("Não Autorizado");
        }
    }
}
