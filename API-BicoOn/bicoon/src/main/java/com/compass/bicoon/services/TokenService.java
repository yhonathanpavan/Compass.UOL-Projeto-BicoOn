package com.compass.bicoon.services;

import org.springframework.security.core.Authentication;

public interface TokenService {

    String gerarToken(Authentication authentication);
}
