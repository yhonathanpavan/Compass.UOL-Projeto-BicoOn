package com.compass.bicoon.controllers;

import com.compass.bicoon.builder.CategoriaBuilder;
import com.compass.bicoon.dto.CategoriaFormDto;
import com.compass.bicoon.repository.CategoriaRepository;
import com.compass.bicoon.services.AutenticacaoService;
import com.compass.bicoon.services.CategoriaServiceImpl;
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


@WebMvcTest(controllers = CategoriaController.class)
class CategoriaControllerTest {

    @MockBean
    private CategoriaRepository repository;

    @MockBean
    private CategoriaServiceImpl service;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    AutenticacaoService autenticacaoService;

    @Autowired
    ObjectMapper mapper;


    @Test
    void deveListarCategoriasCorretamenteERetornarStatusOk() throws Exception {

        when(service.listarCategorias(any(PageRequest.class))).thenReturn(CategoriaBuilder.getCategoriaPaginacaoDto());

        mockMvc.perform(
                        get("/bicoon/categorias")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.[0].id").value(1))
                .andExpect(jsonPath("$.content.[0].nome").value("Babá"));
    }


    @Test
    void deveAtualizarUmaCategoriaCorretamenteERetornarStatusOk() throws Exception {

        when(service.atualizarCategoria(anyLong(),any(CategoriaFormDto.class))).thenReturn(CategoriaBuilder.getCategoriaDto());

        MockHttpServletRequestBuilder mockRequest = put("/bicoon/categorias/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(CategoriaBuilder.getCategoriaForm()));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.nome").value("Babá"));
    }

    @Test
    void deveCadastrarUmaCategoriaCorretamenteERetornarStatusCreated() throws Exception {

        when(service.cadastrarCategoria(any(CategoriaFormDto.class))).thenReturn(any());

        MockHttpServletRequestBuilder mockRequest = post("/bicoon/categorias/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(CategoriaBuilder.getCategoriaForm()));

        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated());
    }

    @Test
    void deveDeletarUmaCategoriaCorretamenteERetornarStatusOk() throws Exception {

        when(repository.findById(anyLong())).thenReturn(Optional.of(CategoriaBuilder.getCategoria()));

        mockMvc.perform( delete("/bicoon/categorias/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}