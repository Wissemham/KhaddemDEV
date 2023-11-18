package tn.esprit.spring.khaddem.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.spring.khaddem.entities.DetailEquipe;
import tn.esprit.spring.khaddem.repositories.DetailEquipeRepository;

import java.util.List;

@Service
@Slf4j
public class DetailEquipeServiceImpl implements IDetailEquipeService {
    @Autowired
    DetailEquipeRepository detailEquipeRepository;

    //addg
    @Override
    public DetailEquipe addDetailEquipe(DetailEquipe detailEquipe) {
        detailEquipeRepository.save(detailEquipe);
        return detailEquipe;
    }

    @Override
    public DetailEquipe updateDetailEquipe(DetailEquipe d) {
        detailEquipeRepository.save(d);
        return d;
    }

    @Override
    public void supprimerDetailEquipe(Integer id) {
        detailEquipeRepository.deleteById(id);

    }

    @Override
    public List<DetailEquipe> retrieveAllDetailEquipe() {
        List<DetailEquipe> detailEquipes = detailEquipeRepository.findAll();
        log.info("Retrieved detailEquipes from the database: " + detailEquipes);
        return detailEquipes;
    }

}