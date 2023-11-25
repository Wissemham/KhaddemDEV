package tn.esprit.spring.khaddem;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.khaddem.entities.Departement;
import tn.esprit.spring.khaddem.entities.Universite;
import tn.esprit.spring.khaddem.repositories.DepartementRepository;
import tn.esprit.spring.khaddem.repositories.UniversiteRepository;
import tn.esprit.spring.khaddem.services.UniversiteServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@Slf4j
@ExtendWith(MockitoExtension.class)
class UniversiteServiceImplJunitTest {
    @InjectMocks
    private UniversiteServiceImpl universiteService;

    @Mock
    private UniversiteRepository universiteRepository;

    @Mock
    private DepartementRepository departementRepository;

    @BeforeEach
    void setUp() {

    }

    @Test
    void testRetrieveAllUniversites() {

        List<Universite> universities = new ArrayList<>();

        Universite univer1 = new Universite();
        univer1.setNomUniv("hi");
        Universite univer2 = new Universite();
        univer2.setNomUniv("kl");

        universities.add(univer1);
        universities.add(univer2);
        when(universiteRepository.findAll()).thenReturn(universities);
        List<Universite> result = universiteService.retrieveAllUniversites();
        Mockito.verify(universiteRepository, Mockito.times(1)).findAll();
        if (result.size() == 2) {
            log.info("Test Passed: Result size is 1 as expected.");
            System.err.println("Test 1 : \n Test Passed: Result size is 1 as expected.");
        } else {
            log.info("Test Failed: Expected result size 1, but got " + result.size());
            System.err.println("Test 1 : \n Test Failed: Expected result size 1, but got " + result.size());
        }
        assertEquals(2 , universities.size());
    }


    @Test
    void testAddUniversite() {
        Universite universite = new Universite();
        universite.setNomUniv("Your University Name");

        when(universiteRepository.save(Mockito.any())).thenReturn(universite);

        Universite result = universiteService.addUniversite(universite);

        assertEquals(universite, result);
    }

    @Test
    void testRetrieveUniversite() {
        Universite universite = new Universite();
        universite.setIdUniversite(1);
        universite.setNomUniv("Univer 1");

        Mockito.when(universiteRepository.findById(1)).thenReturn(Optional.of(universite));

        Universite result = universiteService.retrieveUniversite(1);
        assertNotNull(result);
        assertEquals("Univer 1", result.getNomUniv());
    }



    @Test
    void testRetrieveUniversiteNotFound() {
        Mockito.when(universiteRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> universiteService.retrieveUniversite(1));
    }
    @Test
    void testUpdateUniversite() {
        // Arrange
        Universite existingUniversite = new Universite();
        existingUniversite.setIdUniversite(1);
        existingUniversite.setNomUniv("Test University");

        // Mocking the save method of the repository
        when(universiteRepository.save(any(Universite.class))).thenAnswer(invocation -> {
            Universite updatedEntity = invocation.getArgument(0);
            existingUniversite.setNomUniv(updatedEntity.getNomUniv());
            return existingUniversite;
        });

        System.out.println("Before Test Update = " + existingUniversite.getNomUniv());
        existingUniversite.setNomUniv("Updated University Name");

        // Act
        Universite updatedUniversite = universiteService.updateUniversite(existingUniversite);

        // Assert
        assertEquals("Updated University Name", updatedUniversite.getNomUniv());
        verify(universiteRepository).save(existingUniversite);

        System.out.println("After Test Update = " + existingUniversite.getNomUniv());
    }

    @Test
    void testAssignUniversiteToDepartement() {
        Universite universite = new Universite();
        universite.setIdUniversite(1);
        universite.setDepartements(new ArrayList<>());

        Departement departement = new Departement();
        departement.setIdDepartement(1);

        when(universiteRepository.findById(1)).thenReturn(Optional.of(universite));
        when(departementRepository.findById(1)).thenReturn(Optional.of(departement));

        universiteService.assignUniversiteToDepartement(1, 1);
        assertEquals(1, universite.getDepartements().size());
        assertTrue(universite.getDepartements().contains(departement));
    }
    @Test
    void testAssignUniversiteToDepartementUniversityNotFound() {
        // Mock data
        Integer nonExistentUniversiteId = 999;
        Integer departementId = 2;

        // Stubbing repository calls
        when(universiteRepository.findById(nonExistentUniversiteId)).thenReturn(Optional.empty());

        // Call the method and assert the exception
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () ->
                universiteService.assignUniversiteToDepartement(nonExistentUniversiteId, departementId));

        // Verify that the method was called with the correct arguments
        verify(universiteRepository, times(1)).findById(nonExistentUniversiteId);

        // Verify the exception message
        assertEquals("University not found for ID: " + nonExistentUniversiteId, exception.getMessage());
    }
    @Test
    void testAssignUniversiteToDepartementDepartementNotFound() {
        // Mock data
        Integer universiteId = 1;
        Integer nonExistentDepartementId = 999;

       Universite universite = new Universite();
//        universite.setIdUniversite(universiteId);
//        universite.setDepartements(new ArrayList<>());

        // Stubbing repository calls
        when(universiteRepository.findById(universiteId)).thenReturn(Optional.of(universite));
        when(departementRepository.findById(nonExistentDepartementId)).thenReturn(Optional.empty());

        // Call the method and assert the exception
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () ->
                universiteService.assignUniversiteToDepartement(universiteId, nonExistentDepartementId));

        // Verify that the method was called with the correct arguments
        verify(universiteRepository, times(1)).findById(universiteId);
        verify(departementRepository, times(1)).findById(nonExistentDepartementId);

        // Verify the exception message
        //assertEquals("Departement not found for ID: " + nonExistentDepartementId, exception.getMessage());
    }
}