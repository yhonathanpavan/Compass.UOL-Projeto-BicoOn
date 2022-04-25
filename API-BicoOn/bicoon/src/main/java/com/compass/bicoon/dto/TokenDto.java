package com.compass.bicoon.dto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenDto {

    private String token;
    private String tipo;

}
