package tn.esprit.spring.khaddem.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tn.esprit.spring.khaddem.entities.Contrat;
import tn.esprit.spring.khaddem.entities.Etudiant;
import tn.esprit.spring.khaddem.entities.Specialite;
import tn.esprit.spring.khaddem.repositories.ContratRepository;
import tn.esprit.spring.khaddem.repositories.EtudiantRepository;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
@Slf4j

public class ContratServiceImpl implements  IContratService{
    ContratRepository contratRepository;
    EtudiantRepository etudiantRepository;
    public ContratServiceImpl(ContratRepository contratRepository, EtudiantRepository etudiantRepository) {
        this.contratRepository = contratRepository;
        this.etudiantRepository = etudiantRepository;
    }


    @Override
    public List<Contrat> retrieveAllContrats() {
        log.info("debut methode retrieveAllContrats");
        return contratRepository.findAll();
    }

    @Override
    public Contrat updateContrat(Contrat ce) {
        log.info("debut methode updateContrat");
        contratRepository.save(ce);
        return ce;
    }

    @Override
    public Contrat retrieveContrat(Integer idContrat) {
        log.info("debut methode retrieveContrat");
        return contratRepository.findById(idContrat).orElse(null);
    }

    @Override
    public void removeContrat(Integer idContrat) {
        log.info("debut methode removeContrat");
        contratRepository.deleteById(idContrat);
    }

    @Override
    public Contrat addContrat(Contrat c) {
        contratRepository.save(c);
        return c;
    }

    @Transactional
    public Contrat addAndAffectContratToEtudiant(Contrat ce, String nomE, String prenomE) {
        Long startDate = new Date().getTime();
        log.info("startDate: "+startDate);
        log.info("debut methode addAndAffectContratToEtudiant");
        Etudiant etudiant=etudiantRepository.findByNomEAndPrenomE(nomE,prenomE);
        log.info("etudiant: "+etudiant.getNomE()+" "+etudiant.getPrenomE());
        int nbContratsActifs= etudiant.getContrats().size();
        if(nbContratsActifs>5) {
            log.info("nombre de contrats autorisés est atteint");
            Long endDate = new Date().getTime();
            long executionTime = endDate-startDate;
            log.info("endDate: "+startDate);
            log.info("executionTime: "+executionTime+ " ms");
            return ce;
        }
        log.info("nb Contrats en cours: "+nbContratsActifs);
        contratRepository.save(ce);
        ce.setEtudiant(etudiant);
        log.info("fin methode addAndAffectContratToEtudiant");
        Long endDate = new Date().getTime();
        long executionTime = endDate-startDate;

        log.info("endDate: "+startDate);
        log.info("executionTime: "+executionTime+ " ms");

        return ce;
    }

    public 	Integer nbContratsValides(Date startDate, Date endDate){
        return contratRepository.getnbContratsValides(startDate, endDate);
    }

    public void retrieveAndUpdateStatusContrat(){
        log.info("debut methode retrieveAndUpdateStatusContrat");
        List<Contrat>contrats=contratRepository.findAll();
        log.info("total contrats :"+contrats.size());

        for (Contrat contrat : contrats) {
            log.info("id: "+contrat.getIdContrat());
            log.info("date fin"+contrat.getDateFinContrat());
            log.info("archived "+contrat.getArchived());

            Date dateSysteme = new Date();

            if (contrat.getArchived()==null || contrat.getArchived()) {
                long differenceInTime = contrat.getDateFinContrat().getTime()-dateSysteme.getTime();
                long differenceInDays = (differenceInTime / (1000 * 60 * 60 * 24)) % 365;
               log.info("difference in days : "+differenceInDays);
                    if (differenceInDays==15){  // pour 15 jours exactement
                    log.info(" Contrat Commencant le : " + contrat.getDateDebutContrat()+"pour l'etudiant "+contrat.getEtudiant().getNomE()+
                            " "+contrat.getEtudiant().getPrenomE()+"  va bientot s achever le "
                    +contrat.getDateFinContrat());
                }
                if (differenceInDays==0) {
                    log.info("jour j: " + contrat.getIdContrat());
                    contrat.setArchived(true);
                    contratRepository.save(contrat);
                }
            }

            log.info("debut methode retrieveAndUpdateStatusContrat");
        }
    }
    public float getChiffreAffaireEntreDeuxDates(Date startDate, Date endDate){
        float differenceInTime = (float) endDate.getTime() - (float) startDate.getTime();
        float differenceInDays = (differenceInTime / (1000.0f * 60.0f * 60.0f * 24.0f)) % 365.0f;
        float differenceInmonths = differenceInDays / 30.0f;
        List<Contrat> contrats=contratRepository.findAll();
        float chiffreAffaireEntreDeuxDates=0;
        float chiffreAffaireEntreDeuxDatesIA=0;
        float chiffreAffaireEntreDeuxDatesCloud=0;
        float chiffreAffaireEntreDeuxDatesReseau=0;
        float chiffreAffaireEntreDeuxDatesSecurite=0;

        for (Contrat contrat : contrats) {
            if (contrat.getSpecialite()== Specialite.IA){
                chiffreAffaireEntreDeuxDates+=(differenceInmonths*contrat.getMontantContrat());
                chiffreAffaireEntreDeuxDatesIA+=(differenceInmonths*contrat.getMontantContrat());

            } else if (contrat.getSpecialite()== Specialite.CLOUD) {
                chiffreAffaireEntreDeuxDates+=(differenceInmonths*contrat.getMontantContrat());
                chiffreAffaireEntreDeuxDatesCloud+=(differenceInmonths*contrat.getMontantContrat());
            }
            else if (contrat.getSpecialite()== Specialite.RESEAU) {
                chiffreAffaireEntreDeuxDates+=(differenceInmonths*contrat.getMontantContrat());
                chiffreAffaireEntreDeuxDatesReseau+=(differenceInmonths*contrat.getMontantContrat());

            }
            else if (contrat.getSpecialite()== Specialite.SECURITE)
            {
                chiffreAffaireEntreDeuxDates+=(differenceInmonths*contrat.getMontantContrat());
                chiffreAffaireEntreDeuxDatesSecurite+=(differenceInmonths*contrat.getMontantContrat());

            }
        }
        log.info("chiffreAffaireEntreDeuxDates: "+chiffreAffaireEntreDeuxDates);
        log.info("chiffreAffaireEntreDeuxDatesIA:" +chiffreAffaireEntreDeuxDatesIA);
        log.info("chiffreAffaireEntreDeuxDatesCloud "+chiffreAffaireEntreDeuxDatesCloud);
        log.info("chiffreAffaireEntreDeuxDatesReseau "+chiffreAffaireEntreDeuxDatesReseau);
        log.info("chiffreAffaireEntreDeuxDatesSecurite "+chiffreAffaireEntreDeuxDatesSecurite);
        return chiffreAffaireEntreDeuxDates;


    }

}
