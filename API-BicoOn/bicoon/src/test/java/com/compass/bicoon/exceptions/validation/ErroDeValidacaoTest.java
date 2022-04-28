package com.compass.bicoon.exceptions.validation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.net.URI;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ErroDeValidacaoTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void deveRetornarBadRequestCasoAlumaInformacaoEstejaNulaOuVazia() throws Exception {
        URI uri = new URI("/bicoon/servicos/1");
        String json = "{" +
                " \"categoria\": \"\"," +
                "\"descricao\": \"Cuido de crianças de até 10 anos, levo brinquedos\"" +
                "}";

        mockMvc
                .perform(MockMvcRequestBuilders
                        .put(uri)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .status().isBadRequest());
    }

}
