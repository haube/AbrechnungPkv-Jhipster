package de.haube.pkv.service.impl;

import de.haube.pkv.service.RechnungService;
import de.haube.pkv.domain.Rechnung;
import de.haube.pkv.repository.RechnungRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Rechnung}.
 */
@Service
@Transactional
public class RechnungServiceImpl implements RechnungService {

    private final Logger log = LoggerFactory.getLogger(RechnungServiceImpl.class);

    private final RechnungRepository rechnungRepository;

    public RechnungServiceImpl(RechnungRepository rechnungRepository) {
        this.rechnungRepository = rechnungRepository;
    }

    /**
     * Save a rechnung.
     *
     * @param rechnung the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Rechnung save(Rechnung rechnung) {
        log.debug("Request to save Rechnung : {}", rechnung);
        return rechnungRepository.save(rechnung);
    }

    /**
     * Get all the rechnungs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Rechnung> findAll(Pageable pageable) {
        log.debug("Request to get all Rechnungs");
        return rechnungRepository.findAll(pageable);
    }


    /**
     * Get one rechnung by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Rechnung> findOne(Long id) {
        log.debug("Request to get Rechnung : {}", id);
        return rechnungRepository.findById(id);
    }

    /**
     * Delete the rechnung by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Rechnung : {}", id);
        rechnungRepository.deleteById(id);
    }
}
