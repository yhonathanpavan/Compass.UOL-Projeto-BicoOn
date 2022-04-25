package com.compass.bicoon.services;

import com.compass.bicoon.entities.Prestador;
import com.compass.bicoon.repository.PrestadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AutenticacaoService implements UserDetailsService {

    @Autowired
    PrestadorRepository prestadorRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Prestador> prestador = prestadorRepository.findByEmail(username);
        if(prestador.isPresent()){
            return prestador.get();
        }else {
            throw new UsernameNotFoundException("Usuário ou Senha Inválidos");
        }
    }


}
