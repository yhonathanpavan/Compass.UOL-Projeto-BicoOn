package com.compass.bicoon.services;

import com.compass.bicoon.dto.*;
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
}
