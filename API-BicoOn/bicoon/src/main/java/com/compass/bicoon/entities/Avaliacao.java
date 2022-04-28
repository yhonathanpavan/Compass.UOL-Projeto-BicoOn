package com.compass.bicoon.entities;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Avaliacao {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long clienteId;
    private String comentario;
    private int nota;
    private LocalDate data;
}
