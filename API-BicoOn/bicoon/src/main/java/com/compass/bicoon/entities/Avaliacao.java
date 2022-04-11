package com.compass.bicoon.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Avaliacao {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
