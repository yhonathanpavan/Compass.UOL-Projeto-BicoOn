package com.compass.bicoon.dto;

import com.compass.bicoon.serializer.DataSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AvaliacaoDto {

    private Long id;
    private String comentario;
    private int nota;

    @JsonSerialize(using = DataSerializer.class)
    private LocalDate data;
}
