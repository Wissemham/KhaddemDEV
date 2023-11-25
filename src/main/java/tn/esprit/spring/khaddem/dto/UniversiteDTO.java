package tn.esprit.spring.khaddem.dto;

import lombok.Getter;
import lombok.Setter;
import tn.esprit.spring.khaddem.entities.Universite;

@Getter
@Setter
public class UniversiteDTO {
    private Integer idUniversite;
    private String nomUniv;

    public static UniversiteDTO convertToDTO(Universite universite) {
        UniversiteDTO universiteDTO = new UniversiteDTO();
        universiteDTO.setIdUniversite(universite.getIdUniversite());
        universiteDTO.setNomUniv(universite.getNomUniv());
        return universiteDTO;
    }

    public static Universite convertToEntity(UniversiteDTO universiteDTO) {
        Universite universite = new Universite();
        universite.setIdUniversite(universiteDTO.getIdUniversite());
        universite.setNomUniv(universiteDTO.getNomUniv());
        return universite;
    }
}




