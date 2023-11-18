package tn.esprit.spring.khaddem.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.spring.khaddem.entities.Departement;
import tn.esprit.spring.khaddem.entities.Universite;
import tn.esprit.spring.khaddem.repositories.DepartementRepository;
import tn.esprit.spring.khaddem.repositories.UniversiteRepository;

import java.util.*;

@Service
@Slf4j
public class DepartementServiceImpl implements IDepartementService {
    DepartementRepository departementRepository;
    UniversiteRepository universiteRepository;
    @Autowired
    public DepartementServiceImpl(DepartementRepository departementRepository, UniversiteRepository universiteRepository) {
        this.departementRepository = departementRepository;
        this.universiteRepository = universiteRepository;
    }

    @Override
    public List<Departement> retrieveAllDepartements() {

        return departementRepository.findAll();
    }

    @Override
    public Departement addDepartement(Departement d) {
        log.debug("d :" + d.getNomDepart());
        departementRepository.save(d);
        return d;
    }

    @Override
    public Departement updateDepartement(Departement dep) {
        departementRepository.save(dep);
        return dep;
    }

    @Override
    public Departement retrieveDepartement(Integer idDepart) {
        Optional<Departement> optionalDepartement = departementRepository.findById(idDepart);
        if (optionalDepartement.isPresent()) {
            return optionalDepartement.get();
        } else {
            throw new NoSuchElementException("Department not found for ID: " + idDepart);
        }
    }
    @Override
    public List<Departement> retrieveDepartementsByUniversite(Integer idUniversite) {
        Optional<Universite> universiteOptional = universiteRepository.findById(idUniversite);
        if (universiteOptional.isPresent()) {
            Universite universite = universiteOptional.get();
            return universite.getDepartements();
        } else {
            throw new NoSuchElementException("University not found for ID: " + idUniversite);
        }
    }
}