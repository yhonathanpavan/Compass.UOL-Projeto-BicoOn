package com.compass.bicoon.dto.token;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenDto {

    private String token;
    private String tipo;
}
