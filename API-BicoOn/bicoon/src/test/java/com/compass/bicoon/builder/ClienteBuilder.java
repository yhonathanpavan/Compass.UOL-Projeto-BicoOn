package com.compass.bicoon.builder;

import com.compass.bicoon.constants.Sexo;
import com.compass.bicoon.dto.cliente.ClienteDto;
import com.compass.bicoon.dto.cliente.ClienteFormDto;
import com.compass.bicoon.entities.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

public class ClienteBuilder {

    private static final long ID        = 1L;
    private static final String NOME    = "Mateus";
    private static final String EMAIL   = "mateus@email.com";
    private static final String SENHA   = "123";
    private static final String CIDADE  = "Limeira";
    private static final Sexo SEXO      = Sexo.MASCULINO;

    public static Cliente getCliente() {
        return Cliente.builder()
                .id(ID)
                .nome(NOME)
                .email(EMAIL)
                .senha(SENHA)
                .cidade(CIDADE)
                .sexo(SEXO)
                .build();
    }

    public static ClienteDto getClienteDto(){
        return ClienteDto.builder()
                .id(ID)
                .nome(NOME)
                .cidade(CIDADE)
                .sexo(SEXO)
                .build();
    }

    public static ClienteFormDto getClienteFormDto(){
        return ClienteFormDto.builder()
                .nome(NOME)
                .email(EMAIL)
                .senha(SENHA)
                .cidade(CIDADE)
                .sexo(SEXO)
                .build();
    }
    public static Page<Cliente> getClientePaginacao(){
        return new PageImpl<>(List.of(getCliente()));
    }

    public static Page<ClienteDto> getClientePaginacaoDto(){
        return new PageImpl<>(List.of(getClienteDto()));
    }
}
