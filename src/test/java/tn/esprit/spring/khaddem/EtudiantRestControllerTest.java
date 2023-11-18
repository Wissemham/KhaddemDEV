package tn.esprit.spring.khaddem;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import tn.esprit.spring.khaddem.dto.EtudiantDTO;
import tn.esprit.spring.khaddem.entities.Etudiant;
import tn.esprit.spring.khaddem.entities.Niveau;
import tn.esprit.spring.khaddem.entities.Option;
import tn.esprit.spring.khaddem.entities.Specialite;
import tn.esprit.spring.khaddem.repositories.EtudiantRepository;


@SpringBootTest
@AutoConfigureMockMvc
class EtudiantRestControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private EtudiantRepository etudiantRepository;

    @Test
    void testGetEtudiants() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/etudiant/retrieve-all-etudiants"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testRetrieveEtudiant() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/etudiant/retrieve-etudiant/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testAddEtudiant() throws Exception {
        // Create a JSON representation of your EtudiantDTO and post it to the endpoint
        EtudiantDTO etudiantDTO = new EtudiantDTO();
        etudiantDTO.setIdEtudiant(1);  // set appropriate values
        etudiantDTO.setPrenomE("John");
        etudiantDTO.setNomE("Doe");
        etudiantDTO.setOp(Option.GAMIX);

        String etudiantJson = new ObjectMapper().writeValueAsString(etudiantDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/etudiant/add-etudiant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(etudiantJson))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testGetEtudiantsByDepartement() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/etudiant/getEtudiantsByDepartement/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testUpdateEtudiant() throws Exception {
        // Create a JSON representation of your EtudiantDTO and send it to the endpoint
        EtudiantDTO etudiantDTO = new EtudiantDTO();
        etudiantDTO.setIdEtudiant(1);  // set appropriate values
        etudiantDTO.setPrenomE("UpdatedFirstName");
        etudiantDTO.setNomE("UpdatedLastName");
        etudiantDTO.setOp(Option.GAMIX);

        String etudiantJson = new ObjectMapper().writeValueAsString(etudiantDTO);

        mockMvc.perform(MockMvcRequestBuilders.put("/etudiant/update-etudiant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(etudiantJson))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testRemoveEtudiant() throws Exception {
        // Create a new Etudiant entity with ID 1
        Etudiant etudiant = new Etudiant();
        etudiant.setIdEtudiant(1);
        etudiant.setNomE("John");
        etudiant.setPrenomE("Doe");

        // Insert the entity into the test database
        etudiantRepository.save(etudiant);
        Integer idEtudiant = 1; // Use the ID of an existing etudiant

        mockMvc.perform(MockMvcRequestBuilders.delete("/etudiant/removeEtudiant/{idEtudiant}", idEtudiant))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testAssignEtudiantToDepartement() throws Exception {
        Integer etudiantId = 1; // Replace with the ID of the etudiant
        Integer departementId = 1; // Replace with the ID of the departement
        mockMvc.perform(MockMvcRequestBuilders.put("/etudiant/assignEtudiantToDepartement/{etudiantId}/{departementId}", etudiantId, departementId))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testFindByDepartement() throws Exception {
        Integer departementId = 1; // Replace with the ID of the departement
        mockMvc.perform(MockMvcRequestBuilders.get("/etudiant/findByDepartement/{departement-id}", departementId))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testFindByEquipesNiveau() throws Exception {
        Niveau niveau = Niveau.JUNIOR; // Replace with the desired niveau
        mockMvc.perform(MockMvcRequestBuilders.get("/etudiant/findByEquipesNiveau/{niveau}", niveau))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    void testRetrieveEtudiantsByContratSpecialite() throws Exception {
        Specialite specialite = Specialite.SECURITE; // Replace with the desired Specialite
        mockMvc.perform(MockMvcRequestBuilders.get("/etudiant/retrieveEtudiantsByContratSpecialite/{specialite}", specialite))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testRetrieveEtudiantsByContratSpecialiteSQL() throws Exception {
        String specialite = "SECURITE"; // Replace with the desired specialite
        mockMvc.perform(MockMvcRequestBuilders.get("/etudiant/retrieveEtudiantsByContratSpecialiteSQL/{specialite}", specialite))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testAddAndAssignEtudiantToEquipeAndContract() throws Exception {
        Integer equipeId = 1; // Replace with the ID of the equipe
        Integer contratId = 1; // Replace with the ID of the contrat
        EtudiantDTO etudiantDTO = new EtudiantDTO(); // Create an EtudiantDTO object for testing
        mockMvc.perform(MockMvcRequestBuilders.post("/etudiant/addAndAssignEtudiantToEquipeAndContract/{equipeId}/{contratId}", equipeId, contratId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(etudiantDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}

