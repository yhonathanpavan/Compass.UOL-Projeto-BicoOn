package com.compass.bicoon.repository;

import com.compass.bicoon.dto.ServicoDto;
import com.compass.bicoon.entities.Prestador;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrestadorRepository extends JpaRepository<Prestador, Long>{
    Page<Prestador> findByCidade(Pageable paginacao, String cidade);

    List<ServicoDto> findByServico(Long id);
}
