package tn.esprit.spring.khaddem.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.spring.khaddem.entities.Contrat;
import tn.esprit.spring.khaddem.entities.Specialite;

import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
public class ContratDTO {
    private Integer idContrat;
    private Date dateDebutContrat;
    private Date dateFinContrat;
    private Specialite specialite;
    private Boolean archived;
    private Integer montantContrat;



        public static Contrat convertToEntity(ContratDTO contratDTO) {
            Contrat contrat = new Contrat();
            contrat.setIdContrat(contratDTO.getIdContrat());
            contrat.setDateDebutContrat(contratDTO.getDateDebutContrat());
            contrat.setDateFinContrat(contratDTO.getDateFinContrat());
            contrat.setSpecialite(contratDTO.getSpecialite());
            contrat.setArchived(contratDTO.getArchived());
            contrat.setMontantContrat(contratDTO.getMontantContrat());
            return contrat;
        }
    }


