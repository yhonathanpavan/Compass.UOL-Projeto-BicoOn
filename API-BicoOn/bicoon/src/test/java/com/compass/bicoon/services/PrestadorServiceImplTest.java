package com.compass.bicoon.services;

import com.compass.bicoon.constants.Sexo;
import com.compass.bicoon.dto.*;
import com.compass.bicoon.entities.Avaliacao;
import com.compass.bicoon.entities.Categoria;
import com.compass.bicoon.entities.Prestador;
import com.compass.bicoon.entities.Servico;
import com.compass.bicoon.exceptions.ObjectNotFound.ObjectNotFoundException;
import com.compass.bicoon.repository.PrestadorRepository;
import org.junit.jupiter.api.Assertions;
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
        Mockito.when(prestadorRepository.save(Mockito.any(Prestador.class))).thenReturn(prestador);
        service.cadastrarPrestador(prestadorForm);
        Mockito.verify(prestadorRepository, Mockito.times(1)).save(Mockito.any(Prestador.class));
    }

    @Test
    void deveriaTrazerUmUsuarioPeloId() {
        Mockito.when(prestadorRepository.findById(Mockito.anyLong())).thenReturn(prestadorOptional);
        PrestadorDto resposta = service.listarPorId(ID);

        Assertions.assertNotNull(resposta);
        Assertions.assertEquals(PrestadorDto.class, resposta.getClass());
        Assertions.assertEquals(ID, resposta.getId());
    }

    @Test
    void quandoBuscaPeloIdNaoExistenteEstouraUmaExeption() {
        try{
            service.listarPorId(999L);
        }catch (Exception ex){
            Assertions.assertEquals(ObjectNotFoundException.class, ex.getClass());
            Assertions.assertEquals(PRESTADOR_NÃO_ENCONTRADO, ex.getMessage());
        }
    }

    @Test
    void deletadoComSucesso(){
        Mockito.when(prestadorRepository.findById(Mockito.anyLong())).thenReturn(prestadorOptional);
        Mockito.doNothing().when(prestadorRepository).deleteById(Mockito.anyLong());
        service.deletaPrestador(ID);
        Mockito.verify(prestadorRepository, Mockito.times(1)).deleteById(Mockito.anyLong());
    }
    @Test
    void deveriaDarErroAoDeletarPorIdInexistente(){
        try {
            service.deletaPrestador(999L);
        }catch(Exception ex){
            Assertions.assertEquals(ObjectNotFoundException.class, ex.getClass());
            Assertions.assertEquals(PRESTADOR_NÃO_ENCONTRADO, ex.getMessage());
        }
    }

    @Test
    void deveriaAtualizarUmPrestadorPeloId(){
        Mockito.when(prestadorRepository.findById(Mockito.anyLong())).thenReturn(prestadorOptional);
        PrestadorDto resposta = service.atualizarPrestador(ID, prestadorForm);
        Assertions.assertNotNull(resposta);
        Assertions.assertEquals(resposta.getClass(), PrestadorDto.class);
        Assertions.assertEquals(resposta.getNome(), prestadorForm.getNome());
    }

    @Test
    void naoDeveriaAtualizarPrestadorPorIdInválido(){
        try {
            service.atualizarPrestador(999L, prestadorForm);
        }catch (Exception ex){
            Assertions.assertEquals(ex.getClass(), ObjectNotFoundException.class);
            Assertions.assertEquals(PRESTADOR_NÃO_ENCONTRADO, ex.getMessage());
        }
    }

    @Test
    void naoDeveriaAtualizarPrestadorComInformacoesIncorretas(){
        Mockito.when(prestadorRepository.findById(Mockito.anyLong())).thenReturn(prestadorOptional);
        try {
            prestadorForm.setSexo(Sexo.valueOf("NASCULINO"));
            service.atualizarPrestador(ID, prestadorForm);
        }catch (Exception ex){
            Assertions.assertEquals(ex.getClass(), IllegalArgumentException.class);
        }

    }

    @Test
    void deveriaListarTodosPrestadoresSemFiltro (){
        Mockito.when(prestadorRepository.findAll((Pageable) Mockito.any())).thenReturn(page);
        Page<PrestadorDto> paginaResposta = service.listarPrestadores(Mockito.any(),null,null);
        Assertions.assertEquals(paginaResposta.getTotalElements(), page.getTotalElements());
        Assertions.assertEquals(paginaResposta.stream().findFirst(), Optional.of(prestadorDto));
    }

    @Test
    void deveriaListarOsPrestadoresFiltrandoPorCidade(){
        Mockito.when(prestadorRepository.findByCidade((Pageable)Mockito.any(),Mockito.anyString())).thenReturn(page);
        Page<PrestadorDto> paginaResposta = service.listarPrestadores(pageable,CIDADE,null);
        Assertions.assertNotNull(paginaResposta);
        Assertions.assertEquals(paginaResposta.stream().findFirst().get().getCidade(), CIDADE);
    }

    @Test
    void deveriaListarOsPrestadoresFiltrandoPorCategoria(){
        Mockito.when(prestadorRepository.findByServicoCategoriaNome((Pageable)Mockito.any(),Mockito.anyString())).thenReturn(page);
        Page<PrestadorDto> paginaResposta = service.listarPrestadores(pageable,null,BABÁ);
        Assertions.assertNotNull(paginaResposta);
        Assertions.assertEquals(paginaResposta.stream().findFirst().get().getServico().get(0).getCategoria().getNome(), BABÁ);
    }

    @Test
    void deveriaListarPrestadoresFiltrandoPorCidadeECategoria(){
        Mockito.when(prestadorRepository.findByCidadeAndServicoCategoriaNome((Pageable)Mockito.any(),Mockito.anyString()
                ,Mockito.anyString())).thenReturn(page);
        Page<PrestadorDto> paginaResposta = service.listarPrestadores(pageable,CIDADE, BABÁ);
        Assertions.assertNotNull(paginaResposta);
        Assertions.assertEquals(paginaResposta.stream().findFirst().get().getServico().get(0).getCategoria().getNome(), BABÁ);
        Assertions.assertEquals(paginaResposta.stream().findFirst().get().getCidade(), CIDADE);
    }

    @Test
    void deveriaListarOsServicosDeUmPrestadorPeloId(){
        Mockito.when(prestadorRepository.findById(Mockito.anyLong())).thenReturn(prestadorOptional);
        Page<ServicoDto> paginaResposta = service.listarServicosPrestador(ID);
        Assertions.assertNotNull(paginaResposta);
    }

    @Test
    void naoDeveriaListarOsServicosDeUmPrestadorPorIdInvalido(){
        Mockito.when(prestadorRepository.findById(Mockito.anyLong())).thenReturn(prestadorOptional);
        try {
            service.listarServicosPrestador(999L);
        }catch (Exception ex){
            Assertions.assertEquals(ex.getMessage(), PRESTADOR_NÃO_ENCONTRADO);
            Assertions.assertEquals(ex.getClass(), ObjectNotFoundException.class);
        }
    }

    @Test
    void deveriaAlterarADisponibilidadeDeUmPrestador(){
        Mockito.when(prestadorRepository.findById(Mockito.anyLong())).thenReturn(prestadorOptional);
        PrestadorDto prestadorResposta = service.atualizarDisponibilidadePrestador(ID, dispForm);
        Assertions.assertNotNull(prestadorResposta);
        Assertions.assertEquals(prestadorResposta.getDisponivel(), dispForm.getDisponivel());
    }

    @Test
    void deveriaListarAsAvaliacoesDeUmPrestadorPeloId(){
        prestador.setAvaliacao(Arrays.asList(avaliacao));
        Mockito.when(prestadorRepository.findById(Mockito.anyLong())).thenReturn(prestadorOptional);
        Page<AvaliacaoDto> avaliacaoResposta = service.listarAvaliacoesPrestador(ID);
        Assertions.assertNotNull(avaliacaoResposta);
        Assertions.assertEquals(avaliacaoResposta.getTotalElements(), 1);
        Assertions.assertEquals(avaliacaoResposta.stream().findFirst().get().getData(), avaliacao.getData());
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