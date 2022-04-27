package com.compass.bicoon.repository;

import com.compass.bicoon.entities.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Page<Cliente> findByCidade(String cidade, Pageable paginacao);

    Optional<Cliente> findByEmail(String email);
}
