package de.haube.pkv.service.impl;

import de.haube.pkv.service.ArztService;
import de.haube.pkv.domain.Arzt;
import de.haube.pkv.repository.ArztRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Arzt}.
 */
@Service
@Transactional
public class ArztServiceImpl implements ArztService {

    private final Logger log = LoggerFactory.getLogger(ArztServiceImpl.class);

    private final ArztRepository arztRepository;

    public ArztServiceImpl(ArztRepository arztRepository) {
        this.arztRepository = arztRepository;
    }

    /**
     * Save a arzt.
     *
     * @param arzt the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Arzt save(Arzt arzt) {
        log.debug("Request to save Arzt : {}", arzt);
        return arztRepository.save(arzt);
    }

    /**
     * Get all the arzts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Arzt> findAll(Pageable pageable) {
        log.debug("Request to get all Arzts");
        return arztRepository.findAll(pageable);
    }


    /**
     * Get one arzt by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Arzt> findOne(Long id) {
        log.debug("Request to get Arzt : {}", id);
        return arztRepository.findById(id);
    }

    /**
     * Delete the arzt by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Arzt : {}", id);
        arztRepository.deleteById(id);
    }
}
