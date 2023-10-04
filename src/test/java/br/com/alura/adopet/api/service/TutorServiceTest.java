package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.AtualizacaoTutorDto;
import br.com.alura.adopet.api.dto.CadastroTutorDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.TutorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class TutorServiceTest {

    @InjectMocks
    private TutorService tutorService;
    @Mock
    private CadastroTutorDto dto;
    @Mock
    private TutorRepository tutorRepository;

    @Mock
    private AtualizacaoTutorDto atualizacaoTutorDto;

    @Mock
    private Tutor tutor;

    @Test
    void deveriaCadastrarTutor() {
        given(tutorRepository.existsByTelefoneOrEmail(dto.telefone(), dto.email())).willReturn(false);

        tutorService.cadastrar(dto);

        then(tutorRepository).should().save(new Tutor(dto));
    }

    @Test
    void naoDeveriaCadastrarTutor() {
        given(tutorRepository.existsByTelefoneOrEmail(dto.telefone(), dto.email())).willReturn(true);

        //tutorService.cadastrar(dto);

        Assertions.assertThrows(ValidacaoException.class, () -> tutorService.cadastrar(dto));
    }

    @Test
    void deveriaAtualizarDadosTutor() {
        given(tutorRepository.getReferenceById(atualizacaoTutorDto.id())).willReturn(tutor);

        tutorService.atualizar(atualizacaoTutorDto);

        then(tutor).should().atualizarDados(atualizacaoTutorDto);
    }

}