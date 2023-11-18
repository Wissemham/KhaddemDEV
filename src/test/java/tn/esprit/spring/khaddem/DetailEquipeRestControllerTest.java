package tn.esprit.spring.khaddem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import tn.esprit.spring.khaddem.controllers.DetailEquipeRestController;
import tn.esprit.spring.khaddem.dto.DetailEquipeDTO;
import tn.esprit.spring.khaddem.entities.DetailEquipe;
import tn.esprit.spring.khaddem.services.IDetailEquipeService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
@ExtendWith(SpringExtension.class)
@WebMvcTest(DetailEquipeRestController.class)
 class DetailEquipeRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IDetailEquipeService detailEquipeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddDetailequipe() throws Exception {
        // Mock data
        DetailEquipeDTO detailEquipeDTO = new DetailEquipeDTO();
        detailEquipeDTO.setIdDetailEquipe(1);
        detailEquipeDTO.setSalle(101);
        detailEquipeDTO.setThematique("Test Thematique");

        DetailEquipe detailEquipe = new DetailEquipe();
        detailEquipe.setIdDetailEquipe(1);
        detailEquipe.setSalle(101);
        detailEquipe.setThematique("Test Thematique");

        //when(detailEquipeService.addDetailEquipe(Mockito.any(DetailEquipe.class))).thenReturn(detailEquipe);

        mockMvc.perform(MockMvcRequestBuilders.post("/detailequipe/add-detailequipe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"idDetailEquipe\": 1, \"salle\": 101, \"thematique\": \"Test Thematique\"}")) // Adjust the request body JSON
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{}")); // Adjust the expected JSON content
    }

    @Test
    void testUpdateDetailequipe() throws Exception {
        // Mock data
        DetailEquipeDTO detailEquipeDTO = new DetailEquipeDTO();
        detailEquipeDTO.setIdDetailEquipe(1);
        detailEquipeDTO.setSalle(101);
        detailEquipeDTO.setThematique("Updated Thematique");

        DetailEquipe detailEquipe = new DetailEquipe();
        detailEquipe.setIdDetailEquipe(1);
        detailEquipe.setSalle(101);
        detailEquipe.setThematique("Updated Thematique");

        //when(detailEquipeService.updateDetailEquipe(Mockito.any(DetailEquipe.class))).thenReturn(detailEquipe);

        mockMvc.perform(MockMvcRequestBuilders.put("/detailequipe/update-detailequipe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"idDetailEquipe\": 1, \"salle\": 101, \"thematique\": \"Updated Thematique\"}")) // Adjust the request body JSON
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{}")); // Adjust the expected JSON content
    }

    @Test
    void testRetrieveAlldetailequope() throws Exception {
        // Mock data
        DetailEquipe detailEquipe1 = new DetailEquipe();
        DetailEquipe detailEquipe2 = new DetailEquipe();
        List<DetailEquipe> detailEquipes = Arrays.asList(detailEquipe1, detailEquipe2);

        // Mock the service to return the data
        when(detailEquipeService.retrieveAllDetailEquipe()).thenReturn(detailEquipes);

        // Perform the GET request
        mockMvc.perform(MockMvcRequestBuilders.get("/detailequipe/retrieve-detailequipe")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("[{}, {}]")); // Adjust the expected JSON content
    }

    @Test
    void testSupprimerdetailequipe() throws Exception {
        // Arrange
        Integer id = 1;

        // Perform the DELETE request
        mockMvc.perform(MockMvcRequestBuilders.delete("/detailequipe/supprimerdetailequipe/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Assert
        verify(detailEquipeService, times(1)).supprimerDetailEquipe(id);
    }
}
