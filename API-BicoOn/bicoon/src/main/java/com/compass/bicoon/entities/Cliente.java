package com.compass.bicoon.entities;

import com.compass.bicoon.constants.Sexo;
import com.compass.bicoon.model.Usuario;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@Data
public class Cliente extends Usuario {

    public Cliente(long id, String nome, String email, String senha, String cidade, Sexo sexo) {
        super.id = id;
        super.nome = nome;
        super.email = email;
        super.senha = senha;
        super.cidade = cidade;
        super.sexo = sexo;
    }
}
