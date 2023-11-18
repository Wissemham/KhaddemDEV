package tn.esprit.spring.khaddem.dto;

import lombok.Getter;
import lombok.Setter;
import tn.esprit.spring.khaddem.entities.Departement;

@Getter
@Setter
public class DepartementDTO {
    private Integer idDepartement;
    private String nomDepart;
    public DepartementDTO convertToDTO(Departement departement) {
        DepartementDTO departementDTO = new DepartementDTO();
        departementDTO.setIdDepartement (departement.getIdDepartement());
        departementDTO.setNomDepart(departement.getNomDepart());
        // Convert departements manually or omit them if not needed in the DTO
        return departementDTO;
    }

    public Departement convertToEntity(DepartementDTO departementDTO) {
      Departement departement   = new Departement();
        departement.setIdDepartement(departementDTO.getIdDepartement());
        departement.setNomDepart(departementDTO.getNomDepart());
        // Convert departements manually or omit them if not needed in the entity
        return departement;
    }

}