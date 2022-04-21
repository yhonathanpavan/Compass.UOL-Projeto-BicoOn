package com.compass.bicoon.services;

import com.compass.bicoon.constants.Sexo;
import com.compass.bicoon.dto.ClienteDto;
import com.compass.bicoon.dto.ClienteFormDto;
import com.compass.bicoon.entities.Cliente;
import com.compass.bicoon.exceptions.ObjectNotFound.ObjectNotFoundException;
import com.compass.bicoon.repository.ClienteRepository;
import com.compass.bicoon.services.ClienteServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;


@SpringBootTest
class ClienteServiceTest {

    private static final long ID        = 1L;
    private static final String NOME    = "Mateus";
    private static final String EMAIL   = "mateus@email.com";
    private static final String SENHA   = "123";
    private static final String CIDADE  = "Limeira";
    private static final Sexo SEXO      = Sexo.MASCULINO;

    @InjectMocks
    private ClienteServiceImpl service;

    @Mock
    private ClienteRepository clienteRepository;

    @Spy
    private ModelMapper mapper;

    private Cliente cliente;
    private ClienteDto clienteDto;
    private ClienteFormDto clienteFormDto;
    private Optional<Cliente> clienteOpcional;
    private Page<Cliente> pagina;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        iniciarCliente();
        pagina = new PageImpl<>(List.of(cliente));
    }

    @Test
    void listarClientes_Sucesso() {

        when(clienteRepository.findAll((Pageable) any())).thenReturn(pagina);

        Pageable paginacao = PageRequest.of(0, 100);

        Page<ClienteDto> resposta = service.listarClientes(null, paginacao);

        assertNotNull(resposta);
        assertEquals(resposta.getTotalElements(), 1);
    }

    @Test
    void listarClientesPorCidade_Sucesso() {

        when(clienteRepository.findAll((Pageable) any())).thenReturn(pagina);
        when(clienteRepository.findByCidade(any(), (Pageable) any())).thenReturn(pagina);

        Pageable paginacao = PageRequest.of(0, 100);

        Page<ClienteDto> resposta = service.listarClientes(CIDADE, paginacao);

        assertNotNull(resposta);
        assertEquals(resposta.getTotalElements(), 1);
    }

    @Test
    void listarPorId_Sucesso() {
        when(clienteRepository.findById(anyLong())).thenReturn(clienteOpcional);

        ClienteDto resposta = service.listarPorId(ID);

        assertNotNull(resposta);
        assertEquals(ClienteDto.class, resposta.getClass());
        assertEquals(ID, resposta.getId());
    }

    @Test
    void deveVerificarCorretamenteQuandoCriarUmCliente(){
        when(clienteRepository.save(any())).thenReturn(cliente);

        service.cadastrarCliente(clienteFormDto);

        verify(clienteRepository, times(1)).save(any());
    }

    @Test
    void atualizarCliente_Sucesso() {
        when(clienteRepository.save(any())).thenReturn(cliente);
        when(clienteRepository.findById(anyLong())).thenReturn(clienteOpcional);
        ClienteDto resposta = service.atualizarCliente(ID, clienteFormDto);

        assertNotNull(resposta);
        assertEquals(ClienteDto.class, resposta.getClass());
        assertEquals(ID, resposta.getId());
    }

    @Test
    void deletarCliente_Sucesso() {
        when(clienteRepository.findById(anyLong())).thenReturn(clienteOpcional);
        doNothing().when(clienteRepository).deleteById(anyLong());
        service.deletarCliente(ID);
        verify(clienteRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void deletarCliente_Erro() {
        when(clienteRepository.findById(anyLong()))
                .thenThrow(new ObjectNotFoundException("Cliente não encontrado"));
        try{
            service.deletarCliente(ID);
        }catch (Exception e){
            assertEquals(ObjectNotFoundException.class, e.getClass());
            assertEquals("Cliente não encontrado", e.getMessage());
        }
    }

    @Test
    void deveRetornarUmaCategoriaQuandoVerificaExistenciaPorId(){
        when(clienteRepository.findById(anyLong())).thenReturn(clienteOpcional);

        Cliente clienteEsperado = service.verificaExistenciaCliente(ID);

        assertEquals(clienteEsperado, cliente);
    }

    @Test
    void deveDarObjectNotFoundExceptionQuandoVerificaExistenciaPorId(){
        try{
            service.verificaExistenciaCliente(ID);
        }catch(Exception ex){
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals("Cliente não encontrado", ex.getMessage());
        }
    }

    private void iniciarCliente(){
        cliente = new Cliente(ID, NOME, EMAIL, SENHA, CIDADE, SEXO);
        clienteDto = ClienteDto.builder().id(ID).nome(NOME).cidade(CIDADE).sexo(SEXO).build();
        clienteFormDto = ClienteFormDto.builder().nome("Maria").email(EMAIL).senha(SENHA).cidade(CIDADE).sexo(SEXO).build();
        clienteOpcional = Optional.of(new Cliente(ID, NOME, EMAIL, SENHA, CIDADE, SEXO));
    }
}