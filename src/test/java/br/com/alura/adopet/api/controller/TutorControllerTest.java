package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.AtualizacaoTutorDto;
import br.com.alura.adopet.api.dto.CadastroTutorDto;
import br.com.alura.adopet.api.repository.TutorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class TutorControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<CadastroTutorDto> cadastroTutorDtoJacksonTester;

    @Autowired
    private JacksonTester<AtualizacaoTutorDto> atualizacaoTutorDtoJacksonTester;

    @Test
    void deveriaRetornarCodigo200ParaSolicitacaoDeCadastroSemErros() throws Exception {
        CadastroTutorDto dto = new CadastroTutorDto("Rodrigo", "(21)0000-9099", "email5@example.com.br");

        MockHttpServletResponse response = mvc.perform(
                post("/tutores")
                        .content(cadastroTutorDtoJacksonTester.write(dto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());

    }

    @Test
    void deveriaRetornarCodigo400ParaSolicitacaoDeCadastroComErros() throws Exception {
        CadastroTutorDto dto = new CadastroTutorDto(null, null, null);

        MockHttpServletResponse response = mvc.perform(
                post("/tutores")
                        .content(cadastroTutorDtoJacksonTester.write(dto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(400, response.getStatus());

    }

    @Test
    void deveriaRetornarCodigo200ParaRequisicaoDeAtualizarTutor() throws Exception{
        AtualizacaoTutorDto dto = new AtualizacaoTutorDto(1L, "Joao", "(21)0001-9092", "joao22@gmail.com");

        MockHttpServletResponse response = mvc.perform(
                put("/tutores")
                        .content(atualizacaoTutorDtoJacksonTester.write(dto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());

    }

    @Test
    void deveriaRetornarCodigo400ParaRequisicaoDeAtualizarTutor() throws Exception{
        AtualizacaoTutorDto dto = new AtualizacaoTutorDto(2L, "Rodrigo", "(21)0000-90900", "email@example.com.br");

        MockHttpServletResponse response = mvc.perform(
                put("/tutores")
                        .content(atualizacaoTutorDtoJacksonTester.write(dto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(400, response.getStatus());

    }

}