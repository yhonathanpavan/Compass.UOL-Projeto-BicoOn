package com.compass.bicoon.exceptions.services.servico;

import com.compass.bicoon.dto.servico.ServicoDto;
import com.compass.bicoon.dto.servico.ServicoFormDto;
import com.compass.bicoon.entities.Servico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ServicoService {

    Page<ServicoDto> listarServicos(Pageable paginacao);

    ServicoDto atualizarServico(Long id, ServicoFormDto servicoForm);

    void deletarServico(Long id);

    Servico verificaExistenciaServico(Long id);
}
