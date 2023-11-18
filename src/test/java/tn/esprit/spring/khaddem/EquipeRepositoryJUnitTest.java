package tn.esprit.spring.khaddem;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import tn.esprit.spring.khaddem.dto.EquipeDTO;
import tn.esprit.spring.khaddem.entities.Equipe;
import tn.esprit.spring.khaddem.entities.Niveau;
import tn.esprit.spring.khaddem.repositories.EquipeRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ContextConfiguration
 class EquipeRepositoryJUnitTest {
    @Autowired
    private EquipeRepository equipeRepository;

    @Test
    void testSaveEquipe() {
        // Create an Equipe entity
        Equipe equipe = Equipe.builder()
                .nomEquipe("Test Team")
                .niveau(Niveau.SENIOR)  // Replace with the appropriate level
                .build();
        // Save the entity to the database
        equipeRepository.save(equipe);
        // Retrieve the entity from the database
        Equipe savedEquipe = equipeRepository.findById(equipe.getIdEquipe()).orElse(null);
        // Verify that the saved entity is not null
        assertNotNull(savedEquipe);
        // Verify that the retrieved entity has the same name
        assertEquals("Test Team", savedEquipe.getNomEquipe());
        equipeRepository.delete(equipe);
    }




    @Test
    void testFindAll() {
        // Save some Equipe entities to the database for testing
        Equipe equipe1 = new Equipe();
        equipe1.setNomEquipe("Test1");
        Equipe equipe2 = new Equipe();
        equipe2.setNomEquipe("Test2");
        equipeRepository.save(equipe1);
        equipeRepository.save(equipe2);

        // Retrieve all Equipe entities from the repository
        List<Equipe> allEquipes = equipeRepository.findAll();

        // Check if the retrieved list is not empty
        assertFalse(allEquipes.isEmpty());

        // Check if the number of retrieved entities matches the number of saved entities
        assertEquals(2, allEquipes.size());
        equipeRepository.delete(equipe1);
        equipeRepository.delete(equipe2);
    }

    @Test
    void testFindById() {
        // Save an Equipe entity to the database for testing
        Equipe equipe = new Equipe();
        equipe.setNomEquipe("Test");
        equipeRepository.save(equipe);

        // Retrieve the saved Equipe entity by its ID
        Equipe retrievedEquipe = equipeRepository.findById(equipe.getIdEquipe()).orElse(null);

        // Check if the retrieved entity is not null
        assertNotNull(retrievedEquipe);

        // Check if the retrieved entity matches the saved entity
        assertEquals(equipe.getNomEquipe(), retrievedEquipe.getNomEquipe());
        equipeRepository.delete(equipe);
    }

    @Test
    void testDelete() {
        // Create a new Equipe entity and save it to the database
        Equipe equipe = new Equipe();
        equipe.setNomEquipe("test");
        equipeRepository.save(equipe);

        // Delete the saved Equipe entity from the database
        equipeRepository.delete(equipe);

        // Try to retrieve the deleted entity, it should be null
        Equipe deletedEquipe = equipeRepository.findById(equipe.getIdEquipe()).orElse(null);
        assertNull(deletedEquipe);
    }
    @Test
    void testConvertDTOtoEntity() {
        EquipeDTO equipeDTO = new EquipeDTO();
        equipeDTO.setIdEquipe(1);
        equipeDTO.setNomEquipe("Test");
        equipeDTO.setNiveau(Niveau.JUNIOR);
        equipeDTO.setDetailEquipe(null);
        equipeDTO.setEtudiants(new ArrayList<>());

        Equipe eq = EquipeDTO.convertDTOtoEntity(equipeDTO); // Use class name to access static method

        assertEquals(equipeDTO.getIdEquipe(), eq.getIdEquipe());
    }
}
