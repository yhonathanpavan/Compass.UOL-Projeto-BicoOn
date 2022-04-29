package com.compass.bicoon.services;

import com.compass.bicoon.builder.CategoriaBuilder;
import com.compass.bicoon.builder.ServicoBuilder;
import com.compass.bicoon.dto.servico.ServicoDto;
import com.compass.bicoon.entities.Servico;
import com.compass.bicoon.exceptions.objectNotFound.ObjectNotFoundException;
import com.compass.bicoon.repository.CategoriaRepository;
import com.compass.bicoon.repository.ServicoRepository;
import com.compass.bicoon.services.categoria.CategoriaServiceImpl;
import com.compass.bicoon.services.prestador.PrestadorServiceImpl;
import com.compass.bicoon.services.servico.ServicoServiceImpl;
import com.compass.bicoon.services.token.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureTestDatabase
class ServicoServiceTest {

    public static final Long ID = 1L;

    @InjectMocks
    private ServicoServiceImpl service;

    @Mock
    private CategoriaServiceImpl categoriaService;

    @Mock
    private ServicoRepository repository;

    @Mock
    PrestadorServiceImpl prestadorService;

    @Mock
    private CategoriaRepository categoriaRepository;

    @Mock
    private TokenService tokenService;

    @Spy
    private ModelMapper mapper;


    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveDarDarServicoNaoEncontradaQuandoVerificaExistencia(){
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

        Servico resposta = service.verificaExistenciaServico(ID);

        assertEquals(ServicoBuilder.getServico().getDescricao(), resposta.getDescricao());
    }

    @Test
    void deveListarServicosCorretamente(){
        Pageable pageable = PageRequest.of(0, 10);

        when(tokenService.getTipoPerfilLogado()).thenReturn("ROLE_ADMINISTRADOR");
        when(repository.findAll((Pageable) any())).thenReturn(ServicoBuilder.getServicoPaginacao());

        Page<ServicoDto> resposta = service.listarServicos(pageable);
        assertEquals(2, resposta.getTotalElements());
        assertEquals(Optional.of(ServicoBuilder.getServicoDto()), resposta.stream().findFirst());
    }

    @Test
    void deveAtualizarUmServicoCorretamente(){
        when(repository.findById(anyLong())).thenReturn(Optional.of(ServicoBuilder.getServico()));
        when(categoriaRepository.findByNome(anyString())).thenReturn(Optional.of(CategoriaBuilder.getCategoria()));
        when(categoriaService.verificaExistenciaCategoria(anyString())).thenReturn(CategoriaBuilder.getCategoria());

        ServicoDto resposta = service.atualizarServico(ID, ServicoBuilder.getServicoForm());
        assertNotNull(resposta);
        assertEquals(CategoriaBuilder.getCategoria().getNome(), resposta.getCategoria().getNome());
    }

    @Test
    void deveVerificarCorretamenteQuandoDeletadaUmServico(){
        when(repository.findById(anyLong())).thenReturn(Optional.of(ServicoBuilder.getServico()));
        doNothing().when(repository).deleteById(anyLong());

        service.deletarServico(ID);
        verify(repository, times(1)).deleteById(anyLong());
    }



}