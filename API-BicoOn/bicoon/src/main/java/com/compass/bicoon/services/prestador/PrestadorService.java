package com.compass.bicoon.exceptions.services.prestador;

import com.compass.bicoon.dto.avaliacao.AvaliacaoDto;
import com.compass.bicoon.dto.prestador.PrestadorDisponibilidadeFormDto;
import com.compass.bicoon.dto.prestador.PrestadorDto;
import com.compass.bicoon.dto.prestador.PrestadorFormDto;
import com.compass.bicoon.dto.servico.ServicoDto;
import com.compass.bicoon.dto.servico.ServicoFormDto;
import com.compass.bicoon.entities.Prestador;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.net.URI;

public interface PrestadorService {

    URI cadastrarPrestador(PrestadorFormDto prestadorFormDto);

    PrestadorDto atualizarPrestador(Long id, PrestadorFormDto prestadorFormDto);

    void deletaPrestador(Long id);

    Page<PrestadorDto> listarPrestadores(Pageable pageable, String cidade,String nome);

    PrestadorDto listarPorId(Long id);

    Page<ServicoDto> listarServicosPrestador(Long id);

    Page<AvaliacaoDto> listarAvaliacoesPrestador(Long id);

    ServicoDto cadastrarServico(Long id, ServicoFormDto servicoForm);

    Prestador verificaExistenciaPrestador(Long id);

    PrestadorDto atualizarDisponibilidadePrestador(Long id, PrestadorDisponibilidadeFormDto prestadorDispForm);

    void verificaLogado(Long id);

}
