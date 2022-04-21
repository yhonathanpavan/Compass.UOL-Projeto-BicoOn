package com.compass.bicoon.services;

import com.compass.bicoon.constants.Sexo;
import com.compass.bicoon.dto.*;
import com.compass.bicoon.entities.Avaliacao;
import com.compass.bicoon.entities.Categoria;
import com.compass.bicoon.entities.Prestador;
import com.compass.bicoon.entities.Servico;
import com.compass.bicoon.exceptions.objectNotFound.ObjectNotFoundException;
import com.compass.bicoon.repository.PrestadorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

<<<<<<< HEAD
import static org.junit.jupiter.api.Assertions.*;
=======
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
>>>>>>> dedb9a1fa070b003dc3e65c359fe924ca75bc962
import static org.mockito.Mockito.*;

@SpringBootTest
class PrestadorServiceImplTest {

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

    @InjectMocks
    PrestadorServiceImpl service;

    @Mock
    PrestadorRepository prestadorRepository;

    @Spy
    ModelMapper mapper;

    private Prestador prestador;
    private Optional<Prestador> prestadorOptional;
    private PrestadorFormDto prestadorForm;
    private Servico servico;
    private PageImpl<Prestador> page;
    private PrestadorDto prestadorDto;
    private Pageable pageable;
    private Categoria categoria;
    private Optional<Categoria> optionalCategoria;
    private PrestadorDisponibilidadeFormDto dispForm;
    private ServicoFormDto servicoForm;
    private Avaliacao avaliacao;

    @BeforeEach
    void setUp() {
        iniciaCategoria();
        iniciaServico();
        iniciaAvaliacao();
        iniciaPrestador();
        MockitoAnnotations.openMocks(this);
        page = new PageImpl(Arrays.asList(prestador, prestador));
        pageable = PageRequest.of(0,10);

    }
    @Test
    void deveriaCadastrarUmPrestador(){
        when(prestadorRepository.save(any(Prestador.class))).thenReturn(prestador);
        service.cadastrarPrestador(prestadorForm);
        verify(prestadorRepository, times(1)).save(any(Prestador.class));
    }

    @Test
    void deveriaTrazerUmUsuarioPeloId() {
        when(prestadorRepository.findById(anyLong())).thenReturn(prestadorOptional);
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
        when(prestadorRepository.findById(anyLong())).thenReturn(prestadorOptional);
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
        when(prestadorRepository.findById(anyLong())).thenReturn(prestadorOptional);
        PrestadorDto resposta = service.atualizarPrestador(ID, prestadorForm);
        assertNotNull(resposta);
        assertEquals(resposta.getClass(), PrestadorDto.class);
        assertEquals(resposta.getNome(), prestadorForm.getNome());
    }

    @Test
    void naoDeveriaAtualizarPrestadorPorIdInválido(){
        try {
            service.atualizarPrestador(999L, prestadorForm);
        }catch (Exception ex){
            assertEquals(ex.getClass(), ObjectNotFoundException.class);
            assertEquals(PRESTADOR_NÃO_ENCONTRADO, ex.getMessage());
        }
    }

    @Test
    void naoDeveriaAtualizarPrestadorComInformacoesIncorretas(){
        when(prestadorRepository.findById(anyLong())).thenReturn(prestadorOptional);
        try {
            prestadorForm.setSexo(Sexo.valueOf("NASCULINO"));
            service.atualizarPrestador(ID, prestadorForm);
        }catch (Exception ex){
            assertEquals(ex.getClass(), IllegalArgumentException.class);
        }

    }

    @Test
    void deveriaListarTodosPrestadoresSemFiltro (){
        when(prestadorRepository.findAll((Pageable) any())).thenReturn(page);
<<<<<<< HEAD
        Page<PrestadorDto> paginaResposta = service.listarPrestadores(any(),null,null);
=======
        Page<PrestadorDto> paginaResposta = service.listarPrestadores(Mockito.any(),null,null);
>>>>>>> dedb9a1fa070b003dc3e65c359fe924ca75bc962
        assertEquals(paginaResposta.getTotalElements(), page.getTotalElements());
        assertEquals(paginaResposta.stream().findFirst(), Optional.of(prestadorDto));
    }

    @Test
    void deveriaListarOsPrestadoresFiltrandoPorCidade(){
<<<<<<< HEAD
        when(prestadorRepository.findByCidade((Pageable) any(), anyString())).thenReturn(page);
=======
        when(prestadorRepository.findByCidade((Pageable)any(),anyString())).thenReturn(page);
>>>>>>> dedb9a1fa070b003dc3e65c359fe924ca75bc962
        Page<PrestadorDto> paginaResposta = service.listarPrestadores(pageable,CIDADE,null);
        assertNotNull(paginaResposta);
        assertEquals(paginaResposta.stream().findFirst().get().getCidade(), CIDADE);
    }

    @Test
    void deveriaListarOsPrestadoresFiltrandoPorCategoria(){
<<<<<<< HEAD
        when(prestadorRepository.findByServicoCategoriaNome((Pageable) any(), anyString())).thenReturn(page);
=======
        when(prestadorRepository.findByServicoCategoriaNome((Pageable)any(),anyString())).thenReturn(page);
>>>>>>> dedb9a1fa070b003dc3e65c359fe924ca75bc962
        Page<PrestadorDto> paginaResposta = service.listarPrestadores(pageable,null,BABÁ);
        assertNotNull(paginaResposta);
        assertEquals(paginaResposta.stream().findFirst().get().getServico().get(0).getCategoria().getNome(), BABÁ);
    }

    @Test
    void deveriaListarPrestadoresFiltrandoPorCidadeECategoria(){
<<<<<<< HEAD
        when(prestadorRepository.findByCidadeAndServicoCategoriaNome((Pageable) any(), anyString()
                , anyString())).thenReturn(page);
=======
        when(prestadorRepository.findByCidadeAndServicoCategoriaNome((Pageable)any(),anyString()
                ,anyString())).thenReturn(page);
>>>>>>> dedb9a1fa070b003dc3e65c359fe924ca75bc962
        Page<PrestadorDto> paginaResposta = service.listarPrestadores(pageable,CIDADE, BABÁ);
        assertNotNull(paginaResposta);
        assertEquals(paginaResposta.stream().findFirst().get().getServico().get(0).getCategoria().getNome(), BABÁ);
        assertEquals(paginaResposta.stream().findFirst().get().getCidade(), CIDADE);
    }

    @Test
    void deveriaListarOsServicosDeUmPrestadorPeloId(){
        when(prestadorRepository.findById(anyLong())).thenReturn(prestadorOptional);
        Page<ServicoDto> paginaResposta = service.listarServicosPrestador(ID);
        assertNotNull(paginaResposta);
    }

    @Test
    void naoDeveriaListarOsServicosDeUmPrestadorPorIdInvalido(){
        when(prestadorRepository.findById(anyLong())).thenReturn(prestadorOptional);
        try {
            service.listarServicosPrestador(999L);
        }catch (Exception ex){
            assertEquals(ex.getMessage(), PRESTADOR_NÃO_ENCONTRADO);
            assertEquals(ex.getClass(), ObjectNotFoundException.class);
        }
    }

    @Test
    void deveriaAlterarADisponibilidadeDeUmPrestador(){
        when(prestadorRepository.findById(anyLong())).thenReturn(prestadorOptional);
        PrestadorDto prestadorResposta = service.atualizarDisponibilidadePrestador(ID, dispForm);
        assertNotNull(prestadorResposta);
        assertEquals(prestadorResposta.getDisponivel(), dispForm.getDisponivel());
    }

    @Test
    void deveriaListarAsAvaliacoesDeUmPrestadorPeloId(){
        prestador.setAvaliacao(Arrays.asList(avaliacao));
        when(prestadorRepository.findById(anyLong())).thenReturn(prestadorOptional);
        Page<AvaliacaoDto> avaliacaoResposta = service.listarAvaliacoesPrestador(ID);
        assertNotNull(avaliacaoResposta);
        assertEquals(avaliacaoResposta.getTotalElements(), 1);
        assertEquals(avaliacaoResposta.stream().findFirst().get().getData(), avaliacao.getData());
<<<<<<< HEAD
    }

    @Test
    void deveriaCadastrarUmServicoNoPrestador(){

=======
>>>>>>> dedb9a1fa070b003dc3e65c359fe924ca75bc962
    }

    private void iniciaPrestador() {
        prestador = new Prestador(ID, NOME, EMAIL, CIDADE
                , SENHA, SEXO, TELEFONE, DISPONIVEL);

        prestador.setServico(Arrays.asList(servico));

        prestadorOptional = Optional.of(prestador);

        prestadorForm = PrestadorFormDto.builder()
                .email(EMAIL)
                .sexo(SEXO)
                .nome("Maria")
                .telefone("190")
                .cidade(CIDADE).build();

        prestadorDto = mapper.map(prestador, PrestadorDto.class);

        dispForm = PrestadorDisponibilidadeFormDto.builder()
                .disponivel(false).build();
    }
    private void iniciaAvaliacao(){
        avaliacao = Avaliacao.builder().id(ID).data(LocalDate.now())
                .nota(5).clienteId(ID).comentario("Servico muito bom").build();
    }

    private void iniciaCategoria() {
        categoria = Categoria.builder()
                .id(ID)
                .nome("Babá").build();

        optionalCategoria = Optional.of(categoria);

    }

    private void iniciaServico(){
        servico = Servico.builder()
                .id(ID)
                .descricao("Cuido de crianças")
                .categoria(categoria).build();

        servicoForm = ServicoFormDto.builder()
                .descricao("Trabalho Com Obras")
                .categoria("Pedreiro").build();
    }

}