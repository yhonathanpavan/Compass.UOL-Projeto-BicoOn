package com.compass.bicoon.model;


import com.compass.bicoon.constants.Sexo;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@MappedSuperclass
public abstract class Usuario {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    protected String nome;
    protected String email;
    protected String senha;
    protected String cidade;

    @Enumerated(EnumType.STRING)
    protected Sexo sexo;


}
