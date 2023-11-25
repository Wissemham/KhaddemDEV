package tn.esprit.spring.khaddem.dto;

import lombok.*;
import tn.esprit.spring.khaddem.entities.DetailEquipe;

@Getter
@Setter
@NoArgsConstructor
public class DetailEquipeDTO {
    private Integer idDetailEquipe;
    private Integer salle;
    private String thematique;
    public static DetailEquipeDTO convertToDTO(DetailEquipe detailEquipe) {
        DetailEquipeDTO detailEquipeDTO = new DetailEquipeDTO();
        detailEquipeDTO.setIdDetailEquipe(detailEquipe.getIdDetailEquipe());
        detailEquipeDTO.setSalle(detailEquipe.getSalle());
        detailEquipeDTO.setThematique(detailEquipe.getThematique());
        // Convert other fields manually or omit them if not needed in the DTO
        return detailEquipeDTO;
    }

    public static DetailEquipe convertToEntity(DetailEquipeDTO detailEquipeDTO) {
        DetailEquipe detailEquipe = new DetailEquipe();
        detailEquipe.setIdDetailEquipe(detailEquipeDTO.getIdDetailEquipe());
        detailEquipe.setSalle(detailEquipeDTO.getSalle());
        detailEquipe.setThematique(detailEquipeDTO.getThematique());
        // Convert other fields manually or omit them if not needed in the entity
        return detailEquipe;
    }

}