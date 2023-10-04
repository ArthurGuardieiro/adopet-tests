package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.CadastroPetDto;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.PetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class PetServiceTest {

    @InjectMocks
    private PetService petService;

    @Mock
    private PetRepository repository;

    @Mock
    private CadastroPetDto dto;

    @Mock
    private Abrigo abrigo;

    @Test
    void deveriaMostrarPetsDisponiveis() {
        petService.buscarPetsDisponiveis();

        then(repository).should().findAllByAdotadoFalse();
    }

    @Test
    void deveriaCadastrarPet() {
        petService.cadastrarPet(abrigo, dto);

        then(repository).should().save(new Pet(dto, abrigo));
    }

}