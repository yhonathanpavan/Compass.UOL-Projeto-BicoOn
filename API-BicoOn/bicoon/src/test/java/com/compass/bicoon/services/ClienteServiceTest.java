package com.compass.bicoon.services;

import com.compass.bicoon.builder.ClienteBuilder;
import com.compass.bicoon.dto.ClienteDto;
import com.compass.bicoon.entities.Cliente;
import com.compass.bicoon.exceptions.objectNotFound.ObjectNotFoundException;
import com.compass.bicoon.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;


@SpringBootTest
class ClienteServiceTest {

    private static final long ID        = 1L;
    private static final String CIDADE  = "Limeira";

    @InjectMocks
    private ClienteServiceImpl service;

    @Mock
    private ClienteRepository clienteRepository;

    @Spy
    private ModelMapper mapper;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveListarClientesCorretamente() {

        when(clienteRepository.findAll((Pageable) any())).thenReturn(ClienteBuilder.getClientePaginacao());

        Pageable paginacao = PageRequest.of(0, 100);

        Page<ClienteDto> resposta = service.listarClientes(null, paginacao);

        assertNotNull(resposta);
        assertEquals(1, resposta.getTotalElements());
    }

    @Test
    void deveListarClientesPorCidadeCorretamente() {

        when(clienteRepository.findAll((Pageable) any())).thenReturn(ClienteBuilder.getClientePaginacao());
        when(clienteRepository.findByCidade(any(), (Pageable) any())).thenReturn(ClienteBuilder.getClientePaginacao());

        Pageable paginacao = PageRequest.of(0, 100);

        Page<ClienteDto> resposta = service.listarClientes(CIDADE, paginacao);

        assertNotNull(resposta);
        assertEquals(1, resposta.getTotalElements());
    }

    @Test
    void deveListarUmClientePeloIdCorretamente() {
        when(clienteRepository.findById(anyLong())).thenReturn(Optional.of(ClienteBuilder.getCliente()));

        ClienteDto resposta = service.listarPorId(ID);

        assertNotNull(resposta);
        assertEquals(ClienteDto.class, resposta.getClass());
        assertEquals(ID, resposta.getId());
    }

    @Test
    void deveVerificarCorretamenteQuandoCriarUmCliente(){
        when(clienteRepository.save(any())).thenReturn(ClienteBuilder.getCliente());

        service.cadastrarCliente(ClienteBuilder.getClienteFormDto());

        verify(clienteRepository, times(1)).save(any());
    }

    @Test
    void deveAtualizarCorretamenteUmCliente() {
        when(clienteRepository.save(any())).thenReturn(ClienteBuilder.getCliente());
        when(clienteRepository.findById(anyLong())).thenReturn(Optional.of(ClienteBuilder.getCliente()));
        ClienteDto resposta = service.atualizarCliente(ID, ClienteBuilder.getClienteFormDto());

        assertNotNull(resposta);
        assertEquals(ClienteDto.class, resposta.getClass());
        assertEquals(ID, resposta.getId());
    }

    @Test
    void deveVerificarCorretamenteQuandoDeletarUmCliente() {
        when(clienteRepository.findById(anyLong())).thenReturn(Optional.of(ClienteBuilder.getCliente()));
        doNothing().when(clienteRepository).deleteById(anyLong());
        service.deletarCliente(ID);
        verify(clienteRepository, times(1)).deleteById(anyLong());
    }


    @Test
    void deveRetornarUmaCategoriaQuandoVerificaExistenciaPorId(){
        when(clienteRepository.findById(anyLong())).thenReturn(Optional.of(ClienteBuilder.getCliente()));

        Cliente resposta = service.verificaExistenciaCliente(ID);

        assertEquals(ClienteBuilder.getCliente(), resposta);
    }

    @Test
    void deveClienteNaoEncontradoQuandoVerificaExistenciaPorId(){
        try{
            service.verificaExistenciaCliente(ID);
        }catch(Exception ex){
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals("Cliente não encontrado", ex.getMessage());
        }
    }
}