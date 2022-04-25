package com.compass.bicoon.controllers;

import com.compass.bicoon.builder.ServicoBuilder;
import com.compass.bicoon.dto.ServicoFormDto;
import com.compass.bicoon.repository.CategoriaRepository;
import com.compass.bicoon.repository.ServicoRepository;
import com.compass.bicoon.services.AutenticacaoService;
import com.compass.bicoon.services.CategoriaServiceImpl;
import com.compass.bicoon.services.ServicoServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.Optional;

import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = ServicoController.class)
class ServicoControllerTest {

    @MockBean
    private ServicoRepository repository;

    @MockBean
    private CategoriaRepository categoriaRepository;

    @MockBean
    private ServicoServiceImpl service;

    @MockBean
    private CategoriaServiceImpl categoriaService;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    AutenticacaoService autenticacaoService;

    @Autowired
    ObjectMapper mapper;

    @Test
    void deveListarServicosCorretamenteERetornarStatusOk() throws Exception {

        when(service.listarServicos(any(PageRequest.class))).thenReturn(ServicoBuilder.getServicoPaginacaoDto());

        mockMvc.perform(
                        get("/bicoon/servicos")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.[0].id").value(1))
                .andExpect(jsonPath("$.content.[0].descricao").value("Teste"));
    }

    @Test
    void deveAtualizarUmServicoCorretamenteERetornarStatusOk() throws Exception {


        when(service.atualizarServico(anyLong(),any(ServicoFormDto.class))).thenReturn(ServicoBuilder.getServicoDto());

        MockHttpServletRequestBuilder mockRequest = put("/bicoon/servicos/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(ServicoBuilder.getServicoForm()));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.descricao").value("Teste"));
    }

    @Test
    void deveDeletarUmServicoCorretamenteERetornarStatusOk() throws Exception {

        when(repository.findById(anyLong())).thenReturn(Optional.of(ServicoBuilder.getServico()));

        mockMvc.perform( delete("/bicoon/servicos/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }
}