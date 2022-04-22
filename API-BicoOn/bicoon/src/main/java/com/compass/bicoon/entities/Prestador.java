package com.compass.bicoon.entities;

import com.compass.bicoon.constants.Sexo;
import com.compass.bicoon.model.Usuario;
import lombok.Builder;
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


    @Builder
    public Prestador(Long id, String nome, String email, String cidade, String senha, Sexo sexo, String telefone, Boolean disponivel, List<Avaliacao> avaliacao, List<Servico> servico) {
        super.senha = senha;
        super.id = id;
        super.email = email;
        super.nome = nome;
        super.cidade = cidade;
        super.sexo = sexo;
        this.telefone = telefone;
        this.disponivel = disponivel;
        this.avaliacao = avaliacao;
        this.servico = servico;
    }
}
