package com.compass.bicoon.model;


import com.compass.bicoon.constants.Sexo;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Data
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
