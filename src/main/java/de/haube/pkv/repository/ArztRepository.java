package de.haube.pkv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.haube.pkv.domain.Arzt;


/**
 * Spring Data  repository for the Arzt entity.
 */
@Repository
public interface ArztRepository extends JpaRepository<Arzt, Long> {

}
