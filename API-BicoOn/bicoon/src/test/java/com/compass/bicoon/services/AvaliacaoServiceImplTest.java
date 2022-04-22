package com.compass.bicoon.services;

import com.compass.bicoon.builder.AvaliacaoBuilder;
import com.compass.bicoon.constants.Sexo;
import com.compass.bicoon.dto.AvaliacaoFormDto;
import com.compass.bicoon.entities.Avaliacao;
import com.compass.bicoon.entities.Prestador;
import com.compass.bicoon.exceptions.objectNotFound.ObjectNotFoundException;
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

    private static final String NOME    = "Mateus";
    private static final String EMAIL   = "mateus@email.com";
    private static final String SENHA   = "123";
    private static final String CIDADE  = "Limeira";
    private static final Sexo SEXO      = Sexo.MASCULINO;
    private static final String TELEFONE = "19 99578-1012";
    private static final boolean DISPONIVEL= true;

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
////        when(clienteRepository.findById(anyLong())).thenReturn(Optional.of(cliente));
////        when(prestadorRepository.findById(anyLong())).thenReturn(Optional.of(prestador));
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
        when(avaliacaoRepository.findById(anyLong())).thenReturn(Optional.of(AvaliacaoBuilder.getAvaliacao()));
        when(avaliacaoRepository.save(any())).thenReturn(AvaliacaoBuilder.getAvaliacao());

        AvaliacaoFormDto resposta = service.atualizarAvaliacao(ID, AvaliacaoBuilder.getAvaliacaoFormDto());

        assertNotNull(resposta);
        assertEquals(AvaliacaoFormDto.class, resposta.getClass());
    }

    @Test
    void deletarAvaliacao_Sucesso() {
        when(avaliacaoRepository.findById(anyLong())).thenReturn(Optional.of(AvaliacaoBuilder.getAvaliacao()));
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
        when(avaliacaoRepository.findById(anyLong())).thenReturn(Optional.of(AvaliacaoBuilder.getAvaliacao()));

        Avaliacao avaliacaoEsperada = service.verificaExistenciaAvaliacao(ID);

        assertEquals(avaliacaoEsperada, AvaliacaoBuilder.getAvaliacao());
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

        prestador = new Prestador(ID, NOME, EMAIL, CIDADE, SENHA, SEXO, TELEFONE, DISPONIVEL);
        prestadorOpcional = Optional.of(prestador);
        prestador.setAvaliacao(Arrays.asList(AvaliacaoBuilder.getAvaliacao()));
    }
}