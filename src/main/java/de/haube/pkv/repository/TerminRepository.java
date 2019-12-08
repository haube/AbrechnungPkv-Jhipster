package de.haube.pkv.repository;

import de.haube.pkv.domain.Termin;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Termin entity.
 */
@Repository
public interface TerminRepository extends JpaRepository<Termin, Long> {

}
