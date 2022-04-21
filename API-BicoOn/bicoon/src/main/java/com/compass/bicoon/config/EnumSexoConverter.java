package com.compass.bicoon.config;

import com.compass.bicoon.constants.Sexo;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class EnumSexoConverter implements Converter<String, Sexo> {

    @Override
    public Sexo convert(String value) {
        return Sexo.valueOf(value.toUpperCase());
    }
}
