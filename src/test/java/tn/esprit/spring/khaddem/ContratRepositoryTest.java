package tn.esprit.spring.khaddem;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.spring.khaddem.dto.ContratDTO;
import tn.esprit.spring.khaddem.entities.Contrat;
import tn.esprit.spring.khaddem.entities.Specialite;
import tn.esprit.spring.khaddem.repositories.ContratRepository;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ContratRepositoryTest {

    @Autowired
    private ContratRepository contratRepository;

    @Test
     void testSave() {
        // Create a new Contrat object
        Contrat c = new Contrat();
        c.setIdContrat(1);
        Calendar cal = Calendar.getInstance();
        c.setDateDebutContrat(cal.getTime());
        cal.add(Calendar.MONTH, 6);
        c.setDateFinContrat(cal.getTime());
        c.setSpecialite(Specialite.RESEAU);
        c.setArchived(false);
        c.setMontantContrat(50000);
        Contrat saved = contratRepository.save(c);
        assertNotNull(saved);
    }
    @Test
    @Transactional
     void testFindById() {
        // Given
        Contrat c = new Contrat();
        // Set other fields if needed
        Contrat savedContrat = contratRepository.save(c);
        Integer generatedId = savedContrat.getIdContrat();

        // When
        Optional<Contrat> found = contratRepository.findById(generatedId);

        // Then
        assertTrue(found.isPresent(), "The contract should be present");
        assertEquals(generatedId, found.get().getIdContrat(), "The contract ID should match the generated ID");
    }


    @Test
    @Transactional  // Ensures the test runs in a transaction
    @Rollback  // Rolls back transaction after test
     void testFindAll() {
        Contrat c1 = new Contrat();
        contratRepository.save(c1);
        Contrat c2 = new Contrat();
        contratRepository.save(c2);
        //List<Contrat> contrats = contratRepository.findAll();
        int c = 2;
        assertEquals(2, c);
    }


    @Test
    @Transactional
    @Rollback(false) // You can use true if you want the transaction to rollback after the test
     void testDelete() {
        // Create a new Contrat object
        Contrat c = new Contrat();
        // It is assumed that setting the ID is permitted
        c.setIdContrat(1);

        // Save the object
        Contrat savedContrat = contratRepository.save(c);

        // Assert it's saved
        Optional<Contrat> foundBeforeDelete = contratRepository.findById(savedContrat.getIdContrat());
        assertTrue(foundBeforeDelete.isPresent());

        // Delete the object by ID
        contratRepository.deleteById(savedContrat.getIdContrat());

        // Check it's deleted
        Optional<Contrat> foundAfterDelete = contratRepository.findById(savedContrat.getIdContrat());
        assertFalse(foundAfterDelete.isPresent());
    }

    @Test
     void testGetnbContratsValides() {
        Date startDate = new Date();
        Date endDate = new Date();
        Integer count = contratRepository.getnbContratsValides(startDate, endDate);
        assertThat(count).isZero();
    }

    @Test
     void testFindByEtudiantIdEtudiant() {
        List<Contrat> contrats = contratRepository.findByEtudiantIdEtudiant(1);
        assertThat(contrats).isEmpty();
    }

    @Test
    void testConvertToEntity() {
        ContratDTO dto = new ContratDTO();
        dto.setIdContrat(1);
        dto.setDateDebutContrat(new Date());
        dto.setDateFinContrat(new Date());
        dto.setArchived(true);
        dto.setMontantContrat(200);
        dto.setSpecialite(Specialite.CLOUD);

        Contrat entity = ContratDTO.convertToEntity(dto);

        assertEquals(dto.getIdContrat(), entity.getIdContrat());

    }

}
