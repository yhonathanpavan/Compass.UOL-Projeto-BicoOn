package com.compass.bicoon.controllers;

import com.compass.bicoon.builder.ClienteBuilder;
import com.compass.bicoon.dto.ClienteDto;
import com.compass.bicoon.dto.ClienteFormDto;
import com.compass.bicoon.services.ClienteServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
class ClienteControllerTest {

    private static final long ID        = 1L;
    private static final String CIDADE  = "Limeira";

    @InjectMocks
    private ClienteController clienteController;

    @Mock
    private ClienteServiceImpl service;

    @Mock
    private ModelMapper mapper;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveListarClientesCorretamenteERetornarStatusOk() {
        when(service.listarClientes(any(), any())).thenReturn(ClienteBuilder.getClientePaginacaoDto());
        when(mapper.map(any(), any())).thenReturn(ClienteBuilder.getClienteDto());

        Pageable paginacao = PageRequest.of(0, 100);

        ResponseEntity<Page<ClienteDto>> resposta = clienteController.listarClientes(null, paginacao);

        assertNotNull(resposta);
        assertNotNull(resposta.getBody());
        assertEquals(ResponseEntity.class, resposta.getClass());
        assertEquals(HttpStatus.OK, resposta.getStatusCode());
    }

    @Test
    void deveListarClientesPorCidadeCorretamenteERetornarStatusOk() {
        when(service.listarClientes(any(), any())).thenReturn(ClienteBuilder.getClientePaginacaoDto());
        when(mapper.map(any(), any())).thenReturn(ClienteBuilder.getClienteDto());

        Pageable paginacao = PageRequest.of(0, 100);

        ResponseEntity<Page<ClienteDto>> resposta = clienteController.listarClientes(CIDADE, paginacao);

        assertNotNull(resposta);
        assertNotNull(resposta.getBody());
        assertEquals(ResponseEntity.class, resposta.getClass());
        assertEquals(HttpStatus.OK, resposta.getStatusCode());
    }

    @Test
    void deveListarUmClientePeloIdCorretamente() {
        when(service.listarPorId(anyLong())).thenReturn(ClienteBuilder.getClienteDto());
        when(mapper.map(any(), any())).thenReturn(ClienteBuilder.getClienteDto());

        ResponseEntity<ClienteDto> resposta = clienteController.detalhesCliente(ID);

        assertNotNull(resposta);
        assertNotNull(resposta.getBody());
        assertEquals(ResponseEntity.class, resposta.getClass());
        assertEquals(ClienteDto.class, resposta.getBody().getClass());
        assertEquals(ID, resposta.getBody().getId());
    }

    @Test
    void deveCadastrarUmClienteCorretamenteERetornarCreated() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("bicoon/clientes/{id}")
                .buildAndExpand(ClienteBuilder.getCliente().getId()).toUri();

        when(service.cadastrarCliente(any())).thenReturn(uri);

        ResponseEntity<ClienteFormDto> resposta = clienteController.cadastrarCliente(ClienteBuilder.getClienteFormDto());

        assertNotNull(resposta);
        assertEquals(uri.toString(), "http://localhost/bicoon/clientes/1");
        assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
    }

    @Test
    void deveAtualizarUmClienteCorretamenteERetornarStatusOk() {
        when(service.atualizarCliente(ID, ClienteBuilder.getClienteFormDto())).thenReturn(ClienteBuilder.getClienteDto());
        when(mapper.map(any(), any())).thenReturn(ClienteBuilder.getClienteDto());

        ResponseEntity<ClienteDto> resposta = clienteController.atualizarCliente(ID, ClienteBuilder.getClienteFormDto());

        assertNotNull(resposta);
        assertNotNull(resposta.getBody());
        assertEquals(ResponseEntity.class, resposta.getClass());
        assertEquals(ID, resposta.getBody().getId());
        assertEquals(HttpStatus.OK, resposta.getStatusCode());
    }

    @Test
    void deveDeletarUmClienteCorretamenteERetornarStatusOk() {
        doNothing().when(service).deletarCliente(anyLong());

        ResponseEntity<?> resposta = clienteController.deletarCliente(ID);

        assertNotNull(resposta);
        assertEquals(ResponseEntity.class, resposta.getClass());
        verify(service, times(1)).deletarCliente(anyLong());
        assertEquals(HttpStatus.OK, resposta.getStatusCode());
    }
}