package com.compass.bicoon.controllers;

import com.compass.bicoon.dto.login.LoginFormDto;
import com.compass.bicoon.dto.token.TokenDto;
import com.compass.bicoon.services.token.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/bicoon/auth")
@Profile("prod")
public class AutenticacaoController{

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<?> autenticar(@RequestBody @Valid LoginFormDto form){
        UsernamePasswordAuthenticationToken dadosLogin = new UsernamePasswordAuthenticationToken(form.getEmail(), form.getSenha());
        try {
            Authentication authentication = authManager.authenticate(dadosLogin);
            String token = tokenService.gerarToken(authentication);
            return ResponseEntity.ok(new TokenDto(token, "Bearer"));

        } catch(AuthenticationException e){
            return ResponseEntity.badRequest().build();
        }
    }
}
