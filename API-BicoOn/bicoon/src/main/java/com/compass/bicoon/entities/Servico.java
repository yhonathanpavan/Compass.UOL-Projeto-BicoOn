package com.compass.bicoon.entities;

import lombok.Data;

import javax.persistence.*;


@Entity
@Data
public class Servico{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name="categoria_id")
    private Categoria categoria;

    private String descricao;



}
