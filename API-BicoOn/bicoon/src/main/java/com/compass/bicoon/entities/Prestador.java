package com.compass.bicoon.entities;

import com.compass.bicoon.model.Usuario;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Prestador extends Usuario {

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "prestador_id")
    private List<Servico> servico;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "prestador_id")
    private List<Avaliacao> avaliacao;

    private String telefone;
    private Boolean disponivel;

}
