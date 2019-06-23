package de.haube.pkv.web.rest;

import de.haube.pkv.domain.Termin;
import de.haube.pkv.service.TerminService;
import de.haube.pkv.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link de.haube.pkv.domain.Termin}.
 */
@RestController
@RequestMapping("/api")
public class TerminResource {

    private final Logger log = LoggerFactory.getLogger(TerminResource.class);

    private static final String ENTITY_NAME = "termin";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TerminService terminService;

    public TerminResource(TerminService terminService) {
        this.terminService = terminService;
    }

    /**
     * {@code POST  /termins} : Create a new termin.
     *
     * @param termin the termin to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new termin, or with status {@code 400 (Bad Request)} if the termin has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/termins")
    public ResponseEntity<Termin> createTermin(@Valid @RequestBody Termin termin) throws URISyntaxException {
        log.debug("REST request to save Termin : {}", termin);
        if (termin.getId() != null) {
            throw new BadRequestAlertException("A new termin cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Termin result = terminService.save(termin);
        return ResponseEntity.created(new URI("/api/termins/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /termins} : Updates an existing termin.
     *
     * @param termin the termin to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated termin,
     * or with status {@code 400 (Bad Request)} if the termin is not valid,
     * or with status {@code 500 (Internal Server Error)} if the termin couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/termins")
    public ResponseEntity<Termin> updateTermin(@Valid @RequestBody Termin termin) throws URISyntaxException {
        log.debug("REST request to update Termin : {}", termin);
        if (termin.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Termin result = terminService.save(termin);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, termin.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /termins} : get all the termins.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of termins in body.
     */
    @GetMapping("/termins")
    public ResponseEntity<List<Termin>> getAllTermins(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of Termins");
        Page<Termin> page = terminService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /termins/:id} : get the "id" termin.
     *
     * @param id the id of the termin to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the termin, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/termins/{id}")
    public ResponseEntity<Termin> getTermin(@PathVariable Long id) {
        log.debug("REST request to get Termin : {}", id);
        Optional<Termin> termin = terminService.findOne(id);
        return ResponseUtil.wrapOrNotFound(termin);
    }

    /**
     * {@code DELETE  /termins/:id} : delete the "id" termin.
     *
     * @param id the id of the termin to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/termins/{id}")
    public ResponseEntity<Void> deleteTermin(@PathVariable Long id) {
        log.debug("REST request to delete Termin : {}", id);
        terminService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
