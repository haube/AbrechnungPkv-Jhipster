package de.haube.pkv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.haube.pkv.domain.Termin;


/**
 * Spring Data  repository for the Termin entity.
 */
@Repository
public interface TerminRepository extends JpaRepository<Termin, Long> {

}
