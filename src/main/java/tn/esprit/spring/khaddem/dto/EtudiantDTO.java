package tn.esprit.spring.khaddem.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.spring.khaddem.entities.Etudiant;
import tn.esprit.spring.khaddem.entities.Option;

@Getter
@Setter
@NoArgsConstructor
public class EtudiantDTO {
    private Integer idEtudiant;
    private String prenomE;
    private String nomE;
    private Option op;

    public static EtudiantDTO convertEntityToDTO(Etudiant etudiant) {
        EtudiantDTO etudiantDTO = new EtudiantDTO();
        etudiantDTO.setIdEtudiant(etudiant.getIdEtudiant());
        etudiantDTO.setPrenomE(etudiant.getPrenomE());
        etudiantDTO.setNomE(etudiant.getNomE());
        etudiantDTO.setOp(etudiant.getOp());
        // Set other fields if needed

        return etudiantDTO;
    }

    public Etudiant convertDTOToEntity(EtudiantDTO etudiantDTO) {
        Etudiant etudiant = new Etudiant();
        etudiant.setIdEtudiant(etudiantDTO.getIdEtudiant());
        etudiant.setPrenomE(etudiantDTO.getPrenomE());
        etudiant.setNomE(etudiantDTO.getNomE());
        etudiant.setOp(etudiantDTO.getOp());
        // Set other fields if needed

        return etudiant;
    }
}
