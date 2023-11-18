package tn.esprit.spring.khaddem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.spring.khaddem.entities.DetailEquipe;
import tn.esprit.spring.khaddem.entities.Equipe;
import tn.esprit.spring.khaddem.entities.Etudiant;
import tn.esprit.spring.khaddem.entities.Niveau;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class EquipeDTO {
    private Integer idEquipe;
    private String nomEquipe;
    private Niveau niveau;
    private List<Etudiant> etudiants;
    private DetailEquipe detailEquipe;

    public static Equipe convertDTOtoEntity(EquipeDTO e){
        Equipe equipe = new Equipe();
        equipe.setIdEquipe(e.idEquipe);
        equipe.setNomEquipe(e.getNomEquipe());
        equipe.setNiveau(e.getNiveau());
        equipe.setEtudiants(e.getEtudiants());
        equipe.setDetailEquipe(e.getDetailEquipe());
        return equipe;
    }
}
