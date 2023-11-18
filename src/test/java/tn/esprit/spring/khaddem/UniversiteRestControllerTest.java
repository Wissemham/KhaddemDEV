package tn.esprit.spring.khaddem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import tn.esprit.spring.khaddem.controllers.UniversiteRestController;
import tn.esprit.spring.khaddem.dto.UniversiteDTO;
import tn.esprit.spring.khaddem.entities.Universite;
import tn.esprit.spring.khaddem.services.IUniversiteService;

import java.util.Arrays;
import java.util.List;

@WebMvcTest(UniversiteRestController.class)
class UniversiteRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IUniversiteService universiteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetUniversites() throws Exception {
        // Mock data
        Universite universite1 = new Universite(1, "University 1");
        Universite universite2 = new Universite(2, "University 2");
        List<Universite> universities = Arrays.asList(universite1, universite2);

        // Mock the service to return the data
        Mockito.when(universiteService.retrieveAllUniversites()).thenReturn(universities);

        // Perform the GET request
        mockMvc.perform(MockMvcRequestBuilders.get("/universite/retrieve-all-universites")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("[{}, {}]")); // Adjust the expected JSON content
    }

    @Test
    void testRetrieveUniversite() throws Exception {
        Universite universite = new Universite(1, "University 1");
        Mockito.when(universiteService.retrieveUniversite(1)).thenReturn(universite);

        mockMvc.perform(MockMvcRequestBuilders.get("/universite/retrieve-universite/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{}"));
    }
    @Test
    void testAddUniversite() throws Exception {
        // Mock data
        UniversiteDTO universiteDTO = new UniversiteDTO();
        universiteDTO.setIdUniversite(1);
        universiteDTO.setNomUniv("University 1");

        Universite universite = new Universite(1, "University 1");

        Mockito.when(universiteService.addUniversite(Mockito.any(Universite.class))).thenReturn(universite);

        mockMvc.perform(MockMvcRequestBuilders.post("/universite/add-universite")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"idUniversite\": 1, \"nomUniv\": \"University 1\"}")) // Adjust the request body JSON
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{}")); // Adjust the expected JSON content
    }

    @Test
    void testUpdateUniversite() throws Exception {
        UniversiteDTO universiteDTO = new UniversiteDTO();
        universiteDTO.setIdUniversite(1);
        universiteDTO.setNomUniv("University 1");

        Universite universite = new Universite(1, "Updated University 1");

        Mockito.when(universiteService.updateUniversite(Mockito.any(Universite.class))).thenReturn(universite);

        mockMvc.perform(MockMvcRequestBuilders.put("/universite/update-universite")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"idUniversite\": 1, \"nomUniv\": \"Updated University 1\"}")) // Adjust the request body JSON
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{}")); // Adjust the expected JSON content
    }

    @Test
    void testAssignUniversiteToDepartement() throws Exception {
        Integer universiteId = 1;
        Integer departementId = 2;

        Mockito.doNothing().when(universiteService).assignUniversiteToDepartement(universiteId, departementId);


        mockMvc.perform(MockMvcRequestBuilders.put("/universite/assignUniversiteToDepartement/1/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}