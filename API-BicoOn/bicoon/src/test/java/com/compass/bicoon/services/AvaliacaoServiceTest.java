package com.compass.bicoon.services;

import com.compass.bicoon.builder.AvaliacaoBuilder;
import com.compass.bicoon.builder.ClienteBuilder;
import com.compass.bicoon.builder.PrestadorBuilder;
import com.compass.bicoon.dto.avaliacao.AvaliacaoFormDto;
import com.compass.bicoon.entities.Avaliacao;
import com.compass.bicoon.exceptions.objectNotFound.ObjectNotFoundException;
import com.compass.bicoon.repository.AvaliacaoRepository;
import com.compass.bicoon.repository.ClienteRepository;
import com.compass.bicoon.repository.PrestadorRepository;
import com.compass.bicoon.services.avaliacao.AvaliacaoServiceImpl;
import com.compass.bicoon.services.cliente.ClienteServiceImpl;
import com.compass.bicoon.services.prestador.PrestadorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@AutoConfigureTestDatabase
class AvaliacaoServiceTest {

    private static final long ID            = 1L;
    private static final long CLIENTE_ID    = 1L;
    private static final long PRESTADOR_ID  = 1L;

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

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveVerificarCorretamenteQuandoCriarUmaAvaliacao() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        when(clienteService.verificaExistenciaCliente(anyLong())).thenReturn(ClienteBuilder.getCliente());
        when(prestadorService.verificaExistenciaPrestador(anyLong())).thenReturn(PrestadorBuilder.getPrestador());
        when(avaliacaoRepository.save(any())).thenReturn(AvaliacaoBuilder.getAvaliacao());

        service.criarAvaliacao(CLIENTE_ID, PRESTADOR_ID, AvaliacaoBuilder.getAvaliacaoFormDto());
        service.criarAvaliacao(CLIENTE_ID, PRESTADOR_ID, AvaliacaoBuilder.getAvaliacaoFormDtoNotaAcimaDeCinco());
        service.criarAvaliacao(CLIENTE_ID, PRESTADOR_ID, AvaliacaoBuilder.getAvaliacaoFormDtoNotaAbaixoDeUm());


        verify(avaliacaoRepository, times(3)).save(any(Avaliacao.class));
    }

    @Test
    void deveAtualizarUmaAvaliacaoCorretamente() {
        when(avaliacaoRepository.findById(anyLong())).thenReturn(Optional.of(AvaliacaoBuilder.getAvaliacao()));
        when(avaliacaoRepository.save(any())).thenReturn(AvaliacaoBuilder.getAvaliacao());

        AvaliacaoFormDto resposta = service.atualizarAvaliacao(ID, AvaliacaoBuilder.getAvaliacaoFormDto());

        assertNotNull(resposta);
        assertEquals(AvaliacaoFormDto.class, resposta.getClass());
    }

    @Test
    void deveVerificarCorretamenteQuandoDeletadaUmaAvaliacao() {
        when(avaliacaoRepository.findById(anyLong())).thenReturn(Optional.of(AvaliacaoBuilder.getAvaliacao()));
        doNothing().when(avaliacaoRepository).deleteById(anyLong());
        service.deletarAvaliacao(ID);

        verify(avaliacaoRepository, times(1)).deleteById(anyLong());
    }


    @Test
    void deveRetornarUmaCategoriaQuandoVerificarExistenciaPorId(){
        when(avaliacaoRepository.findById(anyLong())).thenReturn(Optional.of(AvaliacaoBuilder.getAvaliacao()));

        Avaliacao avaliacaoEsperada = service.verificaExistenciaAvaliacao(ID);

        assertEquals(avaliacaoEsperada, AvaliacaoBuilder.getAvaliacao());
    }

    @Test
    void deveDarAvaliacaoNaoEncontradaQuandoVerificaExistenciaPorId(){
        try{
            service.verificaExistenciaAvaliacao(ID);
        }catch(Exception ex){
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals("Avaliação não encontrada", ex.getMessage());
        }
    }
}