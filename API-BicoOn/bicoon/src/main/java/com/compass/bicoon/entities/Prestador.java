package com.compass.bicoon.entities;

import com.compass.bicoon.constants.Sexo;
import com.compass.bicoon.model.Usuario;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Prestador extends Usuario implements UserDetails {

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "prestador_id")
    private List<Servico> servico;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "prestador_id")
    private List<Avaliacao> avaliacao;

    private String telefone;
    private Boolean disponivel = true;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Perfil> perfis = new ArrayList<>();


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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.perfis;
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
