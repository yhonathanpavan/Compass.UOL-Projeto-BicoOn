package com.compass.bicoon.services;

import com.compass.bicoon.dto.ServicoDto;
import com.compass.bicoon.dto.ServicoFormDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.net.URI;

public interface ServicoService {

    Page<ServicoDto> listarServicos(Pageable paginacao);

    URI cadastrarServico(ServicoFormDto servicoForm);

    ServicoDto atualizarServico(Long id, ServicoFormDto servicoForm);

    String deletarServico(Long id);

    String deletarCategoriaDoServico(Long idServico, Long idCategoria);
}