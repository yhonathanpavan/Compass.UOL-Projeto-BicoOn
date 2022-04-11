package com.compass.bicoon.repository;

import com.compass.bicoon.entities.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRespository extends JpaRepository<Cliente, Long> {

    Page<Cliente> findByCidade(String cidade, Pageable paginacao);
}
