package com.compass.bicoon.services;

import com.compass.bicoon.builder.CategoriaBuilder;
import com.compass.bicoon.builder.ServicoBuilder;
import com.compass.bicoon.dto.CategoriaDto;
import com.compass.bicoon.entities.Categoria;
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

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class CategoriaServiceTest {

    public static final Long ID = 1L;
    public static final String NOME = "Babá";

    @InjectMocks
    private CategoriaServiceImpl service;

    @Mock
    private CategoriaRepository repository;

    @Mock
    private ServicoRepository servicoRepository;

    @Spy
    private ModelMapper mapper;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveVerificarSeUmaCategoriaFoiSalva(){
        when(repository.save(any())).thenReturn(CategoriaBuilder.getCategoria());

        service.cadastrarCategoria(CategoriaBuilder.getCategoriaForm());

        verify(repository, times(1)).save(any());
    }

    @Test
    void deveDarObjectNotFoundExceptionQuandoVerificaExistenciaPorId(){
        try{
            service.verificaExistenciaCategoria(ID);
        }catch(Exception ex){
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals("Categoria não encontrada", ex.getMessage());
        }
    }

    @Test
    void deveRetornarUmaCategoriaQuandoVerificaExistenciaPorId(){
        when(repository.findById(anyLong())).thenReturn(Optional.of(CategoriaBuilder.getCategoria()));

        Categoria categoriaEsperada = service.verificaExistenciaCategoria(ID);

        assertEquals(categoriaEsperada, CategoriaBuilder.getCategoria());
    }

    @Test
    void deveRetornarUmaCategoriaQuandoVerificaExistenciaPorNome(){
        when(repository.findByNome(anyString())).thenReturn(Optional.of(CategoriaBuilder.getCategoria()));

        Categoria categoriaEsperada = service.verificaExistenciaCategoria(NOME);

        assertEquals(categoriaEsperada, CategoriaBuilder.getCategoria());
    }

    @Test
    void deveDarObjectNotFoundExceptionQuandoVerificaExistenciaPorNome(){
        try{
            service.verificaExistenciaCategoria(NOME);
        }catch(Exception ex){
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals("Categoria não encontrada", ex.getMessage());
        }
    }

    @Test
    void deveListarCategoria(){
        when(repository.findAll((Pageable) any())).thenReturn(CategoriaBuilder.getCategoriaPaginacao());

        Page<CategoriaDto> resposta = service.listarCategorias(any());
        assertEquals(resposta.getTotalElements(), 2);
        assertEquals(resposta.stream().findFirst(), Optional.of(CategoriaBuilder.getCategoriaDto()));
    }

    @Test
    void atualizarCategoria(){
        when(repository.findById(anyLong())).thenReturn(Optional.of(CategoriaBuilder.getCategoria()));

        CategoriaDto esperado = service.atualizarCategoria(ID, CategoriaBuilder.getCategoriaForm());
        assertNotNull(esperado);
        assertEquals(esperado.getNome(), "Babá");
    }

    @Test
    void deveDeletarServicoComSucesso(){
        when(repository.findById(anyLong())).thenReturn(Optional.of(CategoriaBuilder.getCategoria()));
        when(servicoRepository.findByCategoriaId(anyLong())).thenReturn(List.of(ServicoBuilder.getServico()));
        doNothing().when(repository).deleteById(anyLong());

        service.deletarCategoria(ID);
        verify(repository, times(1)).deleteById(anyLong());
    }




}