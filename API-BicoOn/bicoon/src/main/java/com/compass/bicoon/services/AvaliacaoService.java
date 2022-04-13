package com.compass.bicoon.services;

import com.compass.bicoon.dto.AvaliacaoFormDto;

import java.net.URI;

public interface AvaliacaoService {

    URI criarAvaliacao(Long clienteId, Long prestadorId, AvaliacaoFormDto avaliacaoFormDto);

    AvaliacaoFormDto atualizarAvaliacao(Long id, AvaliacaoFormDto avaliacao);

    void deletarAvaliacao(Long id);
}
