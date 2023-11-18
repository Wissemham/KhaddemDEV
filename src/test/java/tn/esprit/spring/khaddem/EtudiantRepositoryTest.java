package tn.esprit.spring.khaddem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.khaddem.dto.EtudiantDTO;
import tn.esprit.spring.khaddem.entities.Etudiant;
import tn.esprit.spring.khaddem.entities.Niveau;
import tn.esprit.spring.khaddem.entities.Option;
import tn.esprit.spring.khaddem.entities.Specialite;
import tn.esprit.spring.khaddem.repositories.EtudiantRepository;
import tn.esprit.spring.khaddem.services.EtudiantServiceImpl;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class EtudiantRepositoryTest {

    @InjectMocks
    private EtudiantServiceImpl etudiantService;

    @Mock
    private EtudiantRepository etudiantRepository;

    @BeforeEach
    public void setUp() {

    }

    @Test
    void testFindByEquipesNiveau() {
        // Mock the behavior of the etudiantRepository
        Mockito.when(etudiantRepository.findByEquipesNiveau(Niveau.JUNIOR))
                .thenReturn(Collections.emptyList());

        // Call the service method
        List<Etudiant> etudiants = etudiantService.findByEquipesNiveau(Niveau.JUNIOR);

        // Assert the results
        assertNotNull(etudiants);
        // Add more assertions as needed
    }

    @Test
    void testFindByNomEAndPrenomE() {
        // Mock the behavior of the etudiantRepository
        Etudiant ee=new Etudiant();
        Mockito.when(etudiantRepository.findByNomEAndPrenomE(Mockito.anyString(), Mockito.anyString()))
                .thenReturn(ee); // Replace with your test data if needed

        // Call the service method
        Etudiant etudiant = etudiantRepository.findByNomEAndPrenomE("YourNomE", "YourPrenomE");

        // Assert the results
        assertNotNull(etudiant);
        // Add more assertions as needed
    }
    @Test
    void testRetrieveAllEtudiants() {
        // Mock the behavior of the etudiantRepository
        Mockito.when(etudiantRepository.findAll()).thenReturn(Collections.emptyList());

        // Call the service method
        List<Etudiant> etudiants = etudiantService.retrieveAllEtudiants();

        // Assert the results
        assertNotNull(etudiants);
        // Add more assertions as needed
    }

    @Test
    void testFindByDepartementIdDepartement() {
        // Mock the behavior of the etudiantRepository

        Mockito.when(etudiantRepository.findByDepartementIdDepartement(0))
                .thenReturn(Collections.emptyList());

        // Call the service method
        List<Etudiant> etudiants = etudiantService.findByDepartementIdDepartement(0);

        // Assert the results
        assertNotNull(etudiants);
        // Add more assertions as needed
    }

    @Test
    void testRetrieveEtudiantsByContratSpecialite() {
        // Mock the behavior of the etudiantRepository
        Mockito.when(etudiantRepository.retrieveEtudiantsByContratSpecialite(Specialite.IA))
                .thenReturn(Collections.emptyList());

        // Call the service method
        List<Etudiant> etudiants = etudiantService.retrieveEtudiantsByContratSpecialite(Specialite.IA);

        // Assert the results
        assertNotNull(etudiants);
        // Add more assertions as needed
    }

    @Test
    void testRetrieveEtudiantsByContratSpecialiteSQL() {
        // Mock the behavior of the etudiantRepository
        Mockito.when(etudiantRepository.retrieveEtudiantsByContratSpecialiteSQL("IA"))
                .thenReturn(Collections.emptyList());

        // Call the service method
        List<Etudiant> etudiants = etudiantService.retrieveEtudiantsByContratSpecialiteSQL("IA");

        // Assert the results
        assertNotNull(etudiants);
        // Add more assertions as needed
    }

    @Test
    void testConvertEntityToDTO() {
        // Create an Etudiant entity
        EtudiantDTO eDTO=new EtudiantDTO();
        Etudiant etudiant = new Etudiant();
        etudiant.setIdEtudiant(1);
        etudiant.setPrenomE("John");
        etudiant.setNomE("Doe");
        etudiant.setOp(Option.GAMIX);
        // Set other fields if needed

        // Call the service method to convert the entity to DTO
        EtudiantDTO etudiantDTO = eDTO.convertEntityToDTO(etudiant);

        // Verify that the DTO contains the correct values
        assertEquals(1, etudiantDTO.getIdEtudiant());
        assertEquals("John", etudiantDTO.getPrenomE());
        assertEquals("Doe", etudiantDTO.getNomE());
        assertEquals(Option.GAMIX, etudiantDTO.getOp());
        // Verify other fields if needed
    }

    @Test
    void testConvertDTOToEntity() {
        // Create an EtudiantDTO
        EtudiantDTO etudiantDTO = new EtudiantDTO();
        etudiantDTO.setIdEtudiant(1);
        etudiantDTO.setPrenomE("John");
        etudiantDTO.setNomE("Doe");
        etudiantDTO.setOp(Option.GAMIX);
        // Set other fields if needed

        // Call the service method to convert the DTO to an entity
        Etudiant etudiant = etudiantDTO.convertDTOToEntity(etudiantDTO);

        // Verify that the entity contains the correct values
        assertEquals(1, etudiant.getIdEtudiant());
        assertEquals("John", etudiant.getPrenomE());
        assertEquals("Doe", etudiant.getNomE());
        assertEquals(Option.GAMIX, etudiant.getOp());
        // Verify other fields if needed
    }
}


