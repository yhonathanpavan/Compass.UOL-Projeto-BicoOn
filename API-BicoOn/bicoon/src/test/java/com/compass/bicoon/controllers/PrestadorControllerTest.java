package com.compass.bicoon.controllers;

import com.compass.bicoon.builder.AvaliacaoBuilder;
import com.compass.bicoon.builder.PrestadorBuilder;
import com.compass.bicoon.builder.ServicoBuilder;
import com.compass.bicoon.dto.prestador.PrestadorDisponibilidadeFormDto;
import com.compass.bicoon.dto.prestador.PrestadorFormDto;
import com.compass.bicoon.dto.servico.ServicoFormDto;
import com.compass.bicoon.repository.PrestadorRepository;
import com.compass.bicoon.services.autenticacao.AutenticacaoService;
import com.compass.bicoon.services.prestador.PrestadorServiceImpl;
import com.compass.bicoon.services.token.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.time.LocalDate;
import java.util.Optional;

import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PrestadorController.class)
@ActiveProfiles("test")
@AutoConfigureTestDatabase
class PrestadorControllerTest {

    public static final Long ID = Long.valueOf(1);
    public static final String URL_TEMPLATE = "/bicoon/prestadores/";

    @MockBean
    private PrestadorRepository prestadorRepository;

    @MockBean
    private PrestadorServiceImpl service;

    @MockBean
    private AutenticacaoService autenticacaoService;

    @MockBean
    private TokenService tokenService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    ObjectMapper mapper;

    @Test
    void deveListarPrestadoresCorretamenteERetornarStatusOk() throws Exception {
        when(service.listarPrestadores(any(), any()
                , any())).thenReturn(PrestadorBuilder.getPrestadorPaginacaoDto());
        mockMvc.perform(get(URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.content.[0].id").value(1));
    }

    @Test
    void deveListarUmPrestadorPorIdCorretamente() throws Exception {
        when(service.listarPorId(anyLong())).thenReturn(PrestadorBuilder.getPrestadorDto());
        mockMvc.perform(get(URL_TEMPLATE+"{id}",1)
                 .contentType(MediaType.APPLICATION_JSON))
                 .andExpect(jsonPath("$", notNullValue()))
                 .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void deveDeletarUmPrestadorCorretamenteERetornarStatusOk() throws Exception{
        when(prestadorRepository.findById(anyLong())).thenReturn(Optional.of(PrestadorBuilder.getPrestador()));
        mockMvc.perform( delete(URL_TEMPLATE+"{id}", 1L)
                 .contentType(MediaType.APPLICATION_JSON))
                 .andExpect(status().isOk());
    }

    @Test
    void deveAtualizarUmPrestadorCorretamenteERetornarStatusOk() throws Exception {
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
    void deveListarServicosDeUmPrestadorPeloIdCorretamenteERetornarStatusOk() throws Exception {
        when(service.listarServicosPrestador(anyLong())).thenReturn(ServicoBuilder.getServicoPaginacaoDto());
        mockMvc.perform( get(URL_TEMPLATE+"{id}/servicos", 1)
                 .contentType(MediaType.APPLICATION_JSON))
                 .andExpect(status().isOk())
                 .andExpect(jsonPath("$.content.[0].id").value(ServicoBuilder.getServicoDto().getId()))
                 .andExpect(jsonPath("$.content.[0].descricao").value(ServicoBuilder.getServicoDto().getDescricao()));
    }

    @Test
    void deveCadastrarUmPrestadorCorretamenteERetornarStatusCreated() throws Exception {
        when(service.cadastrarPrestador(any(PrestadorFormDto.class))).thenReturn(any());
        MockHttpServletRequestBuilder mockRequest = post(URL_TEMPLATE)
                 .contentType(MediaType.APPLICATION_JSON)
                 .accept(MediaType.APPLICATION_JSON)
                 .content(this.mapper.writeValueAsString(PrestadorBuilder.getPrestadorFormDto()));

        mockMvc.perform(mockRequest)
                 .andExpect(status().isCreated());
    }

    @Test
    void deveListarAvaliacoesDeUmPrestadorPeloIdCorretamenteERetornarStatusOk() throws Exception {
        when(service.listarAvaliacoesPrestador(anyLong())).thenReturn(AvaliacaoBuilder.getAvaliacaoPaginacaoDto());
        mockMvc.perform( get(URL_TEMPLATE+"{id}/avaliacoes", 1)
                 .contentType(MediaType.APPLICATION_JSON))
                 .andExpect(status().isOk())
                 .andExpect(jsonPath("$.content.[0].id").value(AvaliacaoBuilder.getAvaliacaoDto().getId()))
                 .andExpect(jsonPath("$.content.[0].nota").value(AvaliacaoBuilder.getAvaliacaoDto().getNota()))
                 .andExpect(jsonPath("$.content.[0].data").value(String.format("%02d", LocalDate.now().getDayOfMonth())  + "/" + String.format("%02d", LocalDate.now().getMonthValue()) + "/" + LocalDate.now().getYear()));
        ;
    }

    @Test
    void deveAtualizarADisponibilidadeDeUmPrestadorPeloIdCorretamenteERetornarStatusOk() throws Exception {
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
    void deveCadastrarUmServicoNoPrestadorCorretamenteERetornarStatusOk() throws Exception {
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