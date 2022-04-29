package com.compass.bicoon.repository;

import com.compass.bicoon.entities.Prestador;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PrestadorRepository extends JpaRepository<Prestador, Long>{

    Page<Prestador> findByCidade(Pageable paginacao, String cidade);

    Page<Prestador> findByCidadeAndServicoCategoriaNome(Pageable paginacao, String cidade, String categoria);

    Page<Prestador> findByServicoCategoriaNome(Pageable paginacao, String categoria);

    Optional<Prestador> findByEmail(String email);

    Page<Prestador> findByDisponivelTrue(Pageable paginacao);

}
