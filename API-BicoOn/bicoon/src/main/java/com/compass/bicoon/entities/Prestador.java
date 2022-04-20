package com.compass.bicoon.entities;

import com.compass.bicoon.constants.Sexo;
import com.compass.bicoon.model.Usuario;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Prestador extends Usuario {

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "prestador_id")
    private List<Servico> servico;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "prestador_id")
    private List<Avaliacao> avaliacao;

    private String telefone;
    private Boolean disponivel = true;

    public Prestador(long id, String nome, String email, String senha, String cidade, Sexo sexo, List<Avaliacao> avaliacao) {
        super.id = id;
        super.nome = nome;
        super.email = email;
        super.senha = senha;
        super.cidade = cidade;
        super.sexo = sexo;
        this.avaliacao = avaliacao;
    }
}
