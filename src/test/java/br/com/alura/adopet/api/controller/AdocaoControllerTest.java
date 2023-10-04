package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.AprovacaoAdocaoDto;
import br.com.alura.adopet.api.dto.ReprovacaoAdocaoDto;
import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.service.AdocaoService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class AdocaoControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<SolicitacaoAdocaoDto> jsdonDto;

    @MockBean
    private AdocaoService adocaoService;

    @Autowired
    JacksonTester<AprovacaoAdocaoDto> aprovacaoAdocaoDtoJacksonTester;

    @Autowired
    JacksonTester<ReprovacaoAdocaoDto> reprovacaoAdocaoDtoJacksonTester;

    @Test
    void deveriaRetornarCodigo400ParaSolicitacaoDeAdocaoComErros() throws Exception {
        SolicitacaoAdocaoDto dto = new SolicitacaoAdocaoDto(null, null, null);

        MockHttpServletResponse response = mvc.perform(
                post("/adocoes")
                        .content(jsdonDto.write(dto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(400, response.getStatus());
    }

    @Test
    void deveriaRetornarCodigo200ParaSolicitacaoDeAdocaoSemErros() throws Exception {
        SolicitacaoAdocaoDto dto = new SolicitacaoAdocaoDto(1l, 2l, "Qualquer");

        MockHttpServletResponse response = mvc.perform(
                post("/adocoes")
                        .content(jsdonDto.write(dto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(200, response.getStatus());
    }

    @Test
    void deveriaRetornarCodigo200ParaSolicitacaoDeAprovacaoSemErros() throws Exception {
        AprovacaoAdocaoDto dto = new AprovacaoAdocaoDto(1L);

        MockHttpServletResponse response = mvc.perform(
                put("/adocoes/aprovar")
                        .content(aprovacaoAdocaoDtoJacksonTester.write(dto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void deveriaRetornarCodigo400ParaSolicitacaoDeAprovacaoComErros() throws Exception {
        AprovacaoAdocaoDto dto = new AprovacaoAdocaoDto(null);

        MockHttpServletResponse response = mvc.perform(
                put("/adocoes/aprovar")
                        .content(aprovacaoAdocaoDtoJacksonTester.write(dto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(400, response.getStatus());
    }

    @Test
    void deveriaRetornarCodigo200ParaRequisicaoDeReprovacaoSemErros() throws Exception {
        ReprovacaoAdocaoDto dto = new ReprovacaoAdocaoDto(1L, "qualquer");

        MockHttpServletResponse response = mvc.perform(
                put("/adocoes/reprovar")
                        .content(reprovacaoAdocaoDtoJacksonTester.write(dto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void deveriaRetornarCodigo400ParaRequisicaoDeReprovacaoComErros() throws Exception {
        ReprovacaoAdocaoDto dto = new ReprovacaoAdocaoDto(null, null);

        MockHttpServletResponse response = mvc.perform(
                put("/adocoes/reprovar")
                        .content(reprovacaoAdocaoDtoJacksonTester.write(dto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(400, response.getStatus());
    }



}