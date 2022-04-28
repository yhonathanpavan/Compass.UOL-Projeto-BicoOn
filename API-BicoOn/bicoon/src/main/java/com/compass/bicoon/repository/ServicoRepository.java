package com.compass.bicoon.repository;

import com.compass.bicoon.entities.Servico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ServicoRepository extends JpaRepository<Servico, Long> {
    List<Servico> findByCategoriaId(Long id);

    @Query(value = "SELECT p.id FROM servico s RIGHT JOIN prestador p ON s.prestador_id = p.id WHERE s.id = ?1", nativeQuery = true)
    Long findPrestadorId (Long id);
}
