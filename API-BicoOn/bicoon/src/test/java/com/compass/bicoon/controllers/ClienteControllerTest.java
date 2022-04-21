package com.compass.bicoon.controllers;

import com.compass.bicoon.constants.Sexo;
import com.compass.bicoon.controllers.ClienteController;
import com.compass.bicoon.dto.ClienteDto;
import com.compass.bicoon.dto.ClienteFormDto;
import com.compass.bicoon.entities.Cliente;
import com.compass.bicoon.services.ClienteServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
class ClienteControllerTest {

    private static final long ID        = 1L;
    private static final String NOME    = "Mateus";
    private static final String EMAIL   = "mateus@email.com";
    private static final String SENHA   = "123";
    private static final String CIDADE  = "Limeira";
    private static final Sexo SEXO      = Sexo.MASCULINO;

    @InjectMocks
    private ClienteController clienteController;

    @Mock
    private ClienteServiceImpl service;

    @Mock
    private ModelMapper mapper;

    private Cliente cliente;
    private ClienteDto clienteDto;
    private ClienteFormDto clienteFormDto;
    private Page<ClienteDto> pagina;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        iniciarCliente();
        pagina = new PageImpl<>(List.of(clienteDto));
    }

    @Test
    void listarClientes_Sucesso() {
        when(service.listarClientes(any(), any())).thenReturn(pagina);
        when(mapper.map(any(), any())).thenReturn(clienteDto);

        Pageable paginacao = PageRequest.of(0, 100);

        ResponseEntity<Page<ClienteDto>> resposta = clienteController.listarClientes(null, paginacao);

        assertNotNull(resposta);
        assertNotNull(resposta.getBody());
        assertEquals(ResponseEntity.class, resposta.getClass());
        assertEquals(HttpStatus.OK, resposta.getStatusCode());
    }

    @Test
    void listarClientesPorCidade_Sucesso() {
        when(service.listarClientes(any(), any())).thenReturn(pagina);
        when(mapper.map(any(), any())).thenReturn(clienteDto);

        Pageable paginacao = PageRequest.of(0, 100);

        ResponseEntity<Page<ClienteDto>> resposta = clienteController.listarClientes(CIDADE, paginacao);

        assertNotNull(resposta);
        assertNotNull(resposta.getBody());
        assertEquals(ResponseEntity.class, resposta.getClass());
        assertEquals(HttpStatus.OK, resposta.getStatusCode());
    }

    @Test
    void detalhesCliente_Sucesso() {
        when(service.listarPorId(anyLong())).thenReturn(clienteDto);
        when(mapper.map(any(), any())).thenReturn(clienteDto);

        ResponseEntity<ClienteDto> resposta = clienteController.detalhesCliente(ID);

        assertNotNull(resposta);
        assertNotNull(resposta.getBody());
        assertEquals(ResponseEntity.class, resposta.getClass());
        assertEquals(ClienteDto.class, resposta.getBody().getClass());
        assertEquals(ID, resposta.getBody().getId());
    }

    @Test
    void cadastrarCliente_Sucesso() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("bicoon/clientes/{id}")
                .buildAndExpand(cliente.getId()).toUri();

        when(service.cadastrarCliente(any())).thenReturn(uri);

        ResponseEntity<ClienteFormDto> resposta = clienteController.cadastrarCliente(clienteFormDto);

        assertNotNull(resposta);
        assertEquals(uri.toString(), "http://localhost/bicoon/clientes/1");
        assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
    }

    @Test
    void atualizarCliente_Sucesso() {
        when(service.atualizarCliente(ID, clienteFormDto)).thenReturn(clienteDto);
        when(mapper.map(any(), any())).thenReturn(clienteDto);

        ResponseEntity<ClienteDto> resposta = clienteController.atualizarCliente(ID, clienteFormDto);

        assertNotNull(resposta);
        assertNotNull(resposta.getBody());
        assertEquals(ResponseEntity.class, resposta.getClass());
        assertEquals(ID, resposta.getBody().getId());
        assertEquals(HttpStatus.OK, resposta.getStatusCode());
    }

    @Test
    void deletarCliente_Sucesso() {
        doNothing().when(service).deletarCliente(anyLong());

        ResponseEntity<?> resposta = clienteController.deletarCliente(ID);

        assertNotNull(resposta);
        assertEquals(ResponseEntity.class, resposta.getClass());
        verify(service, times(1)).deletarCliente(anyLong());
        assertEquals(HttpStatus.OK, resposta.getStatusCode());
    }

    private void iniciarCliente(){
        cliente = new Cliente(ID, NOME, EMAIL, SENHA, CIDADE, SEXO);
        clienteDto = ClienteDto.builder().id(ID).nome(NOME).cidade(CIDADE).sexo(SEXO).build();
        clienteFormDto = ClienteFormDto.builder().nome("Maria").email(EMAIL).senha(SENHA).cidade(CIDADE).sexo(SEXO).build();
    }
}