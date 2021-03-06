package com.compass.bicoon.dto.avaliacao;

import com.compass.bicoon.serializer.DataSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
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
