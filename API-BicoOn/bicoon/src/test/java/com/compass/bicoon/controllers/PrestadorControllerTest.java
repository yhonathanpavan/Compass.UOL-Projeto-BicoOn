package com.compass.bicoon.controllers;

import com.compass.bicoon.builder.AvaliacaoBuilder;
import com.compass.bicoon.builder.PrestadorBuilder;
import com.compass.bicoon.builder.ServicoBuilder;
import com.compass.bicoon.constants.Sexo;
import com.compass.bicoon.dto.*;
import com.compass.bicoon.entities.Avaliacao;
import com.compass.bicoon.entities.Categoria;
import com.compass.bicoon.entities.Prestador;
import com.compass.bicoon.entities.Servico;
import com.compass.bicoon.repository.PrestadorRepository;
import com.compass.bicoon.services.PrestadorServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PrestadorController.class)
class PrestadorControllerTest {

    public static final Long ID = Long.valueOf(1);
    public static final String URL_TEMPLATE = "/bicoon/prestadores/";

    @MockBean
    private PrestadorRepository prestadorRepository;

    @MockBean
    private PrestadorServiceImpl service;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    ObjectMapper mapper;

    @Test
    void deveriaListarPrestadores() throws Exception {
        when(service.listarPrestadores(any(), any()
                , any())).thenReturn(PrestadorBuilder.getPrestadorPaginacaoDto());
        mockMvc.perform(get(URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.content.[0].id").value(1));
    }

    @Test
    void deveriaListarPrestadorPeloId() throws Exception {
        when(service.listarPorId(anyLong())).thenReturn(PrestadorBuilder.getPrestadorDto());
        mockMvc.perform(get(URL_TEMPLATE+"{id}",1)
                 .contentType(MediaType.APPLICATION_JSON))
                 .andExpect(jsonPath("$", notNullValue()))
                 .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void deveriaDeletarUmPrestadorPeloId() throws Exception{
        when(prestadorRepository.findById(anyLong())).thenReturn(Optional.of(PrestadorBuilder.getPrestador()));
        mockMvc.perform( delete(URL_TEMPLATE+"{id}", 1L)
                 .contentType(MediaType.APPLICATION_JSON))
                 .andExpect(status().isOk());
    }

    @Test
    void deveriaAtualizarUmPrestadorPeloId() throws Exception {
        when(service.atualizarPrestador(anyLong(),any(PrestadorFormDto.class))).thenReturn(PrestadorBuilder.getPrestadorDto());
        MockHttpServletRequestBuilder mockRequest = put(URL_TEMPLATE+"{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(PrestadorBuilder.getPrestadorFormDto()));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.nome").value(PrestadorBuilder.getPrestadorDto().getNome()));
        }

    @Test
    void deveriaListarServicosDoPrestadorPeloId() throws Exception {
        when(service.listarServicosPrestador(anyLong())).thenReturn(ServicoBuilder.getServicoPaginacaoDto());
        mockMvc.perform( get(URL_TEMPLATE+"{id}/servicos", 1)
                 .contentType(MediaType.APPLICATION_JSON))
                 .andExpect(status().isOk())
                 .andExpect(jsonPath("$.content.[0].id").value(ServicoBuilder.getServicoDto().getId()))
                 .andExpect(jsonPath("$.content.[0].descricao").value(ServicoBuilder.getServicoDto().getDescricao()));
    }

    @Test
    void deveriaCadastrarUmPrestador() throws Exception {
        when(service.cadastrarPrestador(any(PrestadorFormDto.class))).thenReturn(any());
        MockHttpServletRequestBuilder mockRequest = post(URL_TEMPLATE)
                 .contentType(MediaType.APPLICATION_JSON)
                 .accept(MediaType.APPLICATION_JSON)
                 .content(this.mapper.writeValueAsString(PrestadorBuilder.getPrestadorFormDto()));

        mockMvc.perform(mockRequest)
                 .andExpect(status().isCreated());
    }

    @Test
    void deveriaListarAsAvaliacoesDeUmPrestadorPeloId() throws Exception {
        when(service.listarAvaliacoesPrestador(anyLong())).thenReturn(AvaliacaoBuilder.getAvaliacaoPaginacaoDto());
        mockMvc.perform( get(URL_TEMPLATE+"{id}/avaliacoes", 1)
                 .contentType(MediaType.APPLICATION_JSON))
                 .andExpect(status().isOk())
                 .andExpect(jsonPath("$.content.[0].id").value(AvaliacaoBuilder.getAvaliacaoDto().getId()))
                 .andExpect(jsonPath("$.content.[0].nota").value(AvaliacaoBuilder.getAvaliacaoDto().getNota()));
    }

    @Test
    void deveriaAtualizarADisponibilidadeDeUmPrestadorPeloId() throws Exception {
        when(service.atualizarDisponibilidadePrestador(anyLong(),any(PrestadorDisponibilidadeFormDto.class))).thenReturn(PrestadorBuilder.getPrestadorDto());
        MockHttpServletRequestBuilder mockRequest = put(URL_TEMPLATE+"{id}/disponibilidade", 1)
                 .contentType(MediaType.APPLICATION_JSON)
                 .accept(MediaType.APPLICATION_JSON)
                 .content(this.mapper.writeValueAsString(PrestadorBuilder.getDisponibilidadeFalso()));

        mockMvc.perform(mockRequest)
                 .andExpect(status().isOk())
                 .andExpect(jsonPath("$", notNullValue()))
                 .andExpect(jsonPath("$.disponivel").value(PrestadorBuilder.getPrestadorDto().getDisponivel()));
    }

    @Test
    void deveriaCadastrarServicoNoPrestador() throws Exception {
        when(service.cadastrarServico(anyLong(),any(ServicoFormDto.class))).thenReturn(ServicoBuilder.getServicoDto());
        MockHttpServletRequestBuilder mockRequest = put(URL_TEMPLATE+"{id}/servicos",1)
                 .contentType(MediaType.APPLICATION_JSON)
                 .accept(MediaType.APPLICATION_JSON)
                 .content(this.mapper.writeValueAsString(ServicoBuilder.getServicoForm()));

        mockMvc.perform(mockRequest)
                 .andExpect(status().isOk())
                 .andExpect(jsonPath("$", notNullValue()))
                 .andExpect(jsonPath("$.descricao").value(ServicoBuilder.getServicoDto().getDescricao()));
    }
}