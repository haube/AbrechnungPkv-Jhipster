package de.haube.pkv.service.impl;

import de.haube.pkv.service.TerminService;
import de.haube.pkv.domain.Termin;
import de.haube.pkv.repository.TerminRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Termin}.
 */
@Service
@Transactional
public class TerminServiceImpl implements TerminService {

    private final Logger log = LoggerFactory.getLogger(TerminServiceImpl.class);

    private final TerminRepository terminRepository;

    public TerminServiceImpl(TerminRepository terminRepository) {
        this.terminRepository = terminRepository;
    }

    /**
     * Save a termin.
     *
     * @param termin the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Termin save(Termin termin) {
        log.debug("Request to save Termin : {}", termin);
        return terminRepository.save(termin);
    }

    /**
     * Get all the termins.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Termin> findAll(Pageable pageable) {
        log.debug("Request to get all Termins");
        return terminRepository.findAll(pageable);
    }


    /**
     * Get one termin by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Termin> findOne(Long id) {
        log.debug("Request to get Termin : {}", id);
        return terminRepository.findById(id);
    }

    /**
     * Delete the termin by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Termin : {}", id);
        terminRepository.deleteById(id);
    }
}
