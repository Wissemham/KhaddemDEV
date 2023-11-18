package tn.esprit.spring.khaddem;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.spring.khaddem.dto.DetailEquipeDTO;
import tn.esprit.spring.khaddem.entities.DetailEquipe;
import tn.esprit.spring.khaddem.repositories.DetailEquipeRepository;

import java.util.List;

@SpringBootTest
class DetailEquipeRepositoryTest {
    @Autowired
    private DetailEquipeRepository detailEquipeRepository;

    @Test
    void testRetrieveAllDetailEquipes() {
        // Save some DetailEquipe entities to the database for testing
        DetailEquipe detailEquipe1 = new DetailEquipe();
        detailEquipe1.setSalle(1);
        detailEquipe1.setThematique("Thematique 1");

        DetailEquipe detailEquipe2 = new DetailEquipe();
        detailEquipe2.setSalle(2);
        detailEquipe2.setThematique("Thematique 2");

        detailEquipeRepository.save(detailEquipe1);
        detailEquipeRepository.save(detailEquipe2);

        // Retrieve all DetailEquipe entities from the repository
        List<DetailEquipe> allDetailEquipes = detailEquipeRepository.findAll();

        // Check if the retrieved list is not empty
        Assertions.assertFalse(allDetailEquipes.isEmpty());

        // Check if the number of retrieved entities matches the number of saved entities
        Assertions.assertEquals(2, allDetailEquipes.size());

        // Clean up: Delete the test entities from the database
        detailEquipeRepository.delete(detailEquipe1);
        detailEquipeRepository.delete(detailEquipe2);
    }

    @Test
    void testAddDetailEquipe() {
        DetailEquipe detailEquipe = new DetailEquipe();
        detailEquipe.setSalle(1);
        detailEquipe.setThematique("Test Thematique");

        DetailEquipe savedDetailEquipe = detailEquipeRepository.save(detailEquipe);

        Assertions.assertNotNull(savedDetailEquipe.getIdDetailEquipe());

        // Clean up: Delete the test entity from the database
        detailEquipeRepository.delete(detailEquipe);
    }

    @Test
    void testUpdateDetailEquipe() {
        DetailEquipe detailEquipe = new DetailEquipe();
        detailEquipe.setSalle(1);
        detailEquipe.setThematique("Test Thematique");

        DetailEquipe savedDetailEquipe = detailEquipeRepository.save(detailEquipe);

        Assertions.assertNotNull(savedDetailEquipe.getIdDetailEquipe());

        // Make changes to the detail equipe
        savedDetailEquipe.setSalle(2);
        savedDetailEquipe.setThematique("Updated Thematique");

        DetailEquipe updatedDetailEquipe = detailEquipeRepository.save(savedDetailEquipe);

        Assertions.assertEquals(2, updatedDetailEquipe.getSalle());
        Assertions.assertEquals("Updated Thematique", updatedDetailEquipe.getThematique());

        // Clean up: Delete the test entity from the database
        detailEquipeRepository.delete(detailEquipe);
    }

    @Test
    void testRetrieveDetailEquipe() {
        DetailEquipe detailEquipe = new DetailEquipe();
        detailEquipe.setSalle(1);
        detailEquipe.setThematique("Test Thematique");

        DetailEquipe savedDetailEquipe = detailEquipeRepository.save(detailEquipe);

        Assertions.assertNotNull(savedDetailEquipe.getIdDetailEquipe());

        DetailEquipe retrievedDetailEquipe = detailEquipeRepository.findById(savedDetailEquipe.getIdDetailEquipe()).orElse(null);

        Assertions.assertNotNull(retrievedDetailEquipe);
        Assertions.assertEquals(savedDetailEquipe.getIdDetailEquipe(), retrievedDetailEquipe.getIdDetailEquipe());

        // Clean up: Delete the test entity from the database
        detailEquipeRepository.delete(detailEquipe);
    }

    @Test
    void testConvertToDTO() {
        DetailEquipe detailEquipe = new DetailEquipe();
        detailEquipe.setIdDetailEquipe(1);
        detailEquipe.setSalle(1);
        detailEquipe.setThematique("Sample Thematique");

        DetailEquipeDTO detailEquipeDto = new DetailEquipeDTO();
        detailEquipeDto = detailEquipeDto.convertToDTO(detailEquipe);

        Assertions.assertEquals(detailEquipe.getIdDetailEquipe(), detailEquipeDto.getIdDetailEquipe());
        Assertions.assertEquals(detailEquipe.getSalle(), detailEquipeDto.getSalle());
        Assertions.assertEquals(detailEquipe.getThematique(), detailEquipeDto.getThematique());
    }

    @Test
    void testConvertToEntity() {
        DetailEquipeDTO detailEquipeDto = new DetailEquipeDTO();
        detailEquipeDto.setIdDetailEquipe(1);
        detailEquipeDto.setSalle(1);
        detailEquipeDto.setThematique("Sample Thematique");

        DetailEquipe detailEquipe = detailEquipeDto.convertToEntity(detailEquipeDto);

        Assertions.assertEquals(detailEquipeDto.getIdDetailEquipe(), detailEquipe.getIdDetailEquipe());
        Assertions.assertEquals(detailEquipeDto.getSalle(), detailEquipe.getSalle());
        Assertions.assertEquals(detailEquipeDto.getThematique(), detailEquipe.getThematique());
    }
}

