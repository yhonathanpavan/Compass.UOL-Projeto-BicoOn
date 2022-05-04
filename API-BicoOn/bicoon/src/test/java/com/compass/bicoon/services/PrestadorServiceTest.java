package com.compass.bicoon.services;

import com.compass.bicoon.builder.AvaliacaoBuilder;
import com.compass.bicoon.builder.CategoriaBuilder;
import com.compass.bicoon.builder.PrestadorBuilder;
import com.compass.bicoon.builder.ServicoBuilder;
import com.compass.bicoon.constants.Sexo;
import com.compass.bicoon.dto.avaliacao.AvaliacaoDto;
import com.compass.bicoon.dto.prestador.PrestadorDto;
import com.compass.bicoon.dto.servico.ServicoDto;
import com.compass.bicoon.entities.Cliente;
import com.compass.bicoon.entities.Prestador;
import com.compass.bicoon.exceptions.objectNotFound.ObjectNotFoundException;
import com.compass.bicoon.repository.CategoriaRepository;
import com.compass.bicoon.repository.PrestadorRepository;
import com.compass.bicoon.repository.ServicoRepository;
import com.compass.bicoon.services.autenticacao.AutenticacaoService;
import com.compass.bicoon.services.categoria.CategoriaService;
import com.compass.bicoon.services.prestador.PrestadorServiceImpl;
import com.compass.bicoon.services.token.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.method.P;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureTestDatabase
class PrestadorServiceTest {

    public static final Long ID = Long.valueOf(1);
    public static final String CIDADE = "Mogi";
    public static final String PRESTADOR_NÃO_ENCONTRADO = "Prestador não encontrado";
    public static final String MARIDO_DE_ALUGUEL = "Marido de Aluguel";

    @InjectMocks
    PrestadorServiceImpl service;

    @Mock
    PrestadorRepository prestadorRepository;

    @Mock
    ServicoRepository servicoRepository;

    @Mock
    TokenService tokenService;

    @Mock
    CategoriaRepository categoriaRepository;

    @Mock
    CategoriaService categoriaService;

    @Spy
    ModelMapper mapper;

    private Pageable pageable;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        pageable = PageRequest.of(0,10);

    }
    @Test
    void deveVerificarCorretamenteQuandoCriarUmPrestador(){
        when(prestadorRepository.save(any(Prestador.class))).thenReturn(PrestadorBuilder.getPrestador());
        service.cadastrarPrestador(PrestadorBuilder.getPrestadorFormDto());
        verify(prestadorRepository, times(1)).save(any(Prestador.class));
    }

    @Test
    void deveVerificarCorretamenteQuandoCriarUmServico(){
        when(prestadorRepository.findById(1L)).thenReturn(Optional.of(PrestadorBuilder.getPrestador()));
        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(CategoriaBuilder.getCategoria()));

        when(prestadorRepository.save(any())).thenReturn(PrestadorBuilder.getPrestador());
        when(servicoRepository.save(any())).thenReturn(ServicoBuilder.getServico());
        when(tokenService.getIdLogado()).thenReturn(PrestadorBuilder.getPrestador().getId());
        when(tokenService.getTipoUsuarioLogado()).thenReturn(Prestador.class.toString());
        when(tokenService.getTipoPerfilLogado()).thenReturn("");

        service.cadastrarServico(ID, ServicoBuilder.getServicoForm());

        verify(servicoRepository, times(1)).save(any());
        verify(prestadorRepository, times(1)).save(any());
    }

    @Test
    void deveListarUmPrestadorPeloIdCorretamente() {
        when(prestadorRepository.findById(anyLong())).thenReturn(Optional.of(PrestadorBuilder.getPrestador()));
        PrestadorDto resposta = service.listarPorId(ID);

        assertNotNull(resposta);
        assertEquals(PrestadorDto.class, resposta.getClass());
        assertEquals(ID, resposta.getId());
    }

    @Test
    void deveDarDarPrestadorNaoEncontradaQuandoVerificaExistencia() {
        try{
            service.listarPorId(999L);
        }catch (Exception ex){
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals(PRESTADOR_NÃO_ENCONTRADO, ex.getMessage());
        }
    }

    @Test
    void deveVerificarCorretamenteQuandoDeletarUmPrestador(){
        when(prestadorRepository.findById(anyLong())).thenReturn(Optional.of(PrestadorBuilder.getPrestador()));
        when(tokenService.getIdLogado()).thenReturn(1L);
        when(tokenService.getTipoUsuarioLogado()).thenReturn(Prestador.class.toString());
        doNothing().when(prestadorRepository).deleteById(anyLong());
        service.deletaPrestador(ID);
        verify(prestadorRepository, times(1)).deleteById(anyLong());
    }
    @Test
    void deveDarDarPrestadorNaoEncontradaQuandoTentarDeletarComIdInvalido(){
        try {
            service.deletaPrestador(999L);
        }catch(Exception ex){
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals(PRESTADOR_NÃO_ENCONTRADO, ex.getMessage());
        }
    }

    @Test
    void deveAtualizarCorretamenteUmPrestador(){
        when(servicoRepository.findServicosByPrestadorId(anyLong())).thenReturn(Arrays.asList(ServicoBuilder.getServico()));
        when(prestadorRepository.findById(anyLong())).thenReturn(Optional.of(PrestadorBuilder.getPrestador()));
        when(tokenService.getIdLogado()).thenReturn(1L);
        when(tokenService.getTipoUsuarioLogado()).thenReturn(Prestador.class.toString());
        PrestadorDto resposta = service.atualizarPrestador(ID, PrestadorBuilder.getPrestadorFormDto());
        assertNotNull(resposta);
        assertEquals(resposta.getClass(), PrestadorDto.class);
        assertEquals(resposta.getNome(), PrestadorBuilder.getPrestadorFormDto().getNome());
    }

    @Test
    void deveDarDarPrestadorNaoEncontradaQuandoTentarAtualizarComIdInvalido(){
        try {
            service.atualizarPrestador(999L, PrestadorBuilder.getPrestadorFormDto());
        }catch (Exception ex){
            assertEquals(ex.getClass(), ObjectNotFoundException.class);
            assertEquals(PRESTADOR_NÃO_ENCONTRADO, ex.getMessage());
        }
    }

    @Test
    void naoDeveAtualizarPrestadorComInformacoesIncorretas(){
        when(prestadorRepository.findById(anyLong())).thenReturn(Optional.of(PrestadorBuilder.getPrestador()));
        try {
            PrestadorBuilder.getPrestador().setSexo(Sexo.valueOf("NASCULINO"));
            service.atualizarPrestador(ID, PrestadorBuilder.getPrestadorFormDto());
        }catch (Exception ex){
            assertEquals(ex.getClass(), IllegalArgumentException.class);
        }

    }

    @Test
    void deveListarTodosPrestadoresSemFiltroCorretamente (){
        when(prestadorRepository.findByDisponivelTrue((Pageable) any())).thenReturn(PrestadorBuilder.getPrestadorPaginacao());
        Page<PrestadorDto> paginaResposta = service.listarPrestadores(Mockito.any(),null,null);
        assertEquals(paginaResposta.getTotalElements(), PrestadorBuilder.getPrestadorPaginacao().getTotalElements());
        assertEquals(paginaResposta.stream().findFirst().get().getCidade(), PrestadorBuilder.getPrestadorDto().getCidade());
    }

    @Test
    void deveListarOsPrestadoresFiltrandoPorCidadeCorretamente(){
        when(prestadorRepository.findByCidade((Pageable)any(),anyString())).thenReturn(PrestadorBuilder.getPrestadorPaginacao());
        Page<PrestadorDto> paginaResposta = service.listarPrestadores(pageable,CIDADE,null);
        assertNotNull(paginaResposta);
        assertEquals(paginaResposta.stream().findFirst().get().getCidade(), CIDADE);
    }

    @Test
    void deveListarOsPrestadoresFiltrandoPorCategoriaCorretamente(){
        when(prestadorRepository.findByServicoCategoriaNome((Pageable)any(),anyString())).thenReturn(PrestadorBuilder.getPrestadorPaginacao());
        Page<PrestadorDto> paginaResposta = service.listarPrestadores(pageable,null, MARIDO_DE_ALUGUEL);
        assertNotNull(paginaResposta);
        assertEquals(paginaResposta.stream().findFirst().get().getServico().get(0).getCategoria().getNome(), MARIDO_DE_ALUGUEL);
    }

    @Test
    void deveListarPrestadoresFiltrandoPorCidadeECategoriaCorretamente(){
        when(prestadorRepository.findByCidadeAndServicoCategoriaNome((Pageable)any(),anyString()
                ,anyString())).thenReturn(PrestadorBuilder.getPrestadorPaginacao());
        Page<PrestadorDto> paginaResposta = service.listarPrestadores(pageable,CIDADE, MARIDO_DE_ALUGUEL);
        assertNotNull(paginaResposta);
        assertEquals(paginaResposta.stream().findFirst().get().getServico().get(0).getCategoria().getNome(), MARIDO_DE_ALUGUEL);
        assertEquals(paginaResposta.stream().findFirst().get().getCidade(), CIDADE);
    }

    @Test
    void deverListarOsServicosDeUmPrestadorPeloId(){
        when(prestadorRepository.findById(anyLong())).thenReturn(Optional.of(PrestadorBuilder.getPrestador()));
        Page<ServicoDto> paginaResposta = service.listarServicosPrestador(ID);
        assertNotNull(paginaResposta);
    }

    @Test
    void deveDarDarPrestadorNaoEncontradaQuandoTentarListarComIdInvalido(){
        when(prestadorRepository.findById(anyLong())).thenReturn(Optional.of(PrestadorBuilder.getPrestador()));
        try {
            service.listarServicosPrestador(999L);
        }catch (Exception ex){
            assertEquals(ex.getMessage(), PRESTADOR_NÃO_ENCONTRADO);
            assertEquals(ex.getClass(), ObjectNotFoundException.class);
        }
    }

    @Test
    void deveAlterarADisponibilidadeDeUmPrestadorCorretamente(){
        when(prestadorRepository.findById(anyLong())).thenReturn(Optional.of(PrestadorBuilder.getPrestador()));
        when(tokenService.getIdLogado()).thenReturn(1L);
        when(tokenService.getTipoUsuarioLogado()).thenReturn(Prestador.class.toString());
        PrestadorDto prestadorResposta = service.atualizarDisponibilidadePrestador(ID, PrestadorBuilder.getDisponibilidadeVerdadeiro());
        assertNotNull(prestadorResposta);
        assertEquals(prestadorResposta.getDisponivel(), PrestadorBuilder.getDisponibilidadeVerdadeiro().getDisponivel());
    }

    @Test
    void deveListarAsAvaliacoesDeUmPrestadorPeloId(){
        PrestadorBuilder.getPrestador().setAvaliacao(Arrays.asList(AvaliacaoBuilder.getAvaliacao()));
        when(prestadorRepository.findById(anyLong())).thenReturn(Optional.of(PrestadorBuilder.getPrestador()));
        Page<AvaliacaoDto> avaliacaoResposta = service.listarAvaliacoesPrestador(ID);
        assertNotNull(avaliacaoResposta);
        assertEquals(avaliacaoResposta.getTotalElements(), 1);
        assertEquals(avaliacaoResposta.stream().findFirst().get().getData(), AvaliacaoBuilder.getAvaliacao().getData());
    }
}