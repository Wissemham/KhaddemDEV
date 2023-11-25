package tn.esprit.spring.khaddem.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NoArgsConstructor

public class DetailEquipe implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDetailEquipe;
    private Integer salle;
    private String thematique;
    @OneToOne(mappedBy = "detailEquipe")
    @JsonIgnore
    private Equipe equipe;

    public Integer getIdDetailEquipe() {
        return idDetailEquipe;
    }

    public Integer getSalle() {
        return salle;
    }

    public String getThematique() {
        return thematique;
    }

    public void setIdDetailEquipe(Integer idDetailEquipe) {
        this.idDetailEquipe = idDetailEquipe;
    }

    public void setSalle(Integer salle) {
        this.salle = salle;
    }

    public void setThematique(String thematique) {
        this.thematique = thematique;
    }
}
