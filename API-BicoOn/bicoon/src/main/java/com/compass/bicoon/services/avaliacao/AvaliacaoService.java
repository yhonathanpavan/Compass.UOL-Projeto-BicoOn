package com.compass.bicoon.services.avaliacao;

import com.compass.bicoon.dto.avaliacao.AvaliacaoFormDto;
import com.compass.bicoon.entities.Avaliacao;

import java.net.URI;

public interface AvaliacaoService {

    URI criarAvaliacao(Long clienteId, Long prestadorId, AvaliacaoFormDto avaliacaoFormDto);

    AvaliacaoFormDto atualizarAvaliacao(Long id, AvaliacaoFormDto avaliacao);

    void deletarAvaliacao(Long id);

    Avaliacao verificaExistenciaAvaliacao(Long id);
}
