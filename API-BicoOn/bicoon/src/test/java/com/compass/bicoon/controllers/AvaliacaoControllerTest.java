package com.compass.bicoon.controllers;

import com.compass.bicoon.builder.AvaliacaoBuilder;
import com.compass.bicoon.dto.AvaliacaoFormDto;
import com.compass.bicoon.services.AvaliacaoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
class AvaliacaoControllerTest {

    private static final long ID            = 1L;
    private static final long CLIENTE_ID    = 1L;
    private static final long PRESTADOR_ID  = 1L;
    private static final int NOTA           = 5;

    @InjectMocks
    private AvaliacaoController avaliacaoController;

    @Mock
    private AvaliacaoServiceImpl service;

    @Mock
    private ModelMapper mapper;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveAdicionarUmaAvaliacaoCorretamenteERetornarStatusCreated() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("bicoon/avaliacoes/{id}")
                .buildAndExpand(AvaliacaoBuilder.getAvaliacao().getId()).toUri();

        when(service.criarAvaliacao(any(), any(), any())).thenReturn(uri);

        ResponseEntity<AvaliacaoFormDto> resposta = avaliacaoController.adicionarAvaliacao(CLIENTE_ID, PRESTADOR_ID, AvaliacaoBuilder.getAvaliacaoFormDto());


        assertNotNull(resposta);
        assertEquals(uri.toString(), "http://localhost/bicoon/avaliacoes/1");
        assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
    }

    @Test
    void deveAtualizarUmaAvaliacaoCorretamenteERetornarStatusOk() {
        when(service.atualizarAvaliacao(ID, AvaliacaoBuilder.getAvaliacaoFormDto())).thenReturn(AvaliacaoBuilder.getAvaliacaoFormDto());
        when(mapper.map(any(), any())).thenReturn(AvaliacaoBuilder.getAvaliacaoFormDto());

        ResponseEntity<AvaliacaoFormDto> resposta = avaliacaoController.atualizarAvaliacao(ID, AvaliacaoBuilder.getAvaliacaoFormDto());

        assertNotNull(resposta);
        assertNotNull(resposta.getBody());
        assertEquals(ResponseEntity.class, resposta.getClass());
        assertEquals(NOTA, resposta.getBody().getNota());
        assertEquals(HttpStatus.OK, resposta.getStatusCode());
    }

    @Test
    void deveDeletarUmaAvaliacaoCorretamenteERetornarStatusOk() {
        doNothing().when(service).deletarAvaliacao(anyLong());

        ResponseEntity<?> resposta = avaliacaoController.deletarAvaliacao(ID);

        assertNotNull(resposta);
        assertEquals(ResponseEntity.class, resposta.getClass());
        verify(service, times(1)).deletarAvaliacao(anyLong());
        assertEquals(HttpStatus.OK, resposta.getStatusCode());
    }
}