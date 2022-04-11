package com.compass.bicoon.repository;

import com.compass.bicoon.entities.Servico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServicoRepository extends JpaRepository<Servico, Long> {
    List<Servico> findByCategoriaId(Long id);
}
