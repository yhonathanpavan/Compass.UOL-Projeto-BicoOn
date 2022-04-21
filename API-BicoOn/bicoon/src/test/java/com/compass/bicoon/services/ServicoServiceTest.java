package com.compass.bicoon.services;

import com.compass.bicoon.builder.CategoriaBuilder;
import com.compass.bicoon.builder.ServicoBuilder;
import com.compass.bicoon.dto.ServicoDto;
import com.compass.bicoon.entities.Servico;
import com.compass.bicoon.exceptions.objectNotFound.ObjectNotFoundException;
import com.compass.bicoon.repository.CategoriaRepository;
import com.compass.bicoon.repository.ServicoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
class ServicoServiceTest {

    public static final Long ID = 1L;

    @InjectMocks
    private ServicoServiceImpl service;

    @Mock
    private CategoriaServiceImpl categoriaService;

    @Mock
    private ServicoRepository repository;

    @Mock
    private CategoriaRepository categoriaRepository;

    @Spy
    private ModelMapper mapper;


    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveDarObjectNotFoundExceptionQuandoVerificaExistencia(){
        try{
            service.verificaExistenciaServico(ID);
        }catch(Exception ex){
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals("Serviço não encontrado", ex.getMessage());
        }
    }

    @Test
    void deveRetornarUmServicoQuandoVerificaExistencia(){
        when(repository.findById(anyLong())).thenReturn(Optional.of(ServicoBuilder.getServico()));

        Servico servicoEsperado = service.verificaExistenciaServico(ID);

        assertEquals(servicoEsperado, ServicoBuilder.getServico());
    }

    @Test
    void deveListarServicos(){
        when(repository.findAll((Pageable) any())).thenReturn(ServicoBuilder.getServicoPaginacao());

        Page<ServicoDto> resposta = service.listarServicos(any());
        assertEquals(resposta.getTotalElements(), 2);
        assertEquals(resposta.stream().findFirst(), Optional.of(ServicoBuilder.getServicoDto()));
    }

    @Test
    void atualizarServico(){
        when(repository.findById(anyLong())).thenReturn(Optional.of(ServicoBuilder.getServico()));
        when(categoriaRepository.findByNome(anyString())).thenReturn(Optional.of(CategoriaBuilder.getCategoria()));
        when(categoriaService.verificaExistenciaCategoria(anyString())).thenReturn(CategoriaBuilder.getCategoria());

        ServicoDto esperado = service.atualizarServico(ID, ServicoBuilder.getServicoForm());
        assertNotNull(esperado);
        assertEquals(esperado.getCategoria().getNome(), CategoriaBuilder.getCategoria().getNome());
    }

    @Test
    void deveDeletarServicoComSucesso(){
        when(repository.findById(anyLong())).thenReturn(Optional.of(ServicoBuilder.getServico()));
        doNothing().when(repository).deleteById(anyLong());

        service.deletarServico(ID);
        verify(repository, times(1)).deleteById(anyLong());
    }



}