package com.compass.bicoon.builder;

import com.compass.bicoon.constants.Sexo;
import com.compass.bicoon.dto.prestador.PrestadorDisponibilidadeFormDto;
import com.compass.bicoon.dto.prestador.PrestadorDto;
import com.compass.bicoon.dto.prestador.PrestadorFormDto;
import com.compass.bicoon.entities.Avaliacao;
import com.compass.bicoon.entities.Prestador;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PrestadorBuilder {

    public static final Long ID = Long.valueOf(1);
    public static final String NOME = "João";
    public static final String EMAIL = "joao@gmail.com";
    public static final String CIDADE = "Mogi";
    public static final String SENHA = "123456";
    public static final Sexo SEXO = Sexo.MASCULINO;
    public static final String TELEFONE = "19-988121123";
    public static final boolean DISPONIVEL = true;


    public static Prestador getPrestador() {
        List<Avaliacao> AVALIACOES = new ArrayList();
        AVALIACOES.add(AvaliacaoBuilder.getAvaliacao());

        return Prestador.builder()
                .id(ID)
                .nome(NOME)
                .email(EMAIL)
                .cidade(CIDADE)
                .senha(SENHA)
                .sexo(SEXO)
                .telefone(TELEFONE)
                .disponivel(DISPONIVEL)
                .avaliacao(AVALIACOES)
                .servico(Arrays.asList(ServicoBuilder.getServico()))
                .build();
    }

    public static PrestadorFormDto getPrestadorFormDto(){
        return PrestadorFormDto.builder()
                .email(EMAIL)
                .senha(SENHA)
                .sexo(SEXO)
                .nome("Maria")
                .telefone("190")
                .cidade(CIDADE)
                .build();
    }

    public static PrestadorDto getPrestadorDto(){
        return PrestadorDto.builder()
                .id(ID)
                .nome(NOME)
                .cidade(CIDADE)
                .sexo(SEXO)
                .telefone(TELEFONE)
                .disponivel(DISPONIVEL)
                .servico(Arrays.asList(ServicoBuilder.getServico()))
                .build();
    }

    public static PrestadorDisponibilidadeFormDto getDisponibilidadeVerdadeiro(){
        return PrestadorDisponibilidadeFormDto.builder()
                .disponivel(true).build();
    }

    public static PrestadorDisponibilidadeFormDto getDisponibilidadeFalso(){
        return PrestadorDisponibilidadeFormDto.builder()
                .disponivel(false).build();
    }

    public static Page<Prestador> getPrestadorPaginacao(){
        return new PageImpl<>(List.of(getPrestador(), getPrestador()));
    }

    public static Page<PrestadorDto> getPrestadorPaginacaoDto(){
        return new PageImpl<>(List.of(getPrestadorDto(), getPrestadorDto()));
    }
}
