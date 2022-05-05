package com.compass.bicoon.services;

import com.compass.bicoon.builder.ClienteBuilder;
import com.compass.bicoon.builder.PrestadorBuilder;
import com.compass.bicoon.repository.ClienteRepository;
import com.compass.bicoon.repository.PrestadorRepository;
import com.compass.bicoon.services.autenticacao.AutenticacaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@AutoConfigureTestDatabase
class AutenticacaoServiceTest {

    @InjectMocks
    private AutenticacaoService service;

    @Mock
    private PrestadorRepository prestadorRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveCarregarOsDadosDoUsuarioCorretamente(){
        String clienteEmail = "mateus@email.com";
        String prestadorEmail = "joseromano@email.com";

        when(prestadorRepository.findByEmail(prestadorEmail)).thenReturn(Optional.of(PrestadorBuilder.getPrestador()));
        when(clienteRepository.findByEmail(clienteEmail)).thenReturn(Optional.of(ClienteBuilder.getCliente()));

        UserDetails clienteRecebido = service.loadUserByUsername(clienteEmail);
        UserDetails prestadorRecebido = service.loadUserByUsername(prestadorEmail);


        assertEquals(clienteRecebido.getUsername(), ClienteBuilder.getCliente().getUsername());
        assertEquals(prestadorRecebido.getUsername(), PrestadorBuilder.getPrestador().getUsername());
    }

    @Test
    void deveEstourarExceptionQuandoCarregarOsDadosDoUsuarioIncorreto(){
        String incorretoEmail = "testealeatorio@email...com";

        try{
            service.loadUserByUsername(incorretoEmail);
        }catch (Exception ex){
            assertEquals(UsernameNotFoundException.class, ex.getClass());
            assertEquals("Dados inválidos", ex.getMessage());
        }

    }

}