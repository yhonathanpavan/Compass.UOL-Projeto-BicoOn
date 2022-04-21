package com.compass.bicoon.controllers;

import com.compass.bicoon.controllers.AvaliacaoController;
import com.compass.bicoon.dto.AvaliacaoDto;
import com.compass.bicoon.dto.AvaliacaoFormDto;
import com.compass.bicoon.dto.ClienteDto;
import com.compass.bicoon.dto.ClienteFormDto;
import com.compass.bicoon.entities.Avaliacao;
import com.compass.bicoon.services.AvaliacaoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
class AvaliacaoControllerTest {

    private static final long ID            = 1L;
    private static final long CLIENTE_ID    = 1L;
    private static final long PRESTADOR_ID  = 1L;
    private static final String COMENTARIO  = "Muito bom, serviço top demais!";
    private static final int NOTA           = 5;
    private static final LocalDate DATA     = LocalDate.now();

    @InjectMocks
    private AvaliacaoController avaliacaoController;

    @Mock
    private AvaliacaoServiceImpl service;

    @Mock
    private ModelMapper mapper;

    private Avaliacao avaliacao;
    private AvaliacaoDto avaliacaoDto;
    private AvaliacaoFormDto avaliacaoFormDto;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        iniciarAvaliacao();
    }

    @Test
    void adicionarAvaliacao_Sucesso() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("bicoon/avaliacoes/{id}")
                .buildAndExpand(avaliacao.getId()).toUri();

        when(service.criarAvaliacao(any(), any(), any())).thenReturn(uri);

        ResponseEntity<AvaliacaoFormDto> resposta = avaliacaoController.adicionarAvaliacao(CLIENTE_ID, PRESTADOR_ID, avaliacaoFormDto);


        assertNotNull(resposta);
        assertEquals(uri.toString(), "http://localhost/bicoon/avaliacoes/1");
        assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
    }

    @Test
    void atualizarAvaliacao_Sucesso() {
        when(service.atualizarAvaliacao(ID, avaliacaoFormDto)).thenReturn(avaliacaoFormDto);
        when(mapper.map(any(), any())).thenReturn(avaliacaoFormDto);

        ResponseEntity<AvaliacaoFormDto> resposta = avaliacaoController.atualizarAvaliacao(ID, avaliacaoFormDto);

        assertNotNull(resposta);
        assertNotNull(resposta.getBody());
        assertEquals(ResponseEntity.class, resposta.getClass());
        assertEquals(NOTA, resposta.getBody().getNota());
        assertEquals(HttpStatus.OK, resposta.getStatusCode());
    }

    @Test
    void deletarAvaliacao_Sucesso() {
        doNothing().when(service).deletarAvaliacao(anyLong());

        ResponseEntity<?> resposta = avaliacaoController.deletarAvaliacao(ID);

        assertNotNull(resposta);
        assertEquals(ResponseEntity.class, resposta.getClass());
        verify(service, times(1)).deletarAvaliacao(anyLong());
        assertEquals(HttpStatus.OK, resposta.getStatusCode());
    }

    private void iniciarAvaliacao() {
        avaliacao = Avaliacao.builder().id(ID).clienteId(CLIENTE_ID).comentario(COMENTARIO).nota(NOTA).data(DATA).build();
        avaliacaoDto = AvaliacaoDto.builder().id(ID).comentario(COMENTARIO).nota(NOTA).build();
        avaliacaoFormDto = AvaliacaoFormDto.builder().comentario(COMENTARIO).nota(NOTA).build();
    }
}