package tn.esprit.spring.khaddem.controllers;

import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.khaddem.dto.ContratDTO;
import tn.esprit.spring.khaddem.entities.Contrat;
import tn.esprit.spring.khaddem.services.IContratService;

import java.util.Date;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/contrat")
public class ContratRestController {

    IContratService contratService;


    // http://localhost:8089/Kaddem/contrat/retrieve-all-contrats
    @GetMapping("/retrieve-all-contrats")
    @ResponseBody
    public List<Contrat> getContrats() {
        return contratService.retrieveAllContrats();
    }

    // http://localhost:8089/Kaddem/contrat/retrieve-contrat/8
    @GetMapping("/retrieve-contrat/{contrat-id}")
    @ResponseBody
    public Contrat retrieveContrat(@PathVariable("contrat-id") Integer contratId) {
        return contratService.retrieveContrat(contratId);
    }

    // http://localhost:8089/Kaddem/contrat/add-contrat
// Adding a new contrat
    @PostMapping("/add-contrat")
    @ResponseBody
    public ContratDTO addContrat(@RequestBody ContratDTO contratDTO) {
       Contrat contrat= ContratDTO.convertToEntity(contratDTO);
       contratService.addContrat(contrat);
       return contratDTO;
    }

    // Updating an existing contrat
    @PutMapping("/update-contrat")
    @ResponseBody
    public ContratDTO updateContrat(@RequestBody ContratDTO contratDTO) {
        Contrat contrat= ContratDTO.convertToEntity(contratDTO);
        contratService.updateContrat(contrat);
        return contratDTO;
    }

    // Adding and affecting a contrat to an etudiant
    @PostMapping("/addAndAffectContratToEtudiant/{nomE}/{prenomE}")
    @ResponseBody
    public ContratDTO addAndAffectContratToEtudiant(@RequestBody ContratDTO contratDTO, @PathVariable("nomE") String nomE, @PathVariable("prenomE") String prenomE) {
        Contrat contrat = ContratDTO.convertToEntity(contratDTO); // Assuming this method converts DTO to Entity
        contratService.addAndAffectContratToEtudiant(contrat, nomE, prenomE);
        return contratDTO;
    }

    //The most common ISO Date Format yyyy-MM-dd — for example, "2000-10-31".
    @GetMapping(value = "/getnbContratsValides/{startDate}/{endDate}")
    public Integer getnbContratsValides(@PathVariable(name = "startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
                                        @PathVariable(name = "endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {

        return contratService.nbContratsValides(startDate, endDate);
    }

    //Only no-arg methods may be annotated with @Scheduled
    @Scheduled(cron="0 0 13 * * *")//(cron="0 0 13 * * ?")(fixedRate =21600)
  //  @Scheduled(cron="45 * * * * *")//(cron="0 0 13 * * ?")(fixedRate =21600)
    @PutMapping(value = "/majStatusContrat")
    public void majStatusContrat (){
        contratService.retrieveAndUpdateStatusContrat();
    }

    //public float getChiffreAffaireEntreDeuxDate(Date startDate, Date endDate)

    @GetMapping("/calculChiffreAffaireEntreDeuxDate/{startDate}/{endDate}")
    @ResponseBody
    public float calculChiffreAffaireEntreDeuxDates(@PathVariable(name = "startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
                                                    @PathVariable(name = "endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {

        return contratService.getChiffreAffaireEntreDeuxDates(startDate, endDate);
    }
    @DeleteMapping("/remove-contrat/{id}")
    public ResponseEntity<Void> removeContrat(@PathVariable("id") Integer idContrat) {
        try {
            contratService.removeContrat(idContrat);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
