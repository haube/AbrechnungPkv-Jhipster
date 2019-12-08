package de.haube.pkv.web.rest;

import de.haube.pkv.PkvApp;
import de.haube.pkv.domain.Arzt;
import de.haube.pkv.repository.ArztRepository;
import de.haube.pkv.service.ArztService;
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
import java.util.List;

import static de.haube.pkv.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link ArztResource} REST controller.
 */
@SpringBootTest(classes = PkvApp.class)
public class ArztResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_STRASSE = "AAAAAAAAAA";
    private static final String UPDATED_STRASSE = "BBBBBBBBBB";

    private static final String DEFAULT_HAUSNUMMER = "AAAAAAAAAA";
    private static final String UPDATED_HAUSNUMMER = "BBBBBBBBBB";

    private static final String DEFAULT_PLZ = "AAAAAAAAAA";
    private static final String UPDATED_PLZ = "BBBBBBBBBB";

    private static final String DEFAULT_ORT = "AAAAAAAAAA";
    private static final String UPDATED_ORT = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFON = "AAAAAAAAAA";
    private static final String UPDATED_TELEFON = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFON_2 = "AAAAAAAAAA";
    private static final String UPDATED_TELEFON_2 = "BBBBBBBBBB";

    private static final String DEFAULT_FAX = "AAAAAAAAAA";
    private static final String UPDATED_FAX = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_WEB = "AAAAAAAAAA";
    private static final String UPDATED_WEB = "BBBBBBBBBB";

    @Autowired
    private ArztRepository arztRepository;

    @Autowired
    private ArztService arztService;

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

    private MockMvc restArztMockMvc;

    private Arzt arzt;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ArztResource arztResource = new ArztResource(arztService);
        this.restArztMockMvc = MockMvcBuilders.standaloneSetup(arztResource)
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
    public static Arzt createEntity(EntityManager em) {
        Arzt arzt = new Arzt()
            .name(DEFAULT_NAME)
            .strasse(DEFAULT_STRASSE)
            .hausnummer(DEFAULT_HAUSNUMMER)
            .plz(DEFAULT_PLZ)
            .ort(DEFAULT_ORT)
            .telefon(DEFAULT_TELEFON)
            .telefon2(DEFAULT_TELEFON_2)
            .fax(DEFAULT_FAX)
            .email(DEFAULT_EMAIL)
            .web(DEFAULT_WEB);
        return arzt;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Arzt createUpdatedEntity(EntityManager em) {
        Arzt arzt = new Arzt()
            .name(UPDATED_NAME)
            .strasse(UPDATED_STRASSE)
            .hausnummer(UPDATED_HAUSNUMMER)
            .plz(UPDATED_PLZ)
            .ort(UPDATED_ORT)
            .telefon(UPDATED_TELEFON)
            .telefon2(UPDATED_TELEFON_2)
            .fax(UPDATED_FAX)
            .email(UPDATED_EMAIL)
            .web(UPDATED_WEB);
        return arzt;
    }

    @BeforeEach
    public void initTest() {
        arzt = createEntity(em);
    }

    @Test
    @Transactional
    public void createArzt() throws Exception {
        int databaseSizeBeforeCreate = arztRepository.findAll().size();

        // Create the Arzt
        restArztMockMvc.perform(post("/api/arzts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(arzt)))
            .andExpect(status().isCreated());

        // Validate the Arzt in the database
        List<Arzt> arztList = arztRepository.findAll();
        assertThat(arztList).hasSize(databaseSizeBeforeCreate + 1);
        Arzt testArzt = arztList.get(arztList.size() - 1);
        assertThat(testArzt.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testArzt.getStrasse()).isEqualTo(DEFAULT_STRASSE);
        assertThat(testArzt.getHausnummer()).isEqualTo(DEFAULT_HAUSNUMMER);
        assertThat(testArzt.getPlz()).isEqualTo(DEFAULT_PLZ);
        assertThat(testArzt.getOrt()).isEqualTo(DEFAULT_ORT);
        assertThat(testArzt.getTelefon()).isEqualTo(DEFAULT_TELEFON);
        assertThat(testArzt.getTelefon2()).isEqualTo(DEFAULT_TELEFON_2);
        assertThat(testArzt.getFax()).isEqualTo(DEFAULT_FAX);
        assertThat(testArzt.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testArzt.getWeb()).isEqualTo(DEFAULT_WEB);
    }

    @Test
    @Transactional
    public void createArztWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = arztRepository.findAll().size();

        // Create the Arzt with an existing ID
        arzt.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restArztMockMvc.perform(post("/api/arzts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(arzt)))
            .andExpect(status().isBadRequest());

        // Validate the Arzt in the database
        List<Arzt> arztList = arztRepository.findAll();
        assertThat(arztList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = arztRepository.findAll().size();
        // set the field null
        arzt.setName(null);

        // Create the Arzt, which fails.

        restArztMockMvc.perform(post("/api/arzts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(arzt)))
            .andExpect(status().isBadRequest());

        List<Arzt> arztList = arztRepository.findAll();
        assertThat(arztList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllArzts() throws Exception {
        // Initialize the database
        arztRepository.saveAndFlush(arzt);

        // Get all the arztList
        restArztMockMvc.perform(get("/api/arzts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(arzt.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].strasse").value(hasItem(DEFAULT_STRASSE.toString())))
            .andExpect(jsonPath("$.[*].hausnummer").value(hasItem(DEFAULT_HAUSNUMMER.toString())))
            .andExpect(jsonPath("$.[*].plz").value(hasItem(DEFAULT_PLZ.toString())))
            .andExpect(jsonPath("$.[*].ort").value(hasItem(DEFAULT_ORT.toString())))
            .andExpect(jsonPath("$.[*].telefon").value(hasItem(DEFAULT_TELEFON.toString())))
            .andExpect(jsonPath("$.[*].telefon2").value(hasItem(DEFAULT_TELEFON_2.toString())))
            .andExpect(jsonPath("$.[*].fax").value(hasItem(DEFAULT_FAX.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].web").value(hasItem(DEFAULT_WEB.toString())));
    }
    
    @Test
    @Transactional
    public void getArzt() throws Exception {
        // Initialize the database
        arztRepository.saveAndFlush(arzt);

        // Get the arzt
        restArztMockMvc.perform(get("/api/arzts/{id}", arzt.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(arzt.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.strasse").value(DEFAULT_STRASSE.toString()))
            .andExpect(jsonPath("$.hausnummer").value(DEFAULT_HAUSNUMMER.toString()))
            .andExpect(jsonPath("$.plz").value(DEFAULT_PLZ.toString()))
            .andExpect(jsonPath("$.ort").value(DEFAULT_ORT.toString()))
            .andExpect(jsonPath("$.telefon").value(DEFAULT_TELEFON.toString()))
            .andExpect(jsonPath("$.telefon2").value(DEFAULT_TELEFON_2.toString()))
            .andExpect(jsonPath("$.fax").value(DEFAULT_FAX.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.web").value(DEFAULT_WEB.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingArzt() throws Exception {
        // Get the arzt
        restArztMockMvc.perform(get("/api/arzts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateArzt() throws Exception {
        // Initialize the database
        arztService.save(arzt);

        int databaseSizeBeforeUpdate = arztRepository.findAll().size();

        // Update the arzt
        Arzt updatedArzt = arztRepository.findById(arzt.getId()).get();
        // Disconnect from session so that the updates on updatedArzt are not directly saved in db
        em.detach(updatedArzt);
        updatedArzt
            .name(UPDATED_NAME)
            .strasse(UPDATED_STRASSE)
            .hausnummer(UPDATED_HAUSNUMMER)
            .plz(UPDATED_PLZ)
            .ort(UPDATED_ORT)
            .telefon(UPDATED_TELEFON)
            .telefon2(UPDATED_TELEFON_2)
            .fax(UPDATED_FAX)
            .email(UPDATED_EMAIL)
            .web(UPDATED_WEB);

        restArztMockMvc.perform(put("/api/arzts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedArzt)))
            .andExpect(status().isOk());

        // Validate the Arzt in the database
        List<Arzt> arztList = arztRepository.findAll();
        assertThat(arztList).hasSize(databaseSizeBeforeUpdate);
        Arzt testArzt = arztList.get(arztList.size() - 1);
        assertThat(testArzt.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testArzt.getStrasse()).isEqualTo(UPDATED_STRASSE);
        assertThat(testArzt.getHausnummer()).isEqualTo(UPDATED_HAUSNUMMER);
        assertThat(testArzt.getPlz()).isEqualTo(UPDATED_PLZ);
        assertThat(testArzt.getOrt()).isEqualTo(UPDATED_ORT);
        assertThat(testArzt.getTelefon()).isEqualTo(UPDATED_TELEFON);
        assertThat(testArzt.getTelefon2()).isEqualTo(UPDATED_TELEFON_2);
        assertThat(testArzt.getFax()).isEqualTo(UPDATED_FAX);
        assertThat(testArzt.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testArzt.getWeb()).isEqualTo(UPDATED_WEB);
    }

    @Test
    @Transactional
    public void updateNonExistingArzt() throws Exception {
        int databaseSizeBeforeUpdate = arztRepository.findAll().size();

        // Create the Arzt

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArztMockMvc.perform(put("/api/arzts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(arzt)))
            .andExpect(status().isBadRequest());

        // Validate the Arzt in the database
        List<Arzt> arztList = arztRepository.findAll();
        assertThat(arztList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteArzt() throws Exception {
        // Initialize the database
        arztService.save(arzt);

        int databaseSizeBeforeDelete = arztRepository.findAll().size();

        // Delete the arzt
        restArztMockMvc.perform(delete("/api/arzts/{id}", arzt.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Arzt> arztList = arztRepository.findAll();
        assertThat(arztList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Arzt.class);
        Arzt arzt1 = new Arzt();
        arzt1.setId(1L);
        Arzt arzt2 = new Arzt();
        arzt2.setId(arzt1.getId());
        assertThat(arzt1).isEqualTo(arzt2);
        arzt2.setId(2L);
        assertThat(arzt1).isNotEqualTo(arzt2);
        arzt1.setId(null);
        assertThat(arzt1).isNotEqualTo(arzt2);
    }
}
