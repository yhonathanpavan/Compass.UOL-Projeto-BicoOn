package com.compass.bicoon.builder;

import com.compass.bicoon.dto.AvaliacaoDto;
import com.compass.bicoon.dto.AvaliacaoFormDto;
import com.compass.bicoon.entities.Avaliacao;

import java.time.LocalDate;

public class AvaliacaoBuilder {

    public static final long ID            = 1L;
    public static final long CLIENTE_ID    = 1L;
    public static final String COMENTARIO  = "Muito bom, serviço top demais!";
    public static final int NOTA           = 5;
    public static final LocalDate DATA     = LocalDate.now();

    public static Avaliacao getAvaliacao(){
        return Avaliacao.builder()
                .id(ID)
                .clienteId(CLIENTE_ID)
                .comentario(COMENTARIO)
                .nota(NOTA)
                .data(DATA)
                .build();
    }

    public static AvaliacaoDto getAvaliacaoDto(){
        return AvaliacaoDto.builder()
                .id(ID)
                .comentario(COMENTARIO)
                .nota(NOTA)
                .build();
    }
    
    public  static AvaliacaoFormDto getAvaliacaoFormDto(){
        return AvaliacaoFormDto.builder()
                .comentario(COMENTARIO)
                .nota(NOTA)
                .build();
    }

    public  static AvaliacaoFormDto getAvaliacaoFormDtoNotaAcimaDeCinco(){
        return AvaliacaoFormDto.builder()
                .comentario(COMENTARIO)
                .nota(7)
                .build();
    }

    public  static AvaliacaoFormDto getAvaliacaoFormDtoNotaAbaixoDeUm(){
        return AvaliacaoFormDto.builder()
                .comentario(COMENTARIO)
                .nota(-3)
                .build();
    }
}
