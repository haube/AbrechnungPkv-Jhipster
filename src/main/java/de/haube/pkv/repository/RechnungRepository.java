package de.haube.pkv.repository;

import de.haube.pkv.domain.Rechnung;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Rechnung entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RechnungRepository extends JpaRepository<Rechnung, Long> {

}
