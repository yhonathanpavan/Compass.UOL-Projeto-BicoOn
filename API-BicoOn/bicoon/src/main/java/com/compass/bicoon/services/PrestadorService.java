package com.compass.bicoon.services;

import com.compass.bicoon.dto.PrestadorDto;
import com.compass.bicoon.dto.PrestadorForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface PrestadorService {

    PrestadorDto cadastrarPrestador(PrestadorForm prestadorForm);

    PrestadorDto atualizarPrestador(Long id, PrestadorForm prestadorForm);

    void deletaPrestador(Long id);

    Page<PrestadorDto> listarPrestadores(Pageable pageable, String cidade);

    PrestadorDto listarPorId(Long id);

}
