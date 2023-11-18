package tn.esprit.spring.khaddem.services;

import tn.esprit.spring.khaddem.entities.DetailEquipe;

import java.util.List;

public interface IDetailEquipeService {
    DetailEquipe addDetailEquipe(DetailEquipe detailEquipe);
    DetailEquipe updateDetailEquipe(DetailEquipe d);
    void supprimerDetailEquipe(Integer id);
    List<DetailEquipe> retrieveAllDetailEquipe();

}