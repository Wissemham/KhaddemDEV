package tn.esprit.spring.khaddem;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.spring.khaddem.dto.UniversiteDTO;
import tn.esprit.spring.khaddem.entities.Universite;
import tn.esprit.spring.khaddem.repositories.UniversiteRepository;

@SpringBootTest
class UniversiteRepositoryTest {

   @Autowired
   private UniversiteRepository universiteRepository;

   @Test
   void testSaveAndRetrieveUniversite() {
      Universite universite = new Universite();
      universite.setNomUniv("Your University Name");
      Universite savedUniversite = universiteRepository.save(universite);
      Universite retrievedUniversite = universiteRepository.findById(savedUniversite.getIdUniversite()).orElse(null);
      Assertions.assertNotNull(retrievedUniversite);
      Assertions.assertEquals(universite.getNomUniv(), retrievedUniversite.getNomUniv());
   }

   @Test
   void testUpdateUniversite() {
      Universite universite = new Universite();
      universite.setNomUniv("Your University Name");
      Universite savedUniversite = universiteRepository.save(universite);
      savedUniversite.setNomUniv("Updated University Name");
      Universite updatedUniversite = universiteRepository.save(savedUniversite);
      Universite retrievedUniversite = universiteRepository.findById(updatedUniversite.getIdUniversite()).orElse(null);
      Assertions.assertNotNull(retrievedUniversite);
      Assertions.assertEquals("Updated University Name", retrievedUniversite.getNomUniv());
   }

   @Test
   void testDeleteUniversite() {
      Universite universite = new Universite();
      universite.setNomUniv("Your University Name");
      Universite savedUniversite = universiteRepository.save(universite);
      universiteRepository.delete(savedUniversite);
      Universite retrievedUniversite = universiteRepository.findById(savedUniversite.getIdUniversite()).orElse(null);
      Assertions.assertNull(retrievedUniversite);
   }
   @Test
    void testConvertToDTO() {

      Universite universite = new Universite();
      universite.setIdUniversite(1);
      universite.setNomUniv("Sample University");
      UniversiteDTO universiteDTO = new UniversiteDTO();
      universiteDTO = universiteDTO.convertToDTO(universite);

      Assertions.assertEquals(universite.getIdUniversite(), universiteDTO.getIdUniversite());
      Assertions.assertEquals(universite.getNomUniv(), universiteDTO.getNomUniv());
   }

   @Test
   void testConvertToEntity() {

      UniversiteDTO universiteDTO = new UniversiteDTO();
      universiteDTO.setIdUniversite(1);
      universiteDTO.setNomUniv("Sample University");

      Universite universite = universiteDTO.convertToEntity(universiteDTO);

      Assertions.assertEquals(universiteDTO.getIdUniversite(), universite.getIdUniversite());
      Assertions.assertEquals(universiteDTO.getNomUniv(), universite.getNomUniv());
   }
}
