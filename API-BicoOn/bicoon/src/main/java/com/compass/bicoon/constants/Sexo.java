package com.compass.bicoon.constants;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Sexo {
    MASCULINO("masculino"),
    FEMININO("feminino");

    Sexo(String value) {
        this.value = value;
    }
    private String value;

    @JsonCreator
    public static Sexo fromValue(String value) {
        for (Sexo sexo : Sexo.values()) {
            if (sexo.value.equalsIgnoreCase(value)) {
                return sexo;
            }
        }
        return null;
    }
}
