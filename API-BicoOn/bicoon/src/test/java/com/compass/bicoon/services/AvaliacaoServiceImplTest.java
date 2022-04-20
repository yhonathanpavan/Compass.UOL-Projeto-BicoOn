package com.compass.bicoon.services;

import com.compass.bicoon.constants.Sexo;
import com.compass.bicoon.dto.AvaliacaoDto;
import com.compass.bicoon.dto.AvaliacaoFormDto;
import com.compass.bicoon.entities.Avaliacao;
import com.compass.bicoon.entities.Cliente;
import com.compass.bicoon.entities.Prestador;
import com.compass.bicoon.entities.Servico;
import com.compass.bicoon.exceptions.ObjectNotFound.ObjectNotFoundException;
import com.compass.bicoon.repository.AvaliacaoRepository;
import com.compass.bicoon.repository.ClienteRepository;
import com.compass.bicoon.repository.PrestadorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class AvaliacaoServiceImplTest {

    private static final long ID            = 1L;
    private static final long CLIENTE_ID    = 1L;
    private static final long PRESTADOR_ID  = 1L;
    private static final String COMENTARIO  = "Muito bom, serviço top demais!";
    private static final int NOTA           = 5;
    private static final LocalDate DATA     = LocalDate.now();

    private static final String NOME    = "Mateus";
    private static final String EMAIL   = "mateus@email.com";
    private static final String SENHA   = "123";
    private static final String CIDADE  = "Limeira";
    private static final Sexo SEXO      = Sexo.MASCULINO;



    @InjectMocks
    private AvaliacaoServiceImpl service;

    @Mock
    private ClienteServiceImpl clienteService;

    @Mock
    private PrestadorServiceImpl prestadorService;

    @Mock
    private AvaliacaoRepository avaliacaoRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private PrestadorRepository prestadorRepository;

    @Spy
    private ModelMapper mapper;

    private Avaliacao avaliacao;
    private AvaliacaoDto avaliacaoDto;
    private AvaliacaoFormDto avaliacaoFormDto;
    private Optional<Avaliacao> avaliacaoOpcional;

    private Cliente cliente;
    private Optional<Cliente> clienteOpcional;

    private Prestador prestador;
    private Optional<Prestador> prestadorOpcional;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        iniciarAvaliacao();
    }

    private List<Avaliacao> AVALIACOES = new ArrayList<>();

//    @Test
//    void deveVerificarCorretamenteQuandoCriarUmaAvaliacao() {
//        when(clienteRepository.findById(anyLong())).thenReturn(Optional.of(cliente));
//        when(prestadorRepository.findById(anyLong())).thenReturn(Optional.of(prestador));
//
//        when(clienteService.verificaExistenciaCliente(anyLong())).thenReturn(cliente);
//        when(prestadorService.verificaExistenciaPrestador(anyLong())).thenReturn(prestador);
//
//        when(avaliacaoRepository.save(any())).thenReturn(avaliacao);
//
//        service.criarAvaliacao(CLIENTE_ID, PRESTADOR_ID, avaliacaoFormDto);
//
//        verify(avaliacaoRepository, times(1)).save(any());
//    }

    @Test
    void atualizarAvaliacao() {
        when(avaliacaoRepository.findById(anyLong())).thenReturn(avaliacaoOpcional);
        when(avaliacaoRepository.save(any())).thenReturn(avaliacao);

        AvaliacaoFormDto resposta = service.atualizarAvaliacao(ID, avaliacaoFormDto);

        assertNotNull(resposta);
        assertEquals(AvaliacaoFormDto.class, resposta.getClass());
    }

    @Test
    void deletarAvaliacao_Sucesso() {
        when(avaliacaoRepository.findById(anyLong())).thenReturn(avaliacaoOpcional);
        doNothing().when(avaliacaoRepository).deleteById(anyLong());
        service.deletarAvaliacao(ID);
        verify(avaliacaoRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void deletarAvaliacao_Erro() {
        when(avaliacaoRepository.findById(anyLong()))
                .thenThrow(new ObjectNotFoundException("Avaliação não encontrada"));
        try{
            service.deletarAvaliacao(ID);
        }catch (Exception e){
            assertEquals(ObjectNotFoundException.class, e.getClass());
            assertEquals("Avaliação não encontrada", e.getMessage());
        }
    }

    @Test
    void deveRetornarUmaCategoriaQuandoVerificaExistenciaPorId(){
        when(avaliacaoRepository.findById(anyLong())).thenReturn(avaliacaoOpcional);

        Avaliacao avaliacaoEsperada = service.verificaExistenciaAvaliacao(ID);

        assertEquals(avaliacaoEsperada, avaliacao);
    }

    @Test
    void deveDarObjectNotFoundExceptionQuandoVerificaExistenciaPorId(){
        try{
            service.verificaExistenciaAvaliacao(ID);
        }catch(Exception ex){
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals("Avaliação não encontrada", ex.getMessage());
        }
    }

    private void iniciarAvaliacao(){
//        AVALIACOES  = new ArrayList(avaliacao);

        avaliacao = Avaliacao.builder().id(ID).clienteId(CLIENTE_ID).comentario(COMENTARIO).nota(NOTA).data(DATA).build();
        avaliacaoDto = AvaliacaoDto.builder().id(ID).comentario(COMENTARIO).nota(NOTA).build();
        avaliacaoFormDto = AvaliacaoFormDto.builder().comentario(COMENTARIO).nota(NOTA).build();
        avaliacaoOpcional = Optional.of(Avaliacao.builder().id(ID).clienteId(CLIENTE_ID).comentario(COMENTARIO).nota(NOTA).data(DATA).build());

        cliente = new Cliente(ID, NOME, EMAIL, SENHA, CIDADE, SEXO);
        clienteOpcional = Optional.of(cliente);

        prestador = new Prestador(ID, NOME, EMAIL, SENHA, CIDADE, SEXO, AVALIACOES);
        prestadorOpcional = Optional.of(prestador);
        prestador.setAvaliacao(Arrays.asList(avaliacao));
    }
}