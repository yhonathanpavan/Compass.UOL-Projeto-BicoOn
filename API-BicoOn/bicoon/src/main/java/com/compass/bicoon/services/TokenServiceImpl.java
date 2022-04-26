package com.compass.bicoon.services;

import com.compass.bicoon.entities.Prestador;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenServiceImpl implements TokenService{


    @Value("${bicoon.jwt.expiration}")
    private String expiration;

    @Value("${bicoon.jwt.secret}")
    private String secret;

    @Override
    public String gerarToken(Authentication authentication) {
        Prestador prestadorLogado = (Prestador) authentication.getPrincipal();
        Date dataHoje = new Date();
        Date dataExp = new Date(dataHoje.getTime()+Long.parseLong(expiration));

        return Jwts.builder()
                .setIssuer("Bicoon API")
                .setSubject(prestadorLogado.getId().toString())
                .setIssuedAt(dataHoje)
                .setExpiration(dataExp)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }
}
