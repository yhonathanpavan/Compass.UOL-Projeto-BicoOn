package com.compass.bicoon.services.autenticacao;

import com.compass.bicoon.entities.Cliente;
import com.compass.bicoon.entities.Prestador;
import com.compass.bicoon.repository.ClienteRepository;
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
    private ClienteRepository clienteRepository;

    @Autowired
    private PrestadorRepository prestadorRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Cliente> cliente = clienteRepository.findByEmail(username);
        Optional<Prestador> prestador = prestadorRepository.findByEmail(username);

        if(cliente.isPresent()){
            return cliente.get();
        }else if(prestador.isPresent()){
            return prestador.get();
        }
        throw new UsernameNotFoundException("Dados inválidos");
    }
}
