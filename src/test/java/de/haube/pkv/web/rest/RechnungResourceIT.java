package de.haube.pkv.web.rest;

import de.haube.pkv.PkvApp;
import de.haube.pkv.domain.Rechnung;
import de.haube.pkv.repository.RechnungRepository;
import de.haube.pkv.service.RechnungService;
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
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static de.haube.pkv.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link RechnungResource} REST controller.
 */
@SpringBootTest(classes = PkvApp.class)
public class RechnungResourceIT {

    private static final BigDecimal DEFAULT_BETRAG = new BigDecimal(1);
    private static final BigDecimal UPDATED_BETRAG = new BigDecimal(2);

    private static final Instant DEFAULT_DATUM_RECHNUNG = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATUM_RECHNUNG = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATUM_ZAHLUNG = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATUM_ZAHLUNG = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private RechnungRepository rechnungRepository;

    @Autowired
    private RechnungService rechnungService;

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

    private MockMvc restRechnungMockMvc;

    private Rechnung rechnung;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RechnungResource rechnungResource = new RechnungResource(rechnungService);
        this.restRechnungMockMvc = MockMvcBuilders.standaloneSetup(rechnungResource)
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
    public static Rechnung createEntity(EntityManager em) {
        Rechnung rechnung = new Rechnung()
            .betrag(DEFAULT_BETRAG)
            .datumRechnung(DEFAULT_DATUM_RECHNUNG)
            .datumZahlung(DEFAULT_DATUM_ZAHLUNG);
        return rechnung;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rechnung createUpdatedEntity(EntityManager em) {
        Rechnung rechnung = new Rechnung()
            .betrag(UPDATED_BETRAG)
            .datumRechnung(UPDATED_DATUM_RECHNUNG)
            .datumZahlung(UPDATED_DATUM_ZAHLUNG);
        return rechnung;
    }

    @BeforeEach
    public void initTest() {
        rechnung = createEntity(em);
    }

    @Test
    @Transactional
    public void createRechnung() throws Exception {
        int databaseSizeBeforeCreate = rechnungRepository.findAll().size();

        // Create the Rechnung
        restRechnungMockMvc.perform(post("/api/rechnungs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rechnung)))
            .andExpect(status().isCreated());

        // Validate the Rechnung in the database
        List<Rechnung> rechnungList = rechnungRepository.findAll();
        assertThat(rechnungList).hasSize(databaseSizeBeforeCreate + 1);
        Rechnung testRechnung = rechnungList.get(rechnungList.size() - 1);
        assertThat(testRechnung.getBetrag()).isEqualTo(DEFAULT_BETRAG);
        assertThat(testRechnung.getDatumRechnung()).isEqualTo(DEFAULT_DATUM_RECHNUNG);
        assertThat(testRechnung.getDatumZahlung()).isEqualTo(DEFAULT_DATUM_ZAHLUNG);
    }

    @Test
    @Transactional
    public void createRechnungWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = rechnungRepository.findAll().size();

        // Create the Rechnung with an existing ID
        rechnung.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRechnungMockMvc.perform(post("/api/rechnungs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rechnung)))
            .andExpect(status().isBadRequest());

        // Validate the Rechnung in the database
        List<Rechnung> rechnungList = rechnungRepository.findAll();
        assertThat(rechnungList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllRechnungs() throws Exception {
        // Initialize the database
        rechnungRepository.saveAndFlush(rechnung);

        // Get all the rechnungList
        restRechnungMockMvc.perform(get("/api/rechnungs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rechnung.getId().intValue())))
            .andExpect(jsonPath("$.[*].betrag").value(hasItem(DEFAULT_BETRAG.intValue())))
            .andExpect(jsonPath("$.[*].datumRechnung").value(hasItem(DEFAULT_DATUM_RECHNUNG.toString())))
            .andExpect(jsonPath("$.[*].datumZahlung").value(hasItem(DEFAULT_DATUM_ZAHLUNG.toString())));
    }
    
    @Test
    @Transactional
    public void getRechnung() throws Exception {
        // Initialize the database
        rechnungRepository.saveAndFlush(rechnung);

        // Get the rechnung
        restRechnungMockMvc.perform(get("/api/rechnungs/{id}", rechnung.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(rechnung.getId().intValue()))
            .andExpect(jsonPath("$.betrag").value(DEFAULT_BETRAG.intValue()))
            .andExpect(jsonPath("$.datumRechnung").value(DEFAULT_DATUM_RECHNUNG.toString()))
            .andExpect(jsonPath("$.datumZahlung").value(DEFAULT_DATUM_ZAHLUNG.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRechnung() throws Exception {
        // Get the rechnung
        restRechnungMockMvc.perform(get("/api/rechnungs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRechnung() throws Exception {
        // Initialize the database
        rechnungService.save(rechnung);

        int databaseSizeBeforeUpdate = rechnungRepository.findAll().size();

        // Update the rechnung
        Rechnung updatedRechnung = rechnungRepository.findById(rechnung.getId()).get();
        // Disconnect from session so that the updates on updatedRechnung are not directly saved in db
        em.detach(updatedRechnung);
        updatedRechnung
            .betrag(UPDATED_BETRAG)
            .datumRechnung(UPDATED_DATUM_RECHNUNG)
            .datumZahlung(UPDATED_DATUM_ZAHLUNG);

        restRechnungMockMvc.perform(put("/api/rechnungs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRechnung)))
            .andExpect(status().isOk());

        // Validate the Rechnung in the database
        List<Rechnung> rechnungList = rechnungRepository.findAll();
        assertThat(rechnungList).hasSize(databaseSizeBeforeUpdate);
        Rechnung testRechnung = rechnungList.get(rechnungList.size() - 1);
        assertThat(testRechnung.getBetrag()).isEqualTo(UPDATED_BETRAG);
        assertThat(testRechnung.getDatumRechnung()).isEqualTo(UPDATED_DATUM_RECHNUNG);
        assertThat(testRechnung.getDatumZahlung()).isEqualTo(UPDATED_DATUM_ZAHLUNG);
    }

    @Test
    @Transactional
    public void updateNonExistingRechnung() throws Exception {
        int databaseSizeBeforeUpdate = rechnungRepository.findAll().size();

        // Create the Rechnung

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRechnungMockMvc.perform(put("/api/rechnungs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rechnung)))
            .andExpect(status().isBadRequest());

        // Validate the Rechnung in the database
        List<Rechnung> rechnungList = rechnungRepository.findAll();
        assertThat(rechnungList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRechnung() throws Exception {
        // Initialize the database
        rechnungService.save(rechnung);

        int databaseSizeBeforeDelete = rechnungRepository.findAll().size();

        // Delete the rechnung
        restRechnungMockMvc.perform(delete("/api/rechnungs/{id}", rechnung.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Rechnung> rechnungList = rechnungRepository.findAll();
        assertThat(rechnungList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Rechnung.class);
        Rechnung rechnung1 = new Rechnung();
        rechnung1.setId(1L);
        Rechnung rechnung2 = new Rechnung();
        rechnung2.setId(rechnung1.getId());
        assertThat(rechnung1).isEqualTo(rechnung2);
        rechnung2.setId(2L);
        assertThat(rechnung1).isNotEqualTo(rechnung2);
        rechnung1.setId(null);
        assertThat(rechnung1).isNotEqualTo(rechnung2);
    }
}
