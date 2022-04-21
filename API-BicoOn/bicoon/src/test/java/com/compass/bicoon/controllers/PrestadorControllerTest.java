package com.compass.bicoon.controllers;

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
import java.util.Arrays;
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
    public static final String NOME = "João";
    public static final String EMAIL = "joao@gmail.com";
    public static final String CIDADE = "Mogi";
    public static final String SENHA = "123456";
    public static final Sexo SEXO = Sexo.MASCULINO;
    public static final String TELEFONE = "19-988121123";
    public static final boolean DISPONIVEL = true;
    public static final String PRESTADOR_NÃO_ENCONTRADO = "Prestador não encontrado";
    public static final String BABÁ = "Babá";
    public static final String COMENTARIO_AVALIACAO = "Servico muito bom";
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

    private PrestadorDto prestadorDto;
    private Prestador prestador;
    private Servico servico;
    private Avaliacao avaliacao;
    private Categoria categoria;
    private Page<PrestadorDto> prestadorPaginacao;
    private Page<ServicoDto> servicoPaginacao;
    private Page<AvaliacaoDto> avaliacaoPaginacao;
    private PrestadorFormDto prestadorForm;
    private ServicoDto servicoDto;
    private AvaliacaoDto avaliacaoDto;
    private PrestadorDisponibilidadeFormDto presDisp;
    private ServicoFormDto servicoFormDto;


    @BeforeEach
    void setUp() {
        iniciaCategoria();
        iniciaServico();
        iniciaAvaliacao();
        iniciaPrestador();
        prestadorPaginacao = new PageImpl(Arrays.asList(prestadorDto, prestadorDto));
        servicoPaginacao = new PageImpl(Arrays.asList(servicoDto, servicoDto));
        avaliacaoPaginacao = new PageImpl(Arrays.asList(avaliacaoDto, avaliacaoDto));

    }

    @Test
    void deveriaListarPrestadores() throws Exception {
        when(service.listarPrestadores(any(), any()
                , any())).thenReturn(prestadorPaginacao);
        mockMvc.perform(get(URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.content.[0].id").value(1));
    }

    @Test
    void deveriaListarPrestadorPeloId() throws Exception {
        when(service.listarPorId(anyLong())).thenReturn(prestadorDto);
        mockMvc.perform(get(URL_TEMPLATE+"{id}",1)
                 .contentType(MediaType.APPLICATION_JSON))
                 .andExpect(jsonPath("$", notNullValue()))
                 .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void deveriaDeletarUmPrestadorPeloId() throws Exception{
        when(prestadorRepository.findById(anyLong())).thenReturn(Optional.of(prestador));
        mockMvc.perform( delete(URL_TEMPLATE+"{id}", 1L)
                 .contentType(MediaType.APPLICATION_JSON))
                 .andExpect(status().isOk());
    }

    @Test
    void deveriaAtualizarUmPrestadorPeloId() throws Exception {
        when(service.atualizarPrestador(anyLong(),any(PrestadorFormDto.class))).thenReturn(prestadorDto);
        MockHttpServletRequestBuilder mockRequest = put(URL_TEMPLATE+"{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(prestadorForm));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.nome").value(prestadorDto.getNome()));
        }

    @Test
    void deveriaListarServicosDoPrestadorPeloId() throws Exception {
        when(service.listarServicosPrestador(anyLong())).thenReturn(servicoPaginacao);
        mockMvc.perform( get(URL_TEMPLATE+"{id}/servicos", 1)
                 .contentType(MediaType.APPLICATION_JSON))
                 .andExpect(status().isOk())
                 .andExpect(jsonPath("$.content.[0].id").value(servicoDto.getId()))
                 .andExpect(jsonPath("$.content.[0].descricao").value(servicoDto.getDescricao()));
    }

    @Test
    void deveriaCadastrarUmPrestador() throws Exception {
        when(service.cadastrarPrestador(any(PrestadorFormDto.class))).thenReturn(any());
        MockHttpServletRequestBuilder mockRequest = post(URL_TEMPLATE)
                 .contentType(MediaType.APPLICATION_JSON)
                 .accept(MediaType.APPLICATION_JSON)
                 .content(this.mapper.writeValueAsString(prestadorForm));

        mockMvc.perform(mockRequest)
                 .andExpect(status().isCreated());
    }

    @Test
    void deveriaListarAsAvaliacoesDeUmPrestadorPeloId() throws Exception {
        when(service.listarAvaliacoesPrestador(anyLong())).thenReturn(avaliacaoPaginacao);
        mockMvc.perform( get(URL_TEMPLATE+"{id}/avaliacoes", 1)
                 .contentType(MediaType.APPLICATION_JSON))
                 .andExpect(status().isOk())
                 .andExpect(jsonPath("$.content.[0].id").value(avaliacaoDto.getId()))
                 .andExpect(jsonPath("$.content.[0].nota").value(avaliacaoDto.getNota()));
    }

    @Test
    void deveriaAtualizarADisponibilidadeDeUmPrestadorPeloId() throws Exception {
        when(service.atualizarDisponibilidadePrestador(anyLong(),any(PrestadorDisponibilidadeFormDto.class))).thenReturn(prestadorDto);
        MockHttpServletRequestBuilder mockRequest = put(URL_TEMPLATE+"{id}/disponibilidade", 1)
                 .contentType(MediaType.APPLICATION_JSON)
                 .accept(MediaType.APPLICATION_JSON)
                 .content(this.mapper.writeValueAsString(presDisp));

        mockMvc.perform(mockRequest)
                 .andExpect(status().isOk())
                 .andExpect(jsonPath("$", notNullValue()))
                 .andExpect(jsonPath("$.disponivel").value(prestadorDto.getDisponivel()));
    }

    @Test
    void deveriaCadastrarServicoNoPrestador() throws Exception {
        when(service.cadastrarServico(anyLong(),any(ServicoFormDto.class))).thenReturn(servicoDto);
        MockHttpServletRequestBuilder mockRequest = put(URL_TEMPLATE+"{id}/servicos",1)
                 .contentType(MediaType.APPLICATION_JSON)
                 .accept(MediaType.APPLICATION_JSON)
                 .content(this.mapper.writeValueAsString(servicoFormDto));

        mockMvc.perform(mockRequest)
                 .andExpect(status().isOk())
                 .andExpect(jsonPath("$", notNullValue()))
                 .andExpect(jsonPath("$.descricao").value(servicoDto.getDescricao()));
    }

    private void iniciaPrestador() {
        prestador = new Prestador(ID, NOME, EMAIL, CIDADE
                , SENHA, SEXO, TELEFONE, DISPONIVEL);

        prestador.setServico(Arrays.asList(servico));
        prestador.setAvaliacao(Arrays.asList(avaliacao));

        prestadorDto = modelMapper.map(prestador, PrestadorDto.class);

        prestadorForm = PrestadorFormDto.builder()
                .nome(NOME)
                .cidade(CIDADE)
                .email(EMAIL)
                .senha(SENHA)
                .telefone(TELEFONE)
                .sexo(SEXO).build();

        presDisp = PrestadorDisponibilidadeFormDto.builder()
                .disponivel(false).build();

    }

    private void iniciaAvaliacao(){
        avaliacao = Avaliacao.builder().id(ID).data(LocalDate.now())
                .nota(5).clienteId(ID).comentario(COMENTARIO_AVALIACAO).build();

        avaliacaoDto = AvaliacaoDto.builder().id(ID).data(LocalDate.now())
                .nota(5).comentario(COMENTARIO_AVALIACAO).build();
    }

    private void iniciaCategoria() {
        categoria = Categoria.builder()
                .id(ID)
                .nome("Babá").build();

    }

    private void iniciaServico(){
        servico = Servico.builder()
                .id(ID)
                .descricao("Cuido de crianças")
                .categoria(categoria).build();

        servicoDto = ServicoDto.builder()
                .id(ID)
                .descricao("Cuido de crianças")
                .categoria(categoria).build();

        servicoFormDto = ServicoFormDto.builder()
                .descricao("Cuido de crianças")
                .categoria("Teste").build();
    }
}