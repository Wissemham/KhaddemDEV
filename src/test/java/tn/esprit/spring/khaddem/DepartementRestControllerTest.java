package tn.esprit.spring.khaddem;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import tn.esprit.spring.khaddem.controllers.DepartementRestController;
import tn.esprit.spring.khaddem.dto.DepartementDTO;
import tn.esprit.spring.khaddem.entities.Departement;
import tn.esprit.spring.khaddem.services.IDepartementService;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(DepartementRestController.class)
class DepartementRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IDepartementService departementService;
    @BeforeEach
    void setUp() {
        //MockitoAnnotations.openMocks(this);
    }
    @Test
    void testGetDepartements() throws Exception {
        List<Departement> departements = Arrays.asList(new Departement(), new Departement());
        when(departementService.retrieveAllDepartements()).thenReturn(departements);

        mockMvc.perform(get("/departement/retrieve-all-departements"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$[0]").exists())
                .andExpect(jsonPath("$[1]").exists());

        verify(departementService, times(1)).retrieveAllDepartements();
    }

    @Test
    void testRetrieveDepartement() throws Exception {
        int departementId = 1;
        Departement departement = new Departement();
        departement.setIdDepartement(1);
        when(departementService.retrieveDepartement(departementId)).thenReturn(departement);

        mockMvc.perform(get("/departement/retrieve-departement/{departement-id}", departementId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$.idDepartement").value(departementId));

        verify(departementService, times(1)).retrieveDepartement(departementId);
    }

    @Test
    void testAddDepartement() throws Exception {
        DepartementDTO departementDTO = new DepartementDTO();
        departementDTO.setIdDepartement(1);
        Departement departement = DepartementDTO.convertToEntity(departementDTO);

        when(departementService.addDepartement(departement)).thenReturn(departement);

        mockMvc.perform(post("/departement/add-departement")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(departementDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idDepartement", is(notNullValue())));
    }

    @Test
    void testUpdateDepartement() throws Exception {
        int departementId = 1;
        DepartementDTO departementDTO = new DepartementDTO();
        departementDTO.setIdDepartement(departementId);
        Departement departement = DepartementDTO.convertToEntity(departementDTO);

        when(departementService.updateDepartement(departement)).thenReturn(departement);

        mockMvc.perform(put("/departement/update-departement")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(departementDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idDepartement", is(notNullValue())));
    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    void testRetrieveDepartementsByUniversite() throws Exception {
        int idUniversite = 1;

        // Create a list of Departement objects to return when the service method is called
        List<Departement> departements = Arrays.asList(new Departement(), new Departement());
        when(departementService.retrieveDepartementsByUniversite(idUniversite)).thenReturn(departements);

        mockMvc.perform(get("/departement/retrieveDepartementsByUniversite/{idUniversite}", idUniversite))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$[0]").exists())
                .andExpect(jsonPath("$[1]").exists());

        verify(departementService, times(1)).retrieveDepartementsByUniversite(idUniversite);
    }

}