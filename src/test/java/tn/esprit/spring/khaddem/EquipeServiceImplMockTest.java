package tn.esprit.spring.khaddem;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.spring.khaddem.entities.Contrat;
import tn.esprit.spring.khaddem.entities.Equipe;
import tn.esprit.spring.khaddem.entities.Etudiant;
import tn.esprit.spring.khaddem.entities.Niveau;
import tn.esprit.spring.khaddem.repositories.EquipeRepository;
import tn.esprit.spring.khaddem.services.EquipeServiceImpl;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@SpringBootTest
@Slf4j
 class EquipeServiceImplMockTest {
    @InjectMocks
    private EquipeServiceImpl equipeService;

    @Mock
    private EquipeRepository equipeRepository;



    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRetrieveAllEquipes() {
        // Arrange
        Equipe equipe1 = new Equipe();
        Equipe equipe2 = new Equipe();
        Equipe equipe3 = new Equipe();
        List<Equipe> equipes = Arrays.asList(equipe1, equipe2,equipe3);
        when(equipeRepository.findAll()).thenReturn(equipes);

        // Act
        List<Equipe> result = equipeService.retrieveAllEquipes();

        // Assert
        verify(equipeRepository, times(1)).findAll();
        assert(result.size() == 3);
    }
    @Test
    void testRetrieveEquipe_WhenEquipeExists() {
        // Arrange
        //MockitoAnnotations.openMocks(this);
        Integer idEquipe = 1;
        Equipe mockEquipe = new Equipe(); // Create a mock Equipe

        when(equipeRepository.findById(idEquipe)).thenReturn(Optional.of(mockEquipe));

        // Act
        Equipe result = equipeService.retrieveEquipe(idEquipe);

        // Assert
        assertEquals(mockEquipe, result);
        verify(equipeRepository, times(1)).findById(idEquipe);
    }

    @Test
    void testRetrieveEquipe_WhenEquipeDoesNotExist() {
        // Arrange
        //MockitoAnnotations.openMocks(this);
        Integer idEquipe = 2;

        when(equipeRepository.findById(idEquipe)).thenReturn(Optional.empty());

        // Act
        Equipe result = equipeService.retrieveEquipe(idEquipe);

        // Assert
        assertNull(result);
        verify(equipeRepository, times(1)).findById(idEquipe);
    }

    @Test
    void testAddEquipe() {
        // Arrange
        Equipe equipe = new Equipe();
        when(equipeRepository.save(equipe)).thenReturn(equipe);

        // Act
        Equipe result = equipeService.addEquipe(equipe);

        // Assert
        verify(equipeRepository, times(1)).save(equipe);
        assert(result.equals(equipe));
    }

    @Test
    void testUpdateEquipe() {
        // Arrange
        Equipe equipe = new Equipe();
        equipe.setNomEquipe("Test");
        when(equipeRepository.save(equipe)).thenReturn(equipe);
        equipe.setNomEquipe("UpdateNom");
        // Act
        Equipe result = equipeService.updateEquipe(equipe);

        // Assert
        verify(equipeRepository, times(1)).save(equipe);
        assert(result.equals(equipe));
        assertEquals("UpdateNom",result.getNomEquipe());
    }
    @Test
     void testEvoluerEquipes() {
        // Create sample data for testing

        Equipe equipe = new Equipe();
        equipe.setNiveau(Niveau.JUNIOR);
        // Set the initial level to JUNIOR
        // Create a list of Etudiants with active contracts
        List<Etudiant> etudiants = new ArrayList<>();
        Etudiant etudiant1 = new Etudiant();
        etudiants.add(etudiant1);


        List<Contrat> activeContracts = new ArrayList<>();

// Create and add active Contrat objects to the list
        Contrat contrat1 = new Contrat();
// Set properties for contrat1
        contrat1.setDateDebutContrat(new Date()); // Set the start date to the current date
        contrat1.setDateFinContrat(new Date(new Date().getTime() + (365 * 2 * 24 * 60 * 60 * 1000L))); // Set the end date to be 1 year from now
        contrat1.setArchived(false);
        Contrat contrat = new Contrat();
// Set properties for contrat1
        contrat.setDateDebutContrat(new Date()); // Set the start date to the current date
        contrat.setDateFinContrat(new Date(new Date().getTime() + (365 * 2 * 24 * 60 * 60 * 1000L))); // Set the end date to be 1 year from now
        contrat.setArchived(false);
        Contrat contrat2 = new Contrat();
// Set properties for contrat1
        contrat2.setDateDebutContrat(new Date()); // Set the start date to the current date
        contrat2.setDateFinContrat(new Date(new Date().getTime() + (365 * 2 * 24 * 60 * 60 * 1000L))); // Set the end date to be 1 year from now
        contrat2.setArchived(false);

// Add contrat1 to the list
        activeContracts.add(contrat);
        activeContracts.add(contrat1);
        activeContracts.add(contrat2);
        log.info("*****************"+(activeContracts.size()));
        etudiant1.setContrats(activeContracts);
        equipe.setEtudiants(etudiants);
        // Mock the behavior of equipeRepository to return this equipe when findAll is called
        when(equipeRepository.findAll()).thenReturn(Collections.singletonList(equipe));
        // Mock the behavior of contratRepository to return active contracts for the etudiants
        //Mockito.when(contratRepository.findByEtudiantIdEtudiant((int) anyLong())).thenReturn();

        // Call the method to test
        equipeService.evoluerEquipes();

        // Add assertions to verify the behavior
        // For example, assert that the equipe's level has been updated to SENIOR if the conditions are met
        assertEquals(Niveau.SENIOR, equipe.getNiveau());
    }
    @Test
    void testEvoluerEquipesUpdateToExpert() {
        // Create sample data for testing
        Equipe equipe = new Equipe();
        equipe.setNiveau(Niveau.SENIOR);

        // Create a list of Etudiants with active contracts
        List<Etudiant> etudiants = new ArrayList<>();
        Etudiant etudiant1 = new Etudiant();
        etudiants.add(etudiant1);

        // Create and add active Contrat objects to the list
        List<Contrat> activeContracts = new ArrayList<>();
        Contrat contrat1 = new Contrat();
        contrat1.setDateDebutContrat(new Date());
        contrat1.setDateFinContrat(new Date(new Date().getTime() + (365 * 2 * 24 * 60 * 60 * 1000L)));
        contrat1.setArchived(false);

        Contrat contrat2 = new Contrat();
        contrat2.setDateDebutContrat(new Date());
        contrat2.setDateFinContrat(new Date(new Date().getTime() + (365 * 2 * 24 * 60 * 60 * 1000L)));
        contrat2.setArchived(false);

        Contrat contrat3 = new Contrat();
        contrat3.setDateDebutContrat(new Date());
        contrat3.setDateFinContrat(new Date(new Date().getTime() + (365 * 2 * 24 * 60 * 60 * 1000L)));
        contrat3.setArchived(false);

        activeContracts.add(contrat1);
        activeContracts.add(contrat2);
        activeContracts.add(contrat3);

        etudiant1.setContrats(activeContracts);
        equipe.setEtudiants(etudiants);

        // Mock the behavior of equipeRepository to return this equipe when findAll is called
        when(equipeRepository.findAll()).thenReturn(Collections.singletonList(equipe));

        // Call the method to test
        equipeService.evoluerEquipes();

        // Verify that the equipe's level has been updated to EXPERT if the conditions are met
        assertEquals(Niveau.EXPERT, equipe.getNiveau());
    }
}
