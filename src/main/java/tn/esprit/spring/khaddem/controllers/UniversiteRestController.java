package tn.esprit.spring.khaddem.controllers;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.khaddem.dto.UniversiteDTO;
import tn.esprit.spring.khaddem.entities.Universite;
import tn.esprit.spring.khaddem.services.IUniversiteService;

import java.util.List;

@Tag(name = "Gestion des universités")
@RestController
@AllArgsConstructor
@RequestMapping("/universite")

public class UniversiteRestController {

    IUniversiteService universiteService;

    @GetMapping("/retrieve-all-universites")
    @Operation(description = "récupérer la liste des universités")
    @ResponseBody
    public List<Universite> getUniversites() {
        return universiteService.retrieveAllUniversites();
    }

    @GetMapping("/retrieve-universite/{universite-id}")
    @Operation(description = "récupérer une université par son id")
    @ResponseBody
    public Universite retrieveUniversite(@PathVariable("universite-id") Integer universiteId) {
        return universiteService.retrieveUniversite(universiteId);
    }

    @PostMapping("/add-universite")
    @Operation(description = "ajouter une université")
    @ResponseBody
    public UniversiteDTO addUniversite(@RequestBody UniversiteDTO universiteDTO) {
        Universite universite = UniversiteDTO.convertToEntity(universiteDTO);
        Universite savedUniversite = universiteService.addUniversite(universite);
        return UniversiteDTO.convertToDTO(savedUniversite);
    }
    @PutMapping("/update-universite")
    @Operation(description = "modifier une université")
    @ResponseBody
    public UniversiteDTO updateUniversite(@RequestBody UniversiteDTO universiteDTO) {
        Universite universite = UniversiteDTO.convertToEntity(universiteDTO);
        Universite updatedUniversite = universiteService.updateUniversite(universite);
        return UniversiteDTO.convertToDTO(updatedUniversite);
    }
    @PutMapping("/assignUniversiteToDepartement/{universiteId}/{departementId}")
    @Operation(description = "assigner une université à un département")
    @ResponseBody
    public void assignUniversiteToDepartement(@PathVariable("universiteId") Integer universiteId,@PathVariable("departementId") Integer departementId) {
        universiteService.assignUniversiteToDepartement(universiteId,departementId);
    }
}