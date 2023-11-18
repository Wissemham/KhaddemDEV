package tn.esprit.spring.khaddem.dto;

import lombok.Getter;
import lombok.Setter;
import tn.esprit.spring.khaddem.entities.Universite;

import java.util.List;

@Getter
@Setter
public class UniversiteDTO {
    private Integer idUniversite;
    private String nomUniv;
    private List<DepartementDTO> departements;

    public UniversiteDTO convertToDTO(Universite universite) {
        UniversiteDTO universiteDTO = new UniversiteDTO();
        universiteDTO.setIdUniversite(universite.getIdUniversite());
        universiteDTO.setNomUniv(universite.getNomUniv());
        return universiteDTO;
    }

    public Universite convertToEntity(UniversiteDTO universiteDTO) {
        Universite universite = new Universite();
        universite.setIdUniversite(universiteDTO.getIdUniversite());
        universite.setNomUniv(universiteDTO.getNomUniv());
        return universite;
    }
}




