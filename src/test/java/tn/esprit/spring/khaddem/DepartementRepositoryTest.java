package tn.esprit.spring.khaddem;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.spring.khaddem.dto.DepartementDTO;
import tn.esprit.spring.khaddem.entities.Departement;
import tn.esprit.spring.khaddem.repositories.DepartementRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest

 class DepartementRepositoryTest {
    @Autowired
    private DepartementRepository departementRepository;

    @Test
     void testRetrieveAllDepartements() {
        // Save some Departement entities to the database for testing
        Departement departement1 = new Departement();
        departement1.setNomDepart("Department 1");
        Departement departement2 = new Departement();
        departement2.setNomDepart("Department 2");
        departementRepository.save(departement1);
        departementRepository.save(departement2);

        // Retrieve all Departement entities from the repository
        List<Departement> allDepartements = departementRepository.findAll();
        // Check if the retrieved list is not empty
        assertFalse(allDepartements.isEmpty());
        // Check if the number of retrieved entities matches the number of saved entities
        assertEquals(2, allDepartements.size());
        // Clean up: Delete the test entities from the database
        departementRepository.delete(departement1);
        departementRepository.delete(departement2);


    }

    @Test
    void testAddDepartement() {
        Departement department = new Departement();
        department.setNomDepart("Test Department");

        Departement savedDepartment = departementRepository.save(department);
        assertNotNull(savedDepartment.getIdDepartement());
        departementRepository.delete(department);

    }

    @Test
     void testUpdateDepartement() {
        Departement department = new Departement();
        department.setNomDepart("Test Department");

        Departement savedDepartment = departementRepository.save(department);
        assertNotNull(savedDepartment.getIdDepartement());

        // Make changes to the department

        savedDepartment.setNomDepart("Updated Department");

        Departement updatedDepartment = departementRepository.save(savedDepartment);
        assertEquals("Updated Department", updatedDepartment.getNomDepart());
        departementRepository.delete(department);
    }

    @Test
    void testRetrieveDepartement() {
        Departement department = new Departement();
        department.setNomDepart("Test Department");

        Departement savedDepartment = departementRepository.save(department);
        assertNotNull(savedDepartment.getIdDepartement());

        Departement retrievedDepartment = departementRepository.findById(savedDepartment.getIdDepartement()).orElse(null);
        assertNotNull(retrievedDepartment);
        assertEquals(savedDepartment.getIdDepartement(), retrievedDepartment.getIdDepartement());
        //System.out.println(retrievedDepartment.getIdDepartement());
        departementRepository.delete(department);
    }
    @Test
     void testConvertToDTO() {
        Departement departement = new Departement();
        departement.setIdDepartement(1);
        departement.setNomDepart("Sample Department");

        DepartementDTO departementDTO = new DepartementDTO();
        departementDTO = departementDTO.convertToDTO(departement);

        Assertions.assertEquals(departement.getIdDepartement(), departementDTO.getIdDepartement());
        Assertions.assertEquals(departement.getNomDepart(), departementDTO.getNomDepart());
    }

    @Test
    void testConvertToEntity() {
        DepartementDTO departementDTO = new DepartementDTO();
        departementDTO.setIdDepartement(1);
        departementDTO.setNomDepart("Sample Department");

        Departement departement = departementDTO.convertToEntity(departementDTO);

        Assertions.assertEquals(departementDTO.getIdDepartement(), departement.getIdDepartement());
        Assertions.assertEquals(departementDTO.getNomDepart(), departement.getNomDepart());
    }
}

