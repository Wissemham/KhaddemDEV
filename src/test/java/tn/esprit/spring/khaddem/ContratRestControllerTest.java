package tn.esprit.spring.khaddem;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import tn.esprit.spring.khaddem.controllers.ContratRestController;
import tn.esprit.spring.khaddem.dto.ContratDTO;
import tn.esprit.spring.khaddem.entities.Contrat;
import tn.esprit.spring.khaddem.services.IContratService;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ContratRestController.class)
 class ContratRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IContratService contratService;

    @Test
     void testGetContrats() throws Exception {
        // Arrange
        List<Contrat> contrats = Arrays.asList(new Contrat(), new Contrat());
        when(contratService.retrieveAllContrats()).thenReturn(contrats);

        // Act & Assert
        mockMvc.perform(get("/contrat/retrieve-all-contrats"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }
    @Test
     void testRemoveContrat() throws Exception {
        // Arrange
        Integer idContrat = 1;

        // Mock the service method to simulate successful removal
        doNothing().when(contratService).removeContrat(idContrat);

        // Act & Assert
        mockMvc.perform(delete("/contrat/remove-contrat/{id}", idContrat))
                .andExpect(status().isNoContent()); // Expecting HTTP status 204 (No Content)
    }
    @Test
     void testRemoveContratException() throws Exception {
        // Arrange
        doThrow(new RuntimeException()).when(contratService).removeContrat(anyInt());

        // Act & Assert
        mockMvc.perform(delete("/contrat/remove-contrat/1"))
                .andExpect(status().isInternalServerError());
    }

    @Test
     void testAddAndAffectContratToEtudiant() throws Exception {
        // Arrange
        ContratDTO contratDTO = new ContratDTO(); // populate as needed
        Contrat newContrat = new Contrat(); // populate as needed

        // Mock the service method
        when(contratService.addAndAffectContratToEtudiant(any(Contrat.class), any(String.class), any(String.class)))
                .thenReturn(newContrat);

        // Act & Assert
        mockMvc.perform(post("/contrat/addAndAffectContratToEtudiant/{nomE}/{prenomE}", "John", "Doe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(contratDTO)))
                .andExpect(status().isOk());
    }
    @Test
     void testAddContrat() throws Exception {
        // Arrange
        ContratDTO contratDTO = new ContratDTO(); // Populate fields as needed
        Contrat expectedContrat = new Contrat(); // Populate fields as needed

        // Mock the service method instead of static method
        when(contratService.addContrat(any(Contrat.class))).thenReturn(expectedContrat);

        // Act & Assert
        mockMvc.perform(post("/contrat/add-contrat")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(contratDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idContrat").value(contratDTO.getIdContrat())); // Update this line to match the actual JSON structure
    }






    @Test
     void testUpdateContrat() throws Exception {
        // Arrange
        ContratDTO contratDTO = new ContratDTO(); // Populate fields as needed
        Contrat updatedContrat = new Contrat(); // Populate fields as needed

        // Mock the service method
        when(contratService.updateContrat(any(Contrat.class))).thenReturn(updatedContrat);

        // Act & Assert
        mockMvc.perform(put("/contrat/update-contrat")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(contratDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idContrat").value(contratDTO.getIdContrat())); // Update this line to match the actual JSON structure
    }
    @Test
     void testRetrieveContrat() throws Exception {
        // Arrange
        Integer contratId = 1;
        Contrat expectedContrat = new Contrat(); // Populate fields as needed

        // Mock the service method
        when(contratService.retrieveContrat(contratId)).thenReturn(expectedContrat);

        // Act & Assert
        mockMvc.perform(get("/contrat/retrieve-contrat/{contrat-id}", contratId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idContrat").value(expectedContrat.getIdContrat())); // Update this line to match the actual JSON structure
    }
    @Test
     void testGetnbContratsValides() throws Exception {
        // Arrange
        String startDateStr = "2023-10-25";
        String endDateStr = "2023-10-29";
        Integer expectedValue = 5; // Replace with the actual expected value

        // Mock the service method
        when(contratService.nbContratsValides(any(Date.class), any(Date.class)))
                .thenReturn(expectedValue);

        // Act & Assert
        mockMvc.perform(get("/contrat/getnbContratsValides/{startDate}/{endDate}", startDateStr, endDateStr))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(expectedValue));
    }
    @Test
     void testMajStatusContrat() throws Exception {
        // Arrange
        doNothing().when(contratService).retrieveAndUpdateStatusContrat();

        // Act
        mockMvc.perform(put("/contrat/majStatusContrat"))
                .andExpect(status().isOk());

        // Assert
        verify(contratService, times(1)).retrieveAndUpdateStatusContrat();
    }

   @Test
    void testCalculChiffreAffaireEntreDeuxDates() throws Exception {
      // Arrange
      String startDateStr = "2023-10-25";
      String endDateStr = "2023-10-29";
      float expectedValue = 1000.0f; // Replace with the actual expected value

      // Mock the service method
      when(contratService.getChiffreAffaireEntreDeuxDates(any(Date.class), any(Date.class)))
              .thenReturn(expectedValue);

      // Act & Assert
      mockMvc.perform(get("/contrat/calculChiffreAffaireEntreDeuxDate/{startDate}/{endDate}", startDateStr, endDateStr))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$").value(expectedValue));
   }

}
