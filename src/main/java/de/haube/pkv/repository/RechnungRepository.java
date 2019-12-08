package de.haube.pkv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.haube.pkv.domain.Rechnung;


/**
 * Spring Data  repository for the Rechnung entity.
 */
@Repository
public interface RechnungRepository extends JpaRepository<Rechnung, Long> {

}
