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
import tn.esprit.spring.khaddem.controllers.EquipeRestController;
import tn.esprit.spring.khaddem.entities.Equipe;
import tn.esprit.spring.khaddem.services.IEquipeService;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(EquipeRestController.class)
 class EquipeRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IEquipeService equipeService;
    @Autowired
    EquipeRestController equipeRestController;

    @Test
     void testGetEquipes() throws Exception {
        List<Equipe> equipes = Arrays.asList(new Equipe(), new Equipe());
        when(equipeService.retrieveAllEquipes()).thenReturn(equipes);

        mockMvc.perform(get("/equipe/retrieve-all-equipes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$[0]").exists())
                .andExpect(jsonPath("$[1]").exists());

        verify(equipeService, times(1)).retrieveAllEquipes();
    }

    @Test
     void testRetrieveEquipe() throws Exception {
        int equipeId = 1;
        Equipe equipe = new Equipe();
        equipe.setIdEquipe(1);
        when(equipeService.addEquipe(equipe)).thenReturn(equipe);
        when(equipeService.retrieveEquipe(equipeId)).thenReturn(equipe);

        mockMvc.perform(get("/equipe/retrieve-equipe/{equipe-id}", equipeId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$.idEquipe").value(equipeId));

        verify(equipeService, times(1)).retrieveEquipe(equipeId);

    }

    @Test
    void testAddEquipe() throws Exception {
        Equipe equipe = new Equipe();
        equipe.setIdEquipe(1);

        when(equipeService.addEquipe(equipe)).thenReturn(equipe);

        // Perform the POST request with the JSON payload
        mockMvc.perform(post("/equipe/add-equipe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(equipe)))
                .andExpect(status().isOk()) // You might want to use the appropriate status code here
                .andExpect(jsonPath("$.idEquipe", is(notNullValue()))); // Check if the 'id' field is not null
    }
    @Test
    void testUpdateEquipe() throws Exception {
        Equipe equipe = new Equipe();
        equipe.setIdEquipe(1);

        when(equipeService.addEquipe(equipe)).thenReturn(equipe);
        equipe.setNomEquipe("Test");
        // Perform the POST request with the JSON payload
        mockMvc.perform(put("/equipe/update-equipe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(equipe)))
                .andExpect(status().isOk()) // You might want to use the appropriate status code here
                .andExpect(jsonPath("$.nomEquipe").value("Test")); // Check if the 'id' field is not null
    }
    @Test
    void testFaireEvoluerEquipes() {
        equipeRestController.faireEvoluerEquipes();
        verify(equipeService, times(1)).evoluerEquipes();
    }


    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
