package com.compass.bicoon.services;

import com.compass.bicoon.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PrestadorService {

    PrestadorDto cadastrarPrestador(PrestadorForm prestadorForm);

    PrestadorDto atualizarPrestador(Long id, PrestadorForm prestadorForm);

    void deletaPrestador(Long id);

    Page<PrestadorDto> listarPrestadores(Pageable pageable, String cidade);

    PrestadorDto listarPorId(Long id);

    List<ServicoDto> listarServicosPrestador(Long id);

    List<AvaliacaoDto> listarAvaliacoesPrestador(Long id);

    ServicoDto cadastrarServico(Long id, ServicoFormDto servicoForm);
}
