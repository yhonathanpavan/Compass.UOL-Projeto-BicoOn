package com.compass.bicoon.services;

import com.compass.bicoon.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.List;

@Service
public interface PrestadorService {

    URI cadastrarPrestador(PrestadorForm prestadorForm);

    PrestadorDto atualizarPrestador(Long id, PrestadorForm prestadorForm);

    void deletaPrestador(Long id);

    Page<PrestadorDto> listarPrestadores(Pageable pageable, String cidade,String nome);

    PrestadorDto listarPorId(Long id);

    List<ServicoDto> listarServicosPrestador(Long id);

    List<AvaliacaoDto> listarAvaliacoesPrestador(Long id);

    ServicoDto cadastrarServico(Long id, ServicoFormDto servicoForm);
}
