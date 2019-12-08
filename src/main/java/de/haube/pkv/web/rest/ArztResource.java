package de.haube.pkv.web.rest;

import de.haube.pkv.domain.Arzt;
import de.haube.pkv.service.ArztService;
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
 * REST controller for managing {@link de.haube.pkv.domain.Arzt}.
 */
@RestController
@RequestMapping("/api")
public class ArztResource {

    private final Logger log = LoggerFactory.getLogger(ArztResource.class);

    private static final String ENTITY_NAME = "arzt";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ArztService arztService;

    public ArztResource(ArztService arztService) {
        this.arztService = arztService;
    }

    /**
     * {@code POST  /arzts} : Create a new arzt.
     *
     * @param arzt the arzt to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new arzt, or with status {@code 400 (Bad Request)} if the arzt has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/arzts")
    public ResponseEntity<Arzt> createArzt(@Valid @RequestBody Arzt arzt) throws URISyntaxException {
        log.debug("REST request to save Arzt : {}", arzt);
        if (arzt.getId() != null) {
            throw new BadRequestAlertException("A new arzt cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Arzt result = arztService.save(arzt);
        return ResponseEntity.created(new URI("/api/arzts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /arzts} : Updates an existing arzt.
     *
     * @param arzt the arzt to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated arzt,
     * or with status {@code 400 (Bad Request)} if the arzt is not valid,
     * or with status {@code 500 (Internal Server Error)} if the arzt couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/arzts")
    public ResponseEntity<Arzt> updateArzt(@Valid @RequestBody Arzt arzt) throws URISyntaxException {
        log.debug("REST request to update Arzt : {}", arzt);
        if (arzt.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Arzt result = arztService.save(arzt);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, arzt.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /arzts} : get all the arzts.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of arzts in body.
     */
    @GetMapping("/arzts")
    public ResponseEntity<List<Arzt>> getAllArzts(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of Arzts");
        Page<Arzt> page = arztService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /arzts/:id} : get the "id" arzt.
     *
     * @param id the id of the arzt to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the arzt, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/arzts/{id}")
    public ResponseEntity<Arzt> getArzt(@PathVariable Long id) {
        log.debug("REST request to get Arzt : {}", id);
        Optional<Arzt> arzt = arztService.findOne(id);
        return ResponseUtil.wrapOrNotFound(arzt);
    }

    /**
     * {@code DELETE  /arzts/:id} : delete the "id" arzt.
     *
     * @param id the id of the arzt to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/arzts/{id}")
    public ResponseEntity<Void> deleteArzt(@PathVariable Long id) {
        log.debug("REST request to delete Arzt : {}", id);
        arztService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
