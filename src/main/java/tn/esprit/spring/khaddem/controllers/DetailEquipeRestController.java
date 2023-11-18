package tn.esprit.spring.khaddem.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.khaddem.dto.DetailEquipeDTO;
import tn.esprit.spring.khaddem.entities.DetailEquipe;
import tn.esprit.spring.khaddem.services.IDetailEquipeService;

import java.util.List;

@RestController
@RequestMapping("/detailequipe")
public class DetailEquipeRestController {
    @Autowired
    IDetailEquipeService detailEquipeservice;
    @PostMapping("/add-detailequipe")
    @ResponseBody
    public DetailEquipeDTO addDetailequipe(@RequestBody DetailEquipeDTO d) {
        DetailEquipe detailEquipe = d.convertToEntity(d);
        detailEquipeservice.addDetailEquipe(detailEquipe);
        return d;
    }
    @PutMapping("/update-detailequipe")
    @ResponseBody
    public DetailEquipeDTO updateDetailequipe(@RequestBody DetailEquipeDTO d) {
        DetailEquipe detailEquipe = d.convertToEntity(d);
        detailEquipeservice.updateDetailEquipe(detailEquipe);
        return d;
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