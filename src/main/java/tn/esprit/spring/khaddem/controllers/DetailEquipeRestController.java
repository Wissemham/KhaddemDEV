package tn.esprit.spring.khaddem.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.khaddem.dto.DetailEquipeDTO;
import tn.esprit.spring.khaddem.entities.DetailEquipe;
import tn.esprit.spring.khaddem.services.IDetailEquipeService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/detailequipe")
public class DetailEquipeRestController {

    IDetailEquipeService detailEquipeservice;
    @PostMapping("/add-detailequipe")
    @ResponseBody
    public DetailEquipeDTO addDetailequipe(@RequestBody DetailEquipeDTO detailEquipeDTO) {
        DetailEquipe detailEquipe = DetailEquipeDTO.convertToEntity(detailEquipeDTO);
        detailEquipeservice.addDetailEquipe(detailEquipe);
        return detailEquipeDTO;
    }
    @PutMapping("/update-detailequipe")
    @ResponseBody
    public DetailEquipeDTO updateDetailequipe(@RequestBody DetailEquipeDTO detailEquipeDTO) {
        DetailEquipe detailEquipe = DetailEquipeDTO.convertToEntity(detailEquipeDTO);
        detailEquipeservice.updateDetailEquipe(detailEquipe);
        return detailEquipeDTO;
    }
    @GetMapping("/retrieve-detailequipe")
    @ResponseBody
    public List<DetailEquipe> retrivealldetailequope() {

        return detailEquipeservice.retrieveAllDetailEquipe();
    }
    @DeleteMapping("/supprimerdetailequipe/{id}")
    public void supprimerdetailequipe(@PathVariable("id") Integer id){
        detailEquipeservice.supprimerDetailEquipe(id);
    }
}