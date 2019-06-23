package de.haube.pkv.web.rest;

import de.haube.pkv.PkvApp;
import de.haube.pkv.domain.Termin;
import de.haube.pkv.repository.TerminRepository;
import de.haube.pkv.service.TerminService;
import de.haube.pkv.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static de.haube.pkv.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link TerminResource} REST controller.
 */
@SpringBootTest(classes = PkvApp.class)
public class TerminResourceIT {

    private static final Instant DEFAULT_DATUM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATUM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_NOTIZ = "AAAAAAAAAA";
    private static final String UPDATED_NOTIZ = "BBBBBBBBBB";

    @Autowired
    private TerminRepository terminRepository;

    @Autowired
    private TerminService terminService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restTerminMockMvc;

    private Termin termin;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TerminResource terminResource = new TerminResource(terminService);
        this.restTerminMockMvc = MockMvcBuilders.standaloneSetup(terminResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Termin createEntity(EntityManager em) {
        Termin termin = new Termin()
            .datum(DEFAULT_DATUM)
            .notiz(DEFAULT_NOTIZ);
        return termin;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Termin createUpdatedEntity(EntityManager em) {
        Termin termin = new Termin()
            .datum(UPDATED_DATUM)
            .notiz(UPDATED_NOTIZ);
        return termin;
    }

    @BeforeEach
    public void initTest() {
        termin = createEntity(em);
    }

    @Test
    @Transactional
    public void createTermin() throws Exception {
        int databaseSizeBeforeCreate = terminRepository.findAll().size();

        // Create the Termin
        restTerminMockMvc.perform(post("/api/termins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(termin)))
            .andExpect(status().isCreated());

        // Validate the Termin in the database
        List<Termin> terminList = terminRepository.findAll();
        assertThat(terminList).hasSize(databaseSizeBeforeCreate + 1);
        Termin testTermin = terminList.get(terminList.size() - 1);
        assertThat(testTermin.getDatum()).isEqualTo(DEFAULT_DATUM);
        assertThat(testTermin.getNotiz()).isEqualTo(DEFAULT_NOTIZ);
    }

    @Test
    @Transactional
    public void createTerminWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = terminRepository.findAll().size();

        // Create the Termin with an existing ID
        termin.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTerminMockMvc.perform(post("/api/termins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(termin)))
            .andExpect(status().isBadRequest());

        // Validate the Termin in the database
        List<Termin> terminList = terminRepository.findAll();
        assertThat(terminList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDatumIsRequired() throws Exception {
        int databaseSizeBeforeTest = terminRepository.findAll().size();
        // set the field null
        termin.setDatum(null);

        // Create the Termin, which fails.

        restTerminMockMvc.perform(post("/api/termins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(termin)))
            .andExpect(status().isBadRequest());

        List<Termin> terminList = terminRepository.findAll();
        assertThat(terminList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTermins() throws Exception {
        // Initialize the database
        terminRepository.saveAndFlush(termin);

        // Get all the terminList
        restTerminMockMvc.perform(get("/api/termins?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(termin.getId().intValue())))
            .andExpect(jsonPath("$.[*].datum").value(hasItem(DEFAULT_DATUM.toString())))
            .andExpect(jsonPath("$.[*].notiz").value(hasItem(DEFAULT_NOTIZ.toString())));
    }
    
    @Test
    @Transactional
    public void getTermin() throws Exception {
        // Initialize the database
        terminRepository.saveAndFlush(termin);

        // Get the termin
        restTerminMockMvc.perform(get("/api/termins/{id}", termin.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(termin.getId().intValue()))
            .andExpect(jsonPath("$.datum").value(DEFAULT_DATUM.toString()))
            .andExpect(jsonPath("$.notiz").value(DEFAULT_NOTIZ.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTermin() throws Exception {
        // Get the termin
        restTerminMockMvc.perform(get("/api/termins/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTermin() throws Exception {
        // Initialize the database
        terminService.save(termin);

        int databaseSizeBeforeUpdate = terminRepository.findAll().size();

        // Update the termin
        Termin updatedTermin = terminRepository.findById(termin.getId()).get();
        // Disconnect from session so that the updates on updatedTermin are not directly saved in db
        em.detach(updatedTermin);
        updatedTermin
            .datum(UPDATED_DATUM)
            .notiz(UPDATED_NOTIZ);

        restTerminMockMvc.perform(put("/api/termins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTermin)))
            .andExpect(status().isOk());

        // Validate the Termin in the database
        List<Termin> terminList = terminRepository.findAll();
        assertThat(terminList).hasSize(databaseSizeBeforeUpdate);
        Termin testTermin = terminList.get(terminList.size() - 1);
        assertThat(testTermin.getDatum()).isEqualTo(UPDATED_DATUM);
        assertThat(testTermin.getNotiz()).isEqualTo(UPDATED_NOTIZ);
    }

    @Test
    @Transactional
    public void updateNonExistingTermin() throws Exception {
        int databaseSizeBeforeUpdate = terminRepository.findAll().size();

        // Create the Termin

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTerminMockMvc.perform(put("/api/termins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(termin)))
            .andExpect(status().isBadRequest());

        // Validate the Termin in the database
        List<Termin> terminList = terminRepository.findAll();
        assertThat(terminList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTermin() throws Exception {
        // Initialize the database
        terminService.save(termin);

        int databaseSizeBeforeDelete = terminRepository.findAll().size();

        // Delete the termin
        restTerminMockMvc.perform(delete("/api/termins/{id}", termin.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Termin> terminList = terminRepository.findAll();
        assertThat(terminList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Termin.class);
        Termin termin1 = new Termin();
        termin1.setId(1L);
        Termin termin2 = new Termin();
        termin2.setId(termin1.getId());
        assertThat(termin1).isEqualTo(termin2);
        termin2.setId(2L);
        assertThat(termin1).isNotEqualTo(termin2);
        termin1.setId(null);
        assertThat(termin1).isNotEqualTo(termin2);
    }
}
