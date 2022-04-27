package com.compass.bicoon.config.security;

import com.compass.bicoon.entities.Cliente;
import com.compass.bicoon.entities.Prestador;
import com.compass.bicoon.repository.ClienteRepository;
import com.compass.bicoon.repository.PrestadorRepository;
import com.compass.bicoon.services.token.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@AllArgsConstructor
public class AutenticacaoViaTokenFilter extends OncePerRequestFilter {

    private TokenService tokenService;

    private ClienteRepository clienteRepository;

    private PrestadorRepository prestadorRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = recuperarToken(request);
        boolean valido = tokenService.isTokenValido(token);
        if(valido){
            try {
                autenticarUsuario(token);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        filterChain.doFilter(request, response);
    }

    private void autenticarUsuario(String token) throws ClassNotFoundException {
        Long idUsuario = tokenService.getIdUsuario(token);
        String tipoUsuario = tokenService.getTipoUsuario(token);
        Object usuario = null;
        Collection<? extends GrantedAuthority> perfilDeAcesso = null;

        if(tipoUsuario.equals(Cliente.class.toString())){
            usuario = clienteRepository.findById(idUsuario).get();
            perfilDeAcesso = ((Cliente) usuario).getAuthorities();

        }else if(tipoUsuario.equals(Prestador.class.toString())){
            usuario = prestadorRepository.findById(idUsuario).get();
            perfilDeAcesso = ((Prestador) usuario).getAuthorities();

        }

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(usuario, null, perfilDeAcesso);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private String recuperarToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
            return null;
        }

        return token.substring(7, token.length());
    }
}
