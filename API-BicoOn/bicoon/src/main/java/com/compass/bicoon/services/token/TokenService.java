package com.compass.bicoon.services.token;

import com.compass.bicoon.entities.Cliente;
import com.compass.bicoon.entities.Prestador;
import com.compass.bicoon.exceptions.forbiddenAccess.ForbiddenAccessException;
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

    private String idLogado;
    private String tipoPerfilLogado;
    private Object logado;

    public String gerarToken(Authentication authentication) {
        logado = authentication.getPrincipal();

        if(logado.getClass() == Cliente.class){
            logado = (Cliente) authentication.getPrincipal();
            idLogado = ((Cliente) logado).getId().toString();
            tipoPerfilLogado = verificaPerfilCliente((Cliente) logado);
        }else{
            logado = (Prestador) authentication.getPrincipal();
            idLogado = ((Prestador) logado).getId().toString();
            tipoPerfilLogado = verificaPerfilPrestador((Prestador) logado);
        }

        Date hoje = new Date();
        Date dataExpiracao = new Date(hoje.getTime() + Long.parseLong(expiration));

        return Jwts.builder()
                .setIssuer("API BicoOn")
                .setSubject(idLogado)
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

    public String verificaPerfilCliente(Cliente logado) {
        if(!logado.getPerfis().isEmpty()){
            return logado.getPerfis().get(0).getNome();
        }else {
            return ""; //Só retorna vazio pois ele não tem perfil cadastrado
        }
    }

    public String verificaPerfilPrestador(Prestador logado) {
        if(!logado.getPerfis().isEmpty()){
            return logado.getPerfis().get(0).getNome();
        }else {
            return ""; //Só retorna vazio pois ele não tem perfil cadastrado
        }
    }

    public Long getIdUsuario(String token) {
            Claims claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
            return Long.parseLong(claims.getSubject());
    }

    public String getTipoUsuario(String token){
        Claims claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
        return claims.get("classe", String.class);
    }

    public Long getIdLogado(){
        try {
            if(this.idLogado == null){
                return  null;
            }
            return Long.parseLong(this.idLogado);
        }catch(Exception ex){
            throw new ForbiddenAccessException("É preciso estar autenticado");
        }
    }

    public void setIdLogado(Long idRecuperadoToken){
         this.idLogado = idRecuperadoToken.toString();
    }


    public String getTipoUsuarioLogado(){
        if(this.idLogado == null){
            return  null;
        }else if(logado.toString().equals(Cliente.class.toString()) || logado.toString().equals(Prestador.class.toString())){
            return logado.toString();
        }
        return logado.getClass().toString();
    }

    public void setTipoUsuarioLogado(String classeRecuperadoToken){
        this.logado = classeRecuperadoToken;
    }


    public String getTipoPerfilLogado(){
        return tipoPerfilLogado;
    }

    public void setTipoPerfilLogado(String nomePerfil){
        this.tipoPerfilLogado = nomePerfil;
    }
}
