package com.compass.bicoon.services;

import com.compass.bicoon.dto.ServicoDto;
import com.compass.bicoon.dto.ServicoFormDto;
import com.compass.bicoon.entities.Servico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ServicoService {

    Page<ServicoDto> listarServicos(Pageable paginacao);

    ServicoDto atualizarServico(Long id, ServicoFormDto servicoForm);

    void deletarServico(Long id);

    Servico verificaExistenciaServico(Long id);
}
