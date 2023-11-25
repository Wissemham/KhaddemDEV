package tn.esprit.spring.khaddem.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tn.esprit.spring.khaddem.entities.Contrat;
import tn.esprit.spring.khaddem.entities.Equipe;
import tn.esprit.spring.khaddem.entities.Etudiant;
import tn.esprit.spring.khaddem.entities.Niveau;
import tn.esprit.spring.khaddem.repositories.ContratRepository;
import tn.esprit.spring.khaddem.repositories.EquipeRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class EquipeServiceImpl implements IEquipeService {

    EquipeRepository equipeRepository;

    ContratRepository contratRepository;

    @Override
    public List<Equipe> retrieveAllEquipes() {
        return equipeRepository.findAll();
    }

    @Transactional
    public Equipe addEquipe(Equipe e) {
        log.debug("e :" + e.getNomEquipe());
        equipeRepository.save(e);
        return e;
    }

    @Override
    public Equipe updateEquipe(Equipe e) {
        equipeRepository.save(e);
        return e;
    }

    @Override
    public Equipe retrieveEquipe(Integer idEquipe) {

        return equipeRepository.findById(idEquipe).orElse(null);
    }


    @Override
    public void evoluerEquipes() {
        log.info("debut methode evoluerEquipes");

        List<Equipe> equipes = equipeRepository.findAll();
        log.debug("nombre equipes : " + equipes.size());

        for (Equipe equipe : equipes) {
            processEquipe(equipe);
        }

        log.info("fin methode evoluerEquipes");
    }

    private void processEquipe(Equipe equipe) {
        if (equipe.getEtudiants() == null || equipe.getEtudiants().isEmpty()) {
            return;
        }

        log.debug("vérification niveau équipe");

        if (equipe.getNiveau().equals(Niveau.JUNIOR) || equipe.getNiveau().equals(Niveau.SENIOR)) {
            int nbEtudiantsAvecContratsActifs = countActiveContracts(equipe.getEtudiants());

            log.info("nbEtudiantsAvecContratsActifs: " + nbEtudiantsAvecContratsActifs);

            if (nbEtudiantsAvecContratsActifs >= 3) {
                updateEquipeNiveau(equipe);
            }
        }
    }

    private int countActiveContracts(List<Etudiant> etudiants) {
        int nbEtudiantsAvecContratsActifs = 0;

        for (Etudiant etudiant : etudiants) {
            log.debug("in for etudiants");
            List<Contrat> contrats = etudiant.getContrats();

            for (Contrat contrat : contrats) {
                log.debug("in contrat");

                long differenceInTime = contrat.getDateFinContrat().getTime() - contrat.getDateDebutContrat().getTime();
                long differenceInYears = (differenceInTime / (1000L * 60 * 60 * 24 * 365));
                log.debug("difference_In_Years: " + differenceInYears);

                if ((contrat.getArchived() == null || !contrat.getArchived()) && (differenceInYears > 1)) {
                    nbEtudiantsAvecContratsActifs++;

                    if (nbEtudiantsAvecContratsActifs >= 3) {
                        return nbEtudiantsAvecContratsActifs;  // Early return if the threshold is reached
                    }
                }
            }
        }

        return nbEtudiantsAvecContratsActifs;
    }

    private void updateEquipeNiveau(Equipe equipe) {
        if (equipe.getNiveau().equals(Niveau.JUNIOR)) {
            log.info("mise a jour du niveau de l equipe " + equipe.getNomEquipe() +
                    " du niveau " + equipe.getNiveau() + " vers le niveau supérieur SENIOR");
            equipe.setNiveau(Niveau.SENIOR);
            equipeRepository.save(equipe);
        } else if (equipe.getNiveau().equals(Niveau.SENIOR)) {
            log.info("mise a jour du niveau de l equipe " + equipe.getNomEquipe() +
                    " du niveau " + equipe.getNiveau() + " vers le niveau supérieur EXPERT");
            equipe.setNiveau(Niveau.EXPERT);
            equipeRepository.save(equipe);
        }

    }

}


