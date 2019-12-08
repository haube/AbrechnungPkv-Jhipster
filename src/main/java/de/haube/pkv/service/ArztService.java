package de.haube.pkv.service;

import de.haube.pkv.domain.Arzt;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Arzt}.
 */
public interface ArztService {

    /**
     * Save a arzt.
     *
     * @param arzt the entity to save.
     * @return the persisted entity.
     */
    Arzt save(Arzt arzt);

    /**
     * Get all the arzts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Arzt> findAll(Pageable pageable);


    /**
     * Get the "id" arzt.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Arzt> findOne(Long id);

    /**
     * Delete the "id" arzt.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
