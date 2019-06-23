package de.haube.pkv.repository;

import de.haube.pkv.domain.Arzt;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Arzt entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArztRepository extends JpaRepository<Arzt, Long> {

}
