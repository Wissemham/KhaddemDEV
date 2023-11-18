package tn.esprit.spring.khaddem;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.spring.khaddem.entities.DetailEquipe;
import tn.esprit.spring.khaddem.repositories.DetailEquipeRepository;
import tn.esprit.spring.khaddem.services.DetailEquipeServiceImpl;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Slf4j
class DetailEquipeServiceImplMockTest {

    @InjectMocks
    private DetailEquipeServiceImpl detailEquipeService;

    @Mock
    private DetailEquipeRepository detailEquipeRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRetrieveAllDetailEquipe() {
        // Arrange
        DetailEquipe detailEquipe1 = new DetailEquipe();
        DetailEquipe detailEquipe2 = new DetailEquipe();
        DetailEquipe detailEquipe3 = new DetailEquipe();
        List<DetailEquipe> detailEquipes = Arrays.asList(detailEquipe1, detailEquipe2, detailEquipe3);
        when(detailEquipeRepository.findAll()).thenReturn(detailEquipes);

        // Act
        List<DetailEquipe> result = detailEquipeService.retrieveAllDetailEquipe();

        // Assert
        verify(detailEquipeRepository, times(1)).findAll();
        assertEquals(3, result.size());
    }

    @Test
    void testAddDetailEquipe() {
        // Arrange
        DetailEquipe detailEquipe = new DetailEquipe();
        when(detailEquipeRepository.save(detailEquipe)).thenReturn(detailEquipe);

        // Act
        DetailEquipe result = detailEquipeService.addDetailEquipe(detailEquipe);

        // Assert
        verify(detailEquipeRepository, times(1)).save(detailEquipe);
        assertEquals(detailEquipe, result);
    }

    @Test
    void testUpdateDetailEquipe() {
        // Arrange
        DetailEquipe detailEquipe = new DetailEquipe();
        detailEquipe.setSalle(1); // Set initial value
        when(detailEquipeRepository.save(detailEquipe)).thenReturn(detailEquipe);

        // Act
        DetailEquipe result = detailEquipeService.updateDetailEquipe(detailEquipe);

        // Assert
        verify(detailEquipeRepository, times(1)).save(detailEquipe);
        assertEquals(detailEquipe, result);
    }

    @Test
    void testSupprimerDetailEquipe() {
        Integer id = 1;
        detailEquipeService.supprimerDetailEquipe(id);
        verify(detailEquipeRepository, times(1)).deleteById(id);
    }
}

