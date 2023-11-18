package tn.esprit.spring.khaddem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.khaddem.entities.Contrat;
import tn.esprit.spring.khaddem.entities.Etudiant;
import tn.esprit.spring.khaddem.entities.Specialite;
import tn.esprit.spring.khaddem.repositories.ContratRepository;
import tn.esprit.spring.khaddem.repositories.EtudiantRepository;
import tn.esprit.spring.khaddem.services.ContratServiceImpl;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContratServiceImplTest {

   @Mock
   private ContratRepository contratRepository;

   @Mock
   private EtudiantRepository etudiantRepository;

   @InjectMocks
   private ContratServiceImpl contratService;

   Contrat contrat;

   @BeforeEach
   void setUp() {
      contrat = new Contrat();
      contrat.setIdContrat(1);
      contrat.setDateDebutContrat(new Date());
      contrat.setDateFinContrat(new Date());
      contrat.setSpecialite(Specialite.RESEAU);
      contrat.setMontantContrat(50000);
      contrat.setArchived(false);
   }

   @Test
   void testAddContrat() {
      when(contratRepository.save(any(Contrat.class))).thenReturn(contrat);
      Contrat returnedContrat = contratService.addContrat(contrat);
      assertEquals(contrat, returnedContrat);
      verify(contratRepository, times(1)).save(contrat);
   }

   @Test
   void testRetrieveAllContrats() {
      Contrat contrat1 = new Contrat();
      Contrat contrat2 = new Contrat();
      when(contratRepository.findAll()).thenReturn(Arrays.asList(contrat1, contrat2));
      List<Contrat> result = contratService.retrieveAllContrats();
      assertEquals(2, result.size());
   }

   @Test
   void testUpdateContrat() {
      Contrat contratToUpdate = new Contrat();
      Contrat result = contratService.updateContrat(contratToUpdate);
      verify(contratRepository, times(1)).save(contratToUpdate);
      assertEquals(contratToUpdate, result);
   }

   @Test
   void testRetrieveContrat() {
      Integer idContrat = 1;
      Contrat expectedContrat = new Contrat();
      when(contratRepository.findById(idContrat)).thenReturn(Optional.of(expectedContrat));
      Contrat actualContrat = contratService.retrieveContrat(idContrat);
      assertEquals(expectedContrat, actualContrat);
   }

   @Test
   void testRemoveContrat() {
      Integer idContrat = 1;
      contratService.removeContrat(idContrat);
      verify(contratRepository, times(1)).deleteById(idContrat);
   }

   @Test
   void testAddAndAffectContratToEtudiant() {
      Contrat contrat = new Contrat();
      List<Contrat> contrats = new ArrayList<>();
      String nomE = "John";
      String prenomE = "Doe";
      Etudiant etudiant = mock(Etudiant.class);
      when(etudiant.getContrats()).thenReturn(contrats);
      when(etudiantRepository.findByNomEAndPrenomE(nomE, prenomE)).thenReturn(etudiant);
      when(contratRepository.save(contrat)).thenReturn(contrat);
      Contrat actualContrat = contratService.addAndAffectContratToEtudiant(contrat, nomE, prenomE);
      assertEquals(contrat, actualContrat);
      assertEquals(etudiant, actualContrat.getEtudiant());
      verify(contratRepository).save(contrat);
      verify(etudiantRepository).findByNomEAndPrenomE(nomE, prenomE);
   }

   @Test
   void testNbContratsValides() {
      Date startDate = new Date();
      Date endDate = new Date();
      Integer expectedCount = 5;
      when(contratRepository.getnbContratsValides(startDate, endDate)).thenReturn(expectedCount);
      Integer actualCount = contratService.nbContratsValides(startDate, endDate);
      assertEquals(expectedCount, actualCount);
      verify(contratRepository).getnbContratsValides(startDate, endDate);
   }

   @Test
   void testRetrieveAndUpdateStatusContrat() {
      // Arrange
      Contrat contrat1 = new Contrat();
      contrat1.setArchived(null);
      contrat1.setDateFinContrat(new Date()); // Set a date that will make differenceInDays == 15

      Contrat contrat2 = new Contrat();
      contrat2.setArchived(true);
      contrat2.setDateFinContrat(new Date()); // Set a date that will make differenceInDays == 0

      List<Contrat> contrats = Arrays.asList(contrat1, contrat2);

      when(contratRepository.findAll()).thenReturn(contrats);

      // Act
      contratService.retrieveAndUpdateStatusContrat();

      // Assert
      verify(contratRepository, times(1)).findAll();
      verify(contratRepository, times(1)).save(contrat1);
      verify(contratRepository, times(1)).save(contrat2);
   }
   @Test
   void testGetChiffreAffaireEntreDeuxDates() {
      // Arrange
      Date startDate = new Date();
      Calendar c = Calendar.getInstance();
      c.setTime(new Date());
      c.add(Calendar.DATE, 30);  // Add 30 days to current date
      Date endDate = c.getTime();

      Contrat contrat1 = new Contrat();
      contrat1.setSpecialite(Specialite.IA);
      contrat1.setMontantContrat(1000);

      Contrat contrat2 = new Contrat();
      contrat2.setSpecialite(Specialite.CLOUD);
      contrat2.setMontantContrat(2000);

      Contrat contrat3 = new Contrat();
      contrat3.setSpecialite(Specialite.RESEAU);
      contrat3.setMontantContrat(3000);

      Contrat contrat4 = new Contrat();
      contrat4.setSpecialite(Specialite.SECURITE);
      contrat4.setMontantContrat(4000);

      List<Contrat> contrats = Arrays.asList(contrat1, contrat2, contrat3, contrat4);
      when(contratRepository.findAll()).thenReturn(contrats);

      // Act
      float actualChiffreAffaire = contratService.getChiffreAffaireEntreDeuxDates(startDate, endDate);

      // Assert
      float expectedChiffreAffaire = 10000;  // This should be calculated based on your logic
      assertEquals(expectedChiffreAffaire, actualChiffreAffaire, 1.0);

      verify(contratRepository, times(1)).findAll();
   }
}
