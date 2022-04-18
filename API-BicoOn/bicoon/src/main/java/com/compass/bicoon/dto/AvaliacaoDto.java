package com.compass.bicoon.dto;

import com.compass.bicoon.serializer.DataSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AvaliacaoDto {

    private Long id;
    private String comentario;
    private int nota;

    @JsonSerialize(using = DataSerializer.class)
    private LocalDate data;
}
