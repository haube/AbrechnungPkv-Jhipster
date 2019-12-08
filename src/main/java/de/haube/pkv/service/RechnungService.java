package de.haube.pkv.service;

import de.haube.pkv.domain.Rechnung;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Rechnung}.
 */
public interface RechnungService {

    /**
     * Save a rechnung.
     *
     * @param rechnung the entity to save.
     * @return the persisted entity.
     */
    Rechnung save(Rechnung rechnung);

    /**
     * Get all the rechnungs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Rechnung> findAll(Pageable pageable);


    /**
     * Get the "id" rechnung.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Rechnung> findOne(Long id);

    /**
     * Delete the "id" rechnung.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
