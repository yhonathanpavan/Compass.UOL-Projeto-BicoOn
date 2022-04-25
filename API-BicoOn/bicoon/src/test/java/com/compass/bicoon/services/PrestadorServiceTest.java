package com.compass.bicoon.services;

import com.compass.bicoon.builder.AvaliacaoBuilder;
import com.compass.bicoon.builder.PrestadorBuilder;
import com.compass.bicoon.constants.Sexo;
import com.compass.bicoon.dto.AvaliacaoDto;
import com.compass.bicoon.dto.PrestadorDto;
import com.compass.bicoon.dto.ServicoDto;
import com.compass.bicoon.entities.Prestador;
import com.compass.bicoon.exceptions.objectNotFound.ObjectNotFoundException;
import com.compass.bicoon.repository.PrestadorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class PrestadorServiceTest {

    public static final Long ID = Long.valueOf(1);
    public static final String CIDADE = "Mogi";
    public static final String PRESTADOR_NÃO_ENCONTRADO = "Prestador não encontrado";
    public static final String MARIDO_DE_ALUGUEL = "Marido de Aluguel";

    @InjectMocks
    PrestadorServiceImpl service;

    @Mock
    PrestadorRepository prestadorRepository;

    @Spy
    ModelMapper mapper;

    private Pageable pageable;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        pageable = PageRequest.of(0,10);

    }
    @Test
    void deveriaCadastrarUmPrestador(){
        when(prestadorRepository.save(any(Prestador.class))).thenReturn(PrestadorBuilder.getPrestador());
        service.cadastrarPrestador(PrestadorBuilder.getPrestadorFormDto());
        verify(prestadorRepository, times(1)).save(any(Prestador.class));
    }

    @Test
    void deveriaTrazerUmUsuarioPeloId() {
        when(prestadorRepository.findById(anyLong())).thenReturn(Optional.of(PrestadorBuilder.getPrestador()));
        PrestadorDto resposta = service.listarPorId(ID);

        assertNotNull(resposta);
        assertEquals(PrestadorDto.class, resposta.getClass());
        assertEquals(ID, resposta.getId());
    }

    @Test
    void quandoBuscaPeloIdNaoExistenteEstouraUmaExeption() {
        try{
            service.listarPorId(999L);
        }catch (Exception ex){
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals(PRESTADOR_NÃO_ENCONTRADO, ex.getMessage());
        }
    }

    @Test
    void deletadoComSucesso(){
        when(prestadorRepository.findById(anyLong())).thenReturn(Optional.of(PrestadorBuilder.getPrestador()));
        doNothing().when(prestadorRepository).deleteById(anyLong());
        service.deletaPrestador(ID);
        verify(prestadorRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void deveriaDarErroAoDeletarPorIdInexistente(){
        try {
            service.deletaPrestador(999L);
        }catch(Exception ex){
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals(PRESTADOR_NÃO_ENCONTRADO, ex.getMessage());
        }
    }

    @Test
    void deveriaAtualizarUmPrestadorPeloId(){
        when(prestadorRepository.findById(anyLong())).thenReturn(Optional.of(PrestadorBuilder.getPrestador()));
        PrestadorDto resposta = service.atualizarPrestador(ID, PrestadorBuilder.getPrestadorFormDto());
        assertNotNull(resposta);
        assertEquals(resposta.getClass(), PrestadorDto.class);
        assertEquals(resposta.getNome(), PrestadorBuilder.getPrestadorFormDto().getNome());
    }

    @Test
    void naoDeveriaAtualizarPrestadorPorIdInválido(){
        try {
            service.atualizarPrestador(999L, PrestadorBuilder.getPrestadorFormDto());
        }catch (Exception ex){
            assertEquals(ex.getClass(), ObjectNotFoundException.class);
            assertEquals(PRESTADOR_NÃO_ENCONTRADO, ex.getMessage());
        }
    }

    @Test
    void naoDeveriaAtualizarPrestadorComInformacoesIncorretas(){
        when(prestadorRepository.findById(anyLong())).thenReturn(Optional.of(PrestadorBuilder.getPrestador()));
        try {
            PrestadorBuilder.getPrestador().setSexo(Sexo.valueOf("NASCULINO"));
            service.atualizarPrestador(ID, PrestadorBuilder.getPrestadorFormDto());
        }catch (Exception ex){
            assertEquals(ex.getClass(), IllegalArgumentException.class);
        }

    }

    @Test
    void deveriaListarTodosPrestadoresSemFiltro (){
        when(prestadorRepository.findAll((Pageable) any())).thenReturn(PrestadorBuilder.getPrestadorPaginacao());
        Page<PrestadorDto> paginaResposta = service.listarPrestadores(Mockito.any(),null,null);
        assertEquals(paginaResposta.getTotalElements(), PrestadorBuilder.getPrestadorPaginacao().getTotalElements());
        assertEquals(paginaResposta.stream().findFirst(), Optional.of(PrestadorBuilder.getPrestadorDto()));
    }

    @Test
    void deveriaListarOsPrestadoresFiltrandoPorCidade(){
        when(prestadorRepository.findByCidade((Pageable)any(),anyString())).thenReturn(PrestadorBuilder.getPrestadorPaginacao());
        Page<PrestadorDto> paginaResposta = service.listarPrestadores(pageable,CIDADE,null);
        assertNotNull(paginaResposta);
        assertEquals(paginaResposta.stream().findFirst().get().getCidade(), CIDADE);
    }

    @Test
    void deveriaListarOsPrestadoresFiltrandoPorCategoria(){
        when(prestadorRepository.findByServicoCategoriaNome((Pageable)any(),anyString())).thenReturn(PrestadorBuilder.getPrestadorPaginacao());
        Page<PrestadorDto> paginaResposta = service.listarPrestadores(pageable,null, MARIDO_DE_ALUGUEL);
        assertNotNull(paginaResposta);
        assertEquals(paginaResposta.stream().findFirst().get().getServico().get(0).getCategoria().getNome(), MARIDO_DE_ALUGUEL);
    }

    @Test
    void deveriaListarPrestadoresFiltrandoPorCidadeECategoria(){
        when(prestadorRepository.findByCidadeAndServicoCategoriaNome((Pageable)any(),anyString()
                ,anyString())).thenReturn(PrestadorBuilder.getPrestadorPaginacao());
        Page<PrestadorDto> paginaResposta = service.listarPrestadores(pageable,CIDADE, MARIDO_DE_ALUGUEL);
        assertNotNull(paginaResposta);
        assertEquals(paginaResposta.stream().findFirst().get().getServico().get(0).getCategoria().getNome(), MARIDO_DE_ALUGUEL);
        assertEquals(paginaResposta.stream().findFirst().get().getCidade(), CIDADE);
    }

    @Test
    void deveriaListarOsServicosDeUmPrestadorPeloId(){
        when(prestadorRepository.findById(anyLong())).thenReturn(Optional.of(PrestadorBuilder.getPrestador()));
        Page<ServicoDto> paginaResposta = service.listarServicosPrestador(ID);
        assertNotNull(paginaResposta);
    }

    @Test
    void naoDeveriaListarOsServicosDeUmPrestadorPorIdInvalido(){
        when(prestadorRepository.findById(anyLong())).thenReturn(Optional.of(PrestadorBuilder.getPrestador()));
        try {
            service.listarServicosPrestador(999L);
        }catch (Exception ex){
            assertEquals(ex.getMessage(), PRESTADOR_NÃO_ENCONTRADO);
            assertEquals(ex.getClass(), ObjectNotFoundException.class);
        }
    }

    @Test
    void deveriaAlterarADisponibilidadeDeUmPrestador(){
        when(prestadorRepository.findById(anyLong())).thenReturn(Optional.of(PrestadorBuilder.getPrestador()));
        PrestadorDto prestadorResposta = service.atualizarDisponibilidadePrestador(ID, PrestadorBuilder.getDisponibilidadeVerdadeiro());
        assertNotNull(prestadorResposta);
        assertEquals(prestadorResposta.getDisponivel(), PrestadorBuilder.getDisponibilidadeVerdadeiro().getDisponivel());
    }

    @Test
    void deveriaListarAsAvaliacoesDeUmPrestadorPeloId(){
        PrestadorBuilder.getPrestador().setAvaliacao(Arrays.asList(AvaliacaoBuilder.getAvaliacao()));
        when(prestadorRepository.findById(anyLong())).thenReturn(Optional.of(PrestadorBuilder.getPrestador()));
        Page<AvaliacaoDto> avaliacaoResposta = service.listarAvaliacoesPrestador(ID);
        assertNotNull(avaliacaoResposta);
        assertEquals(avaliacaoResposta.getTotalElements(), 1);
    }

}