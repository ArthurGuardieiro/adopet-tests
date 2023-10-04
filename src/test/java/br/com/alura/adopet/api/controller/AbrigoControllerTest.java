package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.CadastroAbrigoDto;
import br.com.alura.adopet.api.dto.CadastroPetDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.TipoPet;
import br.com.alura.adopet.api.service.AbrigoService;
import br.com.alura.adopet.api.service.PetService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class AbrigoControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<CadastroAbrigoDto> jsonDto;

    @MockBean
    private AbrigoService abrigoService;

    @MockBean
    private PetService petService;

    @MockBean
    private Abrigo abrigo;

    @Autowired
    private JacksonTester<CadastroPetDto> cadastroPetDtoJacksonTester;

    @Test
    void deveriaRetornarCodigo200ParaCadastroSemErros() throws Exception {
        CadastroAbrigoDto dto = new CadastroAbrigoDto("ceu azul", "34997965435", "ceuazul@gmail.com");

        MockHttpServletResponse response = mvc.perform(
                post("/abrigos")
                        .content(jsonDto.write(dto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());

    }

    @Test
    void deveriaRetornarCodigo400ParaCadastroComErros() throws Exception {
        CadastroAbrigoDto dto = new CadastroAbrigoDto(null, null, null);

        MockHttpServletResponse response = mvc.perform(
                post("/abrigos")
                        .content(jsonDto.write(dto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(400, response.getStatus());

    }

    @Test
    void deveriaRetornarCodigo200ParaRequisicaoDeListar() throws Exception {
        MockHttpServletResponse response = mvc.perform(
                get("/abrigos")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void deveriaRetornarCodigo200ParaRequisicaoDeListarPetsDoAbrigoPorNome() throws Exception {
        String nome = "Abrigo feliz";

        MockHttpServletResponse response = mvc.perform(
                get("/abrigos/{nome}/pets", nome)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void deveriaRetornarCodigo200ParaRequisicaoDeListarPetsDoAbrigoPorId() throws Exception {
        String id = "1";

        MockHttpServletResponse response = mvc.perform(
                get("/abrigos/{id}/pets", id)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void deveriaRetornarCodigo404ParaRequisicaoDeListarPetsDoAbrigoPorNomeInvalido() throws Exception {
        String nome = "Abrigo feliz";
        given(abrigoService.listarPetsDoAbrigo(nome)).willThrow(ValidacaoException.class);

        MockHttpServletResponse response = mvc.perform(
                get("/abrigos/{nome}/pets", nome)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(404, response.getStatus());
    }

    @Test
    void deveriaRetornarCodigo404ParaRequisicaoDeListarPetsDoAbrigoPorIdInvalido() throws Exception {
        String id = "1";
        given(abrigoService.listarPetsDoAbrigo(id)).willThrow(ValidacaoException.class);

        MockHttpServletResponse response = mvc.perform(
                get("/abrigos/{nome}/pets", id)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(404, response.getStatus());
    }

    @Test
    void deveriaRetornarCodigo200ParaSolicitacaoDeCadastroDePetPeloNome() throws Exception {
        CadastroPetDto dto = new CadastroPetDto(TipoPet.GATO, "Miau", "padrao", 10, "azul", 7.3f);
        String nomeAbrigo = "Abrigo feliz";

        MockHttpServletResponse response = mvc.perform(
                post("/abrigos/{idOuNome}/pets", nomeAbrigo)
                        .content(cadastroPetDtoJacksonTester.write(dto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());

    }

    @Test
    void deveriaRetornarCodigo200ParaSolicitacaoDeCadastroDePetPeloId() throws Exception {
        CadastroPetDto dto = new CadastroPetDto(TipoPet.GATO, "Miau", "padrao", 10, "azul", 7.3f);
        String id = "1";

        MockHttpServletResponse response = mvc.perform(
                post("/abrigos/{idOuNome}/pets", id)
                        .content(cadastroPetDtoJacksonTester.write(dto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());

    }

    @Test
    void deveriaRetornarCodigo404ParaSolicitacaoDeCadastroDeAbrigoNaoEncontradoPetPeloNome() throws Exception {
        CadastroPetDto dto = new CadastroPetDto(TipoPet.GATO, "Miau", "padrao", 10, "azul", 7.3f);
        String nomeAbrigo = "Abrigo feliz";
        given(abrigoService.carregarAbrigo(nomeAbrigo)).willThrow(ValidacaoException.class);

        MockHttpServletResponse response = mvc.perform(
                post("/abrigos/{idOuNome}/pets", nomeAbrigo)
                        .content(cadastroPetDtoJacksonTester.write(dto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(404, response.getStatus());

    }

    @Test
    void deveriaRetornarCodigo404ParaSolicitacaoDeCadastroDeAbrigoNaoEncontradoPetPeloId() throws Exception {
        CadastroPetDto dto = new CadastroPetDto(TipoPet.GATO, "Miau", "padrao", 10, "azul", 7.3f);
        String id = "1";
        given(abrigoService.carregarAbrigo(id)).willThrow(ValidacaoException.class);

        MockHttpServletResponse response = mvc.perform(
                post("/abrigos/{idOuNome}/pets", id)
                        .content(cadastroPetDtoJacksonTester.write(dto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(404, response.getStatus());

    }

}