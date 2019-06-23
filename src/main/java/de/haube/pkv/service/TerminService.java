package de.haube.pkv.service;

import de.haube.pkv.domain.Termin;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Termin}.
 */
public interface TerminService {

    /**
     * Save a termin.
     *
     * @param termin the entity to save.
     * @return the persisted entity.
     */
    Termin save(Termin termin);

    /**
     * Get all the termins.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Termin> findAll(Pageable pageable);


    /**
     * Get the "id" termin.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Termin> findOne(Long id);

    /**
     * Delete the "id" termin.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
