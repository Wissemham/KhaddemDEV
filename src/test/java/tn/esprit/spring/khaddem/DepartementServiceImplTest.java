package tn.esprit.spring.khaddem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;


import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.khaddem.entities.Departement;
import tn.esprit.spring.khaddem.entities.Universite;
import tn.esprit.spring.khaddem.repositories.DepartementRepository;
import tn.esprit.spring.khaddem.repositories.UniversiteRepository;
import tn.esprit.spring.khaddem.services.DepartementServiceImpl;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
 class DepartementServiceImplTest {
    @InjectMocks
    private DepartementServiceImpl departementService;

    @Mock
    private DepartementRepository departementRepository;

    @Mock
    private UniversiteRepository universiteRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRetrieveAllDepartements() {
        Departement departement1 = new Departement();
        departement1.setNomDepart("Esprit Departement");

        Departement departement2 = new Departement();
        departement2.setNomDepart("EAmen Departement");


        List<Departement> departmentList = Arrays.asList(departement1, departement2);

        // Mock the behavior of the departementRepository to return the list of departments
        Mockito.when(departementRepository.findAll()).thenReturn(departmentList);

        // Act
        List<Departement> result = departementService.retrieveAllDepartements();

        Mockito.verify(departementRepository, Mockito.times(1)).findAll();
        assert (result.size() == 2);
    }

    @Test
    void testAddDepartement() {
        // Create a mock Departement object
        Departement departement = new Departement();
        departement.setNomDepart("Test Department");

        // Define the behavior of the mock repository when saving
        // when(departementRepository.save(departement)).thenReturn(departement);

        // Perform the test
        Departement addedDepartement = departementService.addDepartement(departement);

        // Verify the result
        assertNotNull(addedDepartement);
        assertEquals("Test Department", addedDepartement.getNomDepart());
        System.out.println(addedDepartement.getNomDepart() + " ajouter");
    }

    @Test
    void testUpdateDepartement() {
        Departement existingDepartement = new Departement();
        existingDepartement.setIdDepartement(1);
        existingDepartement.setNomDepart("Esp Departement");
        //when(departementRepository.findById(existingDepartement.getIdDepartement())).thenReturn(Optional.of(existingDepartement));
        when(departementRepository.save(any(Departement.class))).thenAnswer(invocation -> {
            Departement updatedEntity = invocation.getArgument(0);
            existingDepartement.setNomDepart(updatedEntity.getNomDepart());
            return existingDepartement;
        });

        System.err.println("Before Test Update = " + existingDepartement.getNomDepart());
        existingDepartement.setNomDepart("Esp1 Departement Name");
        Departement updatedDepartement = departementService.updateDepartement(existingDepartement);
        assertEquals("Esp1 Departement Name", updatedDepartement.getNomDepart());
        verify(departementRepository).save(existingDepartement);

        System.err.println("After Test Update = " + existingDepartement.getNomDepart());
    }

    @Test
    void testRetrieveDepartement() {
        Departement departement = new Departement();
        departement.setIdDepartement(1);
        departement.setNomDepart("Department 1");

        // Mock the behavior of the departementRepository
        when(departementRepository.findById(1)).thenReturn(Optional.of(departement));

        // Call the service method
        Departement result = departementService.retrieveDepartement(1);

        // Assert that the result matches the retrieved Departement
        assertNotNull(result);
        assertEquals("Department 1", result.getNomDepart());
    }

    @Test
    void testRetrieveDepartementsByUniversite() {
        // Set up departementService specifically for this test
        departementService = new DepartementServiceImpl(null, universiteRepository);
        // Create an example university and associated departments
        Universite university = new Universite();
        university.setIdUniversite(1);

        List<Departement> associatedDepartments = new ArrayList<>();
        Departement department1 = new Departement();
        department1.setIdDepartement(1);
        department1.setNomDepart("Department 1");

        Departement department2 = new Departement();
        department2.setIdDepartement(2);
        department2.setNomDepart("Department 2");

        associatedDepartments.add(department1);
        associatedDepartments.add(department2);

        university.setDepartements(associatedDepartments);

        // Configure the behavior of the universiteRepository to return the university when searched by ID
        Mockito.when(universiteRepository.findById(university.getIdUniversite())).thenReturn(Optional.of(university));

        // Call the method to be tested
        List<Departement> retrievedDepartments = departementService.retrieveDepartementsByUniversite(university.getIdUniversite());

        // Assertions to check the results
        assertEquals(associatedDepartments.size(), retrievedDepartments.size());
        for (Departement associatedDepartment : associatedDepartments) {
            assertTrue(retrievedDepartments.contains(associatedDepartment));
        }
    }

    @Test
    void testRetrieveDepartementDepartmentNotFound() {
        int nonExistentDepartmentId = 999;
        // Mock the behavior of departementRepository to return an empty Optional, simulating department not found
        when(departementRepository.findById(nonExistentDepartmentId)).thenReturn(Optional.empty());
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () ->
                departementService.retrieveDepartement(nonExistentDepartmentId));
        // Verify that the exception message contains the expected error message
        assertEquals("Department not found for ID: " + nonExistentDepartmentId, exception.getMessage());
    }

    @Test
    void testRetrieveDepartementsByUniversiteUniversityNotFound() {
        int nonExistentUniversityId = 999;
        when(universiteRepository.findById(nonExistentUniversityId)).thenReturn(Optional.empty());
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () ->
                departementService.retrieveDepartementsByUniversite(nonExistentUniversityId));
        assertEquals("University not found for ID: " + nonExistentUniversityId, exception.getMessage());
    }

}

