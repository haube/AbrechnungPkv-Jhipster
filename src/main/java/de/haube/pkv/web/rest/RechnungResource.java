package de.haube.pkv.web.rest;

import de.haube.pkv.domain.Rechnung;
import de.haube.pkv.service.RechnungService;
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

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link de.haube.pkv.domain.Rechnung}.
 */
@RestController
@RequestMapping("/api")
public class RechnungResource {

    private final Logger log = LoggerFactory.getLogger(RechnungResource.class);

    private static final String ENTITY_NAME = "rechnung";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RechnungService rechnungService;

    public RechnungResource(RechnungService rechnungService) {
        this.rechnungService = rechnungService;
    }

    /**
     * {@code POST  /rechnungs} : Create a new rechnung.
     *
     * @param rechnung the rechnung to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rechnung, or with status {@code 400 (Bad Request)} if the rechnung has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/rechnungs")
    public ResponseEntity<Rechnung> createRechnung(@RequestBody Rechnung rechnung) throws URISyntaxException {
        log.debug("REST request to save Rechnung : {}", rechnung);
        if (rechnung.getId() != null) {
            throw new BadRequestAlertException("A new rechnung cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Rechnung result = rechnungService.save(rechnung);
        return ResponseEntity.created(new URI("/api/rechnungs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /rechnungs} : Updates an existing rechnung.
     *
     * @param rechnung the rechnung to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rechnung,
     * or with status {@code 400 (Bad Request)} if the rechnung is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rechnung couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/rechnungs")
    public ResponseEntity<Rechnung> updateRechnung(@RequestBody Rechnung rechnung) throws URISyntaxException {
        log.debug("REST request to update Rechnung : {}", rechnung);
        if (rechnung.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Rechnung result = rechnungService.save(rechnung);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rechnung.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /rechnungs} : get all the rechnungs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rechnungs in body.
     */
    @GetMapping("/rechnungs")
    public ResponseEntity<List<Rechnung>> getAllRechnungs(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of Rechnungs");
        Page<Rechnung> page = rechnungService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /rechnungs/:id} : get the "id" rechnung.
     *
     * @param id the id of the rechnung to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rechnung, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rechnungs/{id}")
    public ResponseEntity<Rechnung> getRechnung(@PathVariable Long id) {
        log.debug("REST request to get Rechnung : {}", id);
        Optional<Rechnung> rechnung = rechnungService.findOne(id);
        return ResponseUtil.wrapOrNotFound(rechnung);
    }

    /**
     * {@code DELETE  /rechnungs/:id} : delete the "id" rechnung.
     *
     * @param id the id of the rechnung to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/rechnungs/{id}")
    public ResponseEntity<Void> deleteRechnung(@PathVariable Long id) {
        log.debug("REST request to delete Rechnung : {}", id);
        rechnungService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
