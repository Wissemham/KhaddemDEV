package tn.esprit.spring.khaddem;




import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;


import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.khaddem.entities.*;

import tn.esprit.spring.khaddem.repositories.ContratRepository;
import tn.esprit.spring.khaddem.repositories.DepartementRepository;
import tn.esprit.spring.khaddem.repositories.EquipeRepository;
import tn.esprit.spring.khaddem.repositories.EtudiantRepository;
import tn.esprit.spring.khaddem.services.EtudiantServiceImpl;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EtudiantServiceImplTest {

    @InjectMocks
    private EtudiantServiceImpl etudiantService;

    @Mock
    private EtudiantRepository etudiantRepository;


    @Mock
    private DepartementRepository departementRepository;

    @Mock
    private ContratRepository contratRepository;

    @Mock
    private EquipeRepository equipeRepository;


    @BeforeEach
    public void setUp() {

    }

    @Test
    void testRetrieveAllEtudiants() {
        // Mock the behavior of the etudiantRepository

        when(etudiantRepository.findAll()).thenReturn(Collections.emptyList());

        // Call the service method
        List<Etudiant> etudiants = etudiantService.retrieveAllEtudiants();

        // Assert the results
        assertNotNull(etudiants);
        // Add more assertions as needed
    }

    @Test
    void testAddEtudiant() {
        // Mock the behavior of the etudiantRepository.save method
        when(etudiantRepository.save(any(Etudiant.class))).thenReturn(null);

        Etudiant etudiant = new Etudiant(/* initialize with test data */);

        // Call the service method
        Etudiant addedEtudiant = etudiantService.addEtudiant(etudiant);

        // Assert the results
        assertNotNull(addedEtudiant);
        // Add more assertions as needed
    }
    @Test
    void testUpdateEtudiant() {
        // Create a sample Etudiant entity
        Etudiant etudiant = new Etudiant();
        etudiant.setIdEtudiant(1);
        etudiant.setNomE("John");
        etudiant.setPrenomE("Doe");

        // Mock the behavior of the etudiantRepository's savve method
        Mockito.when(etudiantRepository.save(etudiant)).thenReturn(etudiant);

        // Call the service method to update the Etudiant
        Etudiant updatedEtudiant = etudiantService.updateEtudiant(etudiant);

        // Verify that the entity was saved in the repository
        Mockito.verify(etudiantRepository).save(etudiant);

        // Check if the returned entity is not null
        assertNotNull(updatedEtudiant);

        // Assert that the returned entity matches the original entity
        assertEquals(etudiant, updatedEtudiant);
    }

    @Test
    void testGetEtudiantsByDepartement() {
        // Create a mock departement and set its departementId
        Departement departement = new Departement();
        departement.setEtudiants(new ArrayList<>());
        departement.setIdDepartement(0); // Set the departementId to match the mock

        when(departementRepository.findById(0)).thenReturn(Optional.of(departement));

        // Call the service method
        List<Etudiant> etudiants = etudiantService.getEtudiantsByDepartement(departement.getIdDepartement());

        // Assert the results
        assertNotNull(etudiants); // Ensure the result is not null
        assertTrue(etudiants.isEmpty()); // Check if the returned list is empty
    }

    @Test
    void testRetrieveEtudiant() {
        // Define the ID of the Etudiant to retrieve
        Integer etudiantId = 1;

        // Create a sample Etudiant entity
        Etudiant etudiant = new Etudiant();
        etudiant.setIdEtudiant(1);
        etudiant.setNomE("John");
        etudiant.setPrenomE("Doe");

        // Mock the behavior of the etudiantRepository's findById method
        Mockito.when(etudiantRepository.findById(etudiantId)).thenReturn(Optional.of(etudiant));

        // Call the service method to retrieve the Etudiant
        Etudiant retrievedEtudiant = etudiantService.retrieveEtudiant(etudiantId);

        // Verify that the etudiantRepository's findById method was called with the correct ID
        Mockito.verify(etudiantRepository).findById(etudiantId);

        // Check if the retrieved entity is not null
        assertNotNull(retrievedEtudiant);

        // Assert that the retrieved entity matches the original entity
        assertEquals(etudiant, retrievedEtudiant);
    }
    @Test
    void testRemoveEtudiant() {
        // Define the ID of the Etudiant to remove
        Integer etudiantId = 1;

        // Mock the behavior of the etudiantRepository's deleteById method
        Mockito.doNothing().when(etudiantRepository).deleteById(etudiantId);

        // Call the service method to remove the Etudiant
        etudiantService.removeEtudiant(etudiantId);

        // Verify that the etudiantRepository's deleteById method was called with the correct ID
        Mockito.verify(etudiantRepository).deleteById(etudiantId);
    }

    @Test
    void testAssignEtudiantToDepartement() {
        // Define the IDs of an Etudiant and a Departement
        Integer etudiantId = 1;
        Integer departementId = 2;

        // Create mock Etudiant and Departement instances
        Etudiant etudiant = new Etudiant();
        etudiant.setIdEtudiant(etudiantId);

        Departement departement = new Departement();
        departement.setIdDepartement(departementId);

        // Mock the behavior of etudiantRepository and departementRepository
        Mockito.when(etudiantRepository.findById(etudiantId)).thenReturn(Optional.of(etudiant));
        Mockito.when(departementRepository.findById(departementId)).thenReturn(Optional.of(departement));
        Mockito.when(etudiantRepository.save(etudiant)).thenReturn(etudiant);

        // Call the service method to assign the Etudiant to the Departement
        etudiantService.assignEtudiantToDepartement(etudiantId, departementId);

        // Verify that etudiantRepository's findById and save methods were called with the correct IDs
        Mockito.verify(etudiantRepository).findById(etudiantId);
        Mockito.verify(departementRepository).findById(departementId);
        Mockito.verify(etudiantRepository).save(etudiant);

        // Verify that the Etudiant is correctly assigned to the Departement
        assertEquals(departement, etudiant.getDepartement());
    }
    @Test
    void testFindByDepartementIdDepartement() {
        // Define the ID of a Departement
        Integer departementId = 1;
        Etudiant etudiant1 = new Etudiant();
        etudiant1.setIdEtudiant(1);
        etudiant1.setNomE("John");
        etudiant1.setPrenomE("Doe");
        Etudiant etudiant2 = new Etudiant();
        etudiant2.setIdEtudiant(2);
        etudiant2.setNomE("John2");
        etudiant2.setPrenomE("Doe2");

        // Create a mock list of Etudiant instances
        List<Etudiant> etudiants = Arrays.asList(
                etudiant1,
                etudiant2
        );

        // Mock the behavior of etudiantRepository
        Mockito.when(etudiantRepository.findByDepartementIdDepartement(departementId)).thenReturn(etudiants);

        // Call the service method to find Etudiants by Departement ID
        List<Etudiant> result = etudiantService.findByDepartementIdDepartement(departementId);

        // Verify that etudiantRepository's findByDepartementIdDepartement method was called with the correct ID
        Mockito.verify(etudiantRepository).findByDepartementIdDepartement(departementId);

        // Verify that the result matches the mock list of Etudiants
        assertEquals(etudiants, result);
    }
    @Test
    void testFindByEquipesNiveau() {
        //etudiant mock data
        Etudiant etudiant1 = new Etudiant();
        etudiant1.setIdEtudiant(1);
        etudiant1.setNomE("John");
        etudiant1.setPrenomE("Doe");
        Etudiant etudiant2 = new Etudiant();
        etudiant2.setIdEtudiant(2);
        etudiant2.setNomE("John2");
        etudiant2.setPrenomE("Doe2");

        // Create a mock list of Etudiant instances
        List<Etudiant> etudiants = Arrays.asList(
                etudiant1,
                etudiant2
        );

        // Mock the behavior of etudiantRepository
        Mockito.when(etudiantRepository.findByEquipesNiveau(Niveau.JUNIOR)).thenReturn(etudiants);

        // Call the service method to find Etudiants by Niveau
        List<Etudiant> result = etudiantService.findByEquipesNiveau(Niveau.JUNIOR);

        // Verify that etudiantRepository's findByEquipesNiveau method was called with the correct Niveau
        Mockito.verify(etudiantRepository).findByEquipesNiveau(Niveau.JUNIOR);

        // Verify that the result matches the mock list of Etudiants
        assertEquals(etudiants, result);
    }
    @Test
    void testRetrieveEtudiantsByContratSpecialite() {
        Etudiant etudiant1 = new Etudiant();
        etudiant1.setIdEtudiant(1);
        etudiant1.setNomE("John");
        etudiant1.setPrenomE("Doe");
        Etudiant etudiant2 = new Etudiant();
        etudiant2.setIdEtudiant(2);
        etudiant2.setNomE("John2");
        etudiant2.setPrenomE("Doe2");

        // Create a mock list of Etudiant instances
        List<Etudiant> etudiants = Arrays.asList(
                etudiant1,
                etudiant2
        );

        // Mock the behavior of etudiantRepository
        Mockito.when(etudiantRepository.retrieveEtudiantsByContratSpecialite(Specialite.IA)).thenReturn(etudiants);

        // Call the service method to retrieve Etudiants by Specialite
        List<Etudiant> result = etudiantService.retrieveEtudiantsByContratSpecialite(Specialite.IA);

        // Verify that etudiantRepository's retrieveEtudiantsByContratSpecialite method was called with the correct Specialite
        Mockito.verify(etudiantRepository).retrieveEtudiantsByContratSpecialite(Specialite.IA);

        // Verify that the result matches the mock list of Etudiants
        assertEquals(etudiants, result);
    }
    @Test
    void testRetrieveEtudiantsByContratSpecialiteSQL() {
        // Define a specialite string
        String specialite = "IA";
        Etudiant etudiant1 = new Etudiant();
        etudiant1.setIdEtudiant(1);
        etudiant1.setNomE("John");
        etudiant1.setPrenomE("Doe");
        Etudiant etudiant2 = new Etudiant();
        etudiant2.setIdEtudiant(2);
        etudiant2.setNomE("John2");
        etudiant2.setPrenomE("Doe2");

        // Create a mock list of Etudiant instances
        List<Etudiant> etudiants = Arrays.asList(
                etudiant1,
                etudiant2
        );

        // Mock the behavior of etudiantRepository
        Mockito.when(etudiantRepository.retrieveEtudiantsByContratSpecialiteSQL(specialite)).thenReturn(etudiants);

        // Call the service method to retrieve Etudiants by Specialite
        List<Etudiant> result = etudiantService.retrieveEtudiantsByContratSpecialiteSQL(specialite);

        // Verify that etudiantRepository's retrieveEtudiantsByContratSpecialiteSQL method was called with the correct specialite
        Mockito.verify(etudiantRepository).retrieveEtudiantsByContratSpecialiteSQL(specialite);

        // Verify that the result matches the mock list of Etudiants
        assertEquals(etudiants, result);
    }
    @Test
    void testAddAndAssignEtudiantToEquipeAndContract() {
        // Create mock objects for Contrat, Equipe, and Etudiant
        Contrat contrat = new Contrat();
        Equipe equipe = new Equipe();
        Etudiant etudiant = new Etudiant();
        etudiant.setIdEtudiant(1);
        etudiant.setNomE("John");
        etudiant.setPrenomE("Doe");

        // Mock the behavior of repositories
        Mockito.when(contratRepository.findById(1)).thenReturn(Optional.of(contrat));
        Mockito.when(equipeRepository.findById(1)).thenReturn(Optional.of(equipe));
        Mockito.when(etudiantRepository.save(etudiant)).thenReturn(etudiant);

        // Call the service method
        etudiantService.addAndAssignEtudiantToEquipeAndContract(etudiant, 1, 1);

        // Verify interactions
        Mockito.verify(contratRepository).findById(1);
        Mockito.verify(equipeRepository).findById(1);
        Mockito.verify(etudiantRepository).save(etudiant);

        // Verify the modifications to Etudiant
        assertNotNull(contrat);
        List<Equipe> equipes = etudiant.getEquipes();
        assertEquals(1, equipes.size());
        assertTrue(equipes.contains(equipe));
    }
}

