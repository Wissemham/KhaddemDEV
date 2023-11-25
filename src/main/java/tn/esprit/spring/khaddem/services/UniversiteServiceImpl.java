package tn.esprit.spring.khaddem.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tn.esprit.spring.khaddem.entities.Departement;
import tn.esprit.spring.khaddem.entities.Universite;
import tn.esprit.spring.khaddem.repositories.DepartementRepository;
import tn.esprit.spring.khaddem.repositories.UniversiteRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Slf4j
public class UniversiteServiceImpl implements IUniversiteService {
    UniversiteRepository universiteRepository;
    DepartementRepository departementRepository;

    public UniversiteServiceImpl(UniversiteRepository universiteRepository, DepartementRepository departementRepository) {
        this.universiteRepository = universiteRepository;
        this.departementRepository = departementRepository;
    }

    @Override
    public List<Universite> retrieveAllUniversites() {
        return universiteRepository.findAll();
    }

    @Override
    public Universite addUniversite(Universite u) {
        log.debug("Adding Universite: " + u.getNomUniv());
        universiteRepository.save(u);
        return u;
    }

    @Override
    public Universite updateUniversite(Universite u) {
        log.debug("Updating Universite: " + u.getNomUniv());
        universiteRepository.save(u);
        return u;
    }

    @Override
    public Universite retrieveUniversite(Integer idUniversite) {
        Optional<Universite> universiteOptional = universiteRepository.findById(idUniversite);
        if (universiteOptional.isPresent()) {
            return universiteOptional.get();
        } else {
            log.error("Universite not found for id " + idUniversite);
            throw new NoSuchElementException("Universite not found for id " + idUniversite);
        }
    }

    @Override
    public void assignUniversiteToDepartement(Integer universiteId, Integer departementId) {
        Optional<Universite> universiteOptional = universiteRepository.findById(universiteId);
        Optional<Departement> departementOptional = departementRepository.findById(departementId);
        if (universiteOptional.isPresent() && departementOptional.isPresent()) {
            Universite universite = universiteOptional.get();
            Departement departement = departementOptional.get();
            universite.getDepartements().add(departement);
            log.info("Assigned Departement to Universite. Departements number: " + universite.getDepartements().size());
        } else {
            throw new NoSuchElementException("University not found for ID: " + universiteId);
        }
    }
}