package com.compass.bicoon.services.token;

import com.compass.bicoon.entities.Cliente;
import com.compass.bicoon.entities.Prestador;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

    @Value("${forum.jwt.expiration}")
    private String expiration;

    @Value("${forum.jwt.secret}")
    private String secret;

    public String gerarToken(Authentication authentication) {
        Object logado = authentication.getPrincipal();
        String idLogado;

        if(logado.getClass() == Cliente.class){
            logado = (Cliente) authentication.getPrincipal();
            idLogado = ((Cliente) logado).getId().toString();
        }else{
            logado = (Prestador) authentication.getPrincipal();
            idLogado = ((Prestador) logado).getId().toString();
        }

        Date hoje = new Date();
        Date dataExpiracao = new Date(hoje.getTime() + Long.parseLong(expiration));

        return Jwts.builder()
                .setIssuer("API BicoOn")
                .setSubject(logado.getClass().toString())
                .setId(idLogado)
                .claim("classe", logado.getClass().toString())
                .setIssuedAt(hoje)
                .setExpiration(dataExpiracao)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public boolean isTokenValido(String token) {
        try{
            Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
            return true;
        } catch (Exception ex){
            return false;
        }
    }

    public Long getIdUsuario(String token) {
        Claims claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
        return Long.parseLong(claims.getId());
    }

    public String getTipoUsuario(String token) throws ClassNotFoundException {
        Claims claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
}
