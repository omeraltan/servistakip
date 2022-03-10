package com.servistakip.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.servistakip.app.IntegrationTest;
import com.servistakip.app.domain.KlasorTemel;
import com.servistakip.app.repository.KlasorTemelRepository;
import com.servistakip.app.service.dto.KlasorTemelDTO;
import com.servistakip.app.service.mapper.KlasorTemelMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link KlasorTemelResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class KlasorTemelResourceIT {

    private static final String DEFAULT_ODASI = "AAAAAAAAAA";
    private static final String UPDATED_ODASI = "BBBBBBBBBB";

    private static final String DEFAULT_DOLABI = "AAAAAAAAAA";
    private static final String UPDATED_DOLABI = "BBBBBBBBBB";

    private static final String DEFAULT_KLASORU = "AAAAAAAAAA";
    private static final String UPDATED_KLASORU = "BBBBBBBBBB";

    private static final String DEFAULT_FIHRIST_SIRASI = "AAAAAAAAAA";
    private static final String UPDATED_FIHRIST_SIRASI = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/klasor-temels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private KlasorTemelRepository klasorTemelRepository;

    @Autowired
    private KlasorTemelMapper klasorTemelMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restKlasorTemelMockMvc;

    private KlasorTemel klasorTemel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static KlasorTemel createEntity(EntityManager em) {
        KlasorTemel klasorTemel = new KlasorTemel()
            .odasi(DEFAULT_ODASI)
            .dolabi(DEFAULT_DOLABI)
            .klasoru(DEFAULT_KLASORU)
            .fihristSirasi(DEFAULT_FIHRIST_SIRASI);
        return klasorTemel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static KlasorTemel createUpdatedEntity(EntityManager em) {
        KlasorTemel klasorTemel = new KlasorTemel()
            .odasi(UPDATED_ODASI)
            .dolabi(UPDATED_DOLABI)
            .klasoru(UPDATED_KLASORU)
            .fihristSirasi(UPDATED_FIHRIST_SIRASI);
        return klasorTemel;
    }

    @BeforeEach
    public void initTest() {
        klasorTemel = createEntity(em);
    }

    @Test
    @Transactional
    void createKlasorTemel() throws Exception {
        int databaseSizeBeforeCreate = klasorTemelRepository.findAll().size();
        // Create the KlasorTemel
        KlasorTemelDTO klasorTemelDTO = klasorTemelMapper.toDto(klasorTemel);
        restKlasorTemelMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(klasorTemelDTO))
            )
            .andExpect(status().isCreated());

        // Validate the KlasorTemel in the database
        List<KlasorTemel> klasorTemelList = klasorTemelRepository.findAll();
        assertThat(klasorTemelList).hasSize(databaseSizeBeforeCreate + 1);
        KlasorTemel testKlasorTemel = klasorTemelList.get(klasorTemelList.size() - 1);
        assertThat(testKlasorTemel.getOdasi()).isEqualTo(DEFAULT_ODASI);
        assertThat(testKlasorTemel.getDolabi()).isEqualTo(DEFAULT_DOLABI);
        assertThat(testKlasorTemel.getKlasoru()).isEqualTo(DEFAULT_KLASORU);
        assertThat(testKlasorTemel.getFihristSirasi()).isEqualTo(DEFAULT_FIHRIST_SIRASI);
    }

    @Test
    @Transactional
    void createKlasorTemelWithExistingId() throws Exception {
        // Create the KlasorTemel with an existing ID
        klasorTemel.setId(1L);
        KlasorTemelDTO klasorTemelDTO = klasorTemelMapper.toDto(klasorTemel);

        int databaseSizeBeforeCreate = klasorTemelRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restKlasorTemelMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(klasorTemelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the KlasorTemel in the database
        List<KlasorTemel> klasorTemelList = klasorTemelRepository.findAll();
        assertThat(klasorTemelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkOdasiIsRequired() throws Exception {
        int databaseSizeBeforeTest = klasorTemelRepository.findAll().size();
        // set the field null
        klasorTemel.setOdasi(null);

        // Create the KlasorTemel, which fails.
        KlasorTemelDTO klasorTemelDTO = klasorTemelMapper.toDto(klasorTemel);

        restKlasorTemelMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(klasorTemelDTO))
            )
            .andExpect(status().isBadRequest());

        List<KlasorTemel> klasorTemelList = klasorTemelRepository.findAll();
        assertThat(klasorTemelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDolabiIsRequired() throws Exception {
        int databaseSizeBeforeTest = klasorTemelRepository.findAll().size();
        // set the field null
        klasorTemel.setDolabi(null);

        // Create the KlasorTemel, which fails.
        KlasorTemelDTO klasorTemelDTO = klasorTemelMapper.toDto(klasorTemel);

        restKlasorTemelMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(klasorTemelDTO))
            )
            .andExpect(status().isBadRequest());

        List<KlasorTemel> klasorTemelList = klasorTemelRepository.findAll();
        assertThat(klasorTemelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkKlasoruIsRequired() throws Exception {
        int databaseSizeBeforeTest = klasorTemelRepository.findAll().size();
        // set the field null
        klasorTemel.setKlasoru(null);

        // Create the KlasorTemel, which fails.
        KlasorTemelDTO klasorTemelDTO = klasorTemelMapper.toDto(klasorTemel);

        restKlasorTemelMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(klasorTemelDTO))
            )
            .andExpect(status().isBadRequest());

        List<KlasorTemel> klasorTemelList = klasorTemelRepository.findAll();
        assertThat(klasorTemelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFihristSirasiIsRequired() throws Exception {
        int databaseSizeBeforeTest = klasorTemelRepository.findAll().size();
        // set the field null
        klasorTemel.setFihristSirasi(null);

        // Create the KlasorTemel, which fails.
        KlasorTemelDTO klasorTemelDTO = klasorTemelMapper.toDto(klasorTemel);

        restKlasorTemelMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(klasorTemelDTO))
            )
            .andExpect(status().isBadRequest());

        List<KlasorTemel> klasorTemelList = klasorTemelRepository.findAll();
        assertThat(klasorTemelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllKlasorTemels() throws Exception {
        // Initialize the database
        klasorTemelRepository.saveAndFlush(klasorTemel);

        // Get all the klasorTemelList
        restKlasorTemelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(klasorTemel.getId().intValue())))
            .andExpect(jsonPath("$.[*].odasi").value(hasItem(DEFAULT_ODASI)))
            .andExpect(jsonPath("$.[*].dolabi").value(hasItem(DEFAULT_DOLABI)))
            .andExpect(jsonPath("$.[*].klasoru").value(hasItem(DEFAULT_KLASORU)))
            .andExpect(jsonPath("$.[*].fihristSirasi").value(hasItem(DEFAULT_FIHRIST_SIRASI)));
    }

    @Test
    @Transactional
    void getKlasorTemel() throws Exception {
        // Initialize the database
        klasorTemelRepository.saveAndFlush(klasorTemel);

        // Get the klasorTemel
        restKlasorTemelMockMvc
            .perform(get(ENTITY_API_URL_ID, klasorTemel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(klasorTemel.getId().intValue()))
            .andExpect(jsonPath("$.odasi").value(DEFAULT_ODASI))
            .andExpect(jsonPath("$.dolabi").value(DEFAULT_DOLABI))
            .andExpect(jsonPath("$.klasoru").value(DEFAULT_KLASORU))
            .andExpect(jsonPath("$.fihristSirasi").value(DEFAULT_FIHRIST_SIRASI));
    }

    @Test
    @Transactional
    void getNonExistingKlasorTemel() throws Exception {
        // Get the klasorTemel
        restKlasorTemelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewKlasorTemel() throws Exception {
        // Initialize the database
        klasorTemelRepository.saveAndFlush(klasorTemel);

        int databaseSizeBeforeUpdate = klasorTemelRepository.findAll().size();

        // Update the klasorTemel
        KlasorTemel updatedKlasorTemel = klasorTemelRepository.findById(klasorTemel.getId()).get();
        // Disconnect from session so that the updates on updatedKlasorTemel are not directly saved in db
        em.detach(updatedKlasorTemel);
        updatedKlasorTemel.odasi(UPDATED_ODASI).dolabi(UPDATED_DOLABI).klasoru(UPDATED_KLASORU).fihristSirasi(UPDATED_FIHRIST_SIRASI);
        KlasorTemelDTO klasorTemelDTO = klasorTemelMapper.toDto(updatedKlasorTemel);

        restKlasorTemelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, klasorTemelDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(klasorTemelDTO))
            )
            .andExpect(status().isOk());

        // Validate the KlasorTemel in the database
        List<KlasorTemel> klasorTemelList = klasorTemelRepository.findAll();
        assertThat(klasorTemelList).hasSize(databaseSizeBeforeUpdate);
        KlasorTemel testKlasorTemel = klasorTemelList.get(klasorTemelList.size() - 1);
        assertThat(testKlasorTemel.getOdasi()).isEqualTo(UPDATED_ODASI);
        assertThat(testKlasorTemel.getDolabi()).isEqualTo(UPDATED_DOLABI);
        assertThat(testKlasorTemel.getKlasoru()).isEqualTo(UPDATED_KLASORU);
        assertThat(testKlasorTemel.getFihristSirasi()).isEqualTo(UPDATED_FIHRIST_SIRASI);
    }

    @Test
    @Transactional
    void putNonExistingKlasorTemel() throws Exception {
        int databaseSizeBeforeUpdate = klasorTemelRepository.findAll().size();
        klasorTemel.setId(count.incrementAndGet());

        // Create the KlasorTemel
        KlasorTemelDTO klasorTemelDTO = klasorTemelMapper.toDto(klasorTemel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKlasorTemelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, klasorTemelDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(klasorTemelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the KlasorTemel in the database
        List<KlasorTemel> klasorTemelList = klasorTemelRepository.findAll();
        assertThat(klasorTemelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchKlasorTemel() throws Exception {
        int databaseSizeBeforeUpdate = klasorTemelRepository.findAll().size();
        klasorTemel.setId(count.incrementAndGet());

        // Create the KlasorTemel
        KlasorTemelDTO klasorTemelDTO = klasorTemelMapper.toDto(klasorTemel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKlasorTemelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(klasorTemelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the KlasorTemel in the database
        List<KlasorTemel> klasorTemelList = klasorTemelRepository.findAll();
        assertThat(klasorTemelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamKlasorTemel() throws Exception {
        int databaseSizeBeforeUpdate = klasorTemelRepository.findAll().size();
        klasorTemel.setId(count.incrementAndGet());

        // Create the KlasorTemel
        KlasorTemelDTO klasorTemelDTO = klasorTemelMapper.toDto(klasorTemel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKlasorTemelMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(klasorTemelDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the KlasorTemel in the database
        List<KlasorTemel> klasorTemelList = klasorTemelRepository.findAll();
        assertThat(klasorTemelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateKlasorTemelWithPatch() throws Exception {
        // Initialize the database
        klasorTemelRepository.saveAndFlush(klasorTemel);

        int databaseSizeBeforeUpdate = klasorTemelRepository.findAll().size();

        // Update the klasorTemel using partial update
        KlasorTemel partialUpdatedKlasorTemel = new KlasorTemel();
        partialUpdatedKlasorTemel.setId(klasorTemel.getId());

        partialUpdatedKlasorTemel.odasi(UPDATED_ODASI).dolabi(UPDATED_DOLABI);

        restKlasorTemelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKlasorTemel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedKlasorTemel))
            )
            .andExpect(status().isOk());

        // Validate the KlasorTemel in the database
        List<KlasorTemel> klasorTemelList = klasorTemelRepository.findAll();
        assertThat(klasorTemelList).hasSize(databaseSizeBeforeUpdate);
        KlasorTemel testKlasorTemel = klasorTemelList.get(klasorTemelList.size() - 1);
        assertThat(testKlasorTemel.getOdasi()).isEqualTo(UPDATED_ODASI);
        assertThat(testKlasorTemel.getDolabi()).isEqualTo(UPDATED_DOLABI);
        assertThat(testKlasorTemel.getKlasoru()).isEqualTo(DEFAULT_KLASORU);
        assertThat(testKlasorTemel.getFihristSirasi()).isEqualTo(DEFAULT_FIHRIST_SIRASI);
    }

    @Test
    @Transactional
    void fullUpdateKlasorTemelWithPatch() throws Exception {
        // Initialize the database
        klasorTemelRepository.saveAndFlush(klasorTemel);

        int databaseSizeBeforeUpdate = klasorTemelRepository.findAll().size();

        // Update the klasorTemel using partial update
        KlasorTemel partialUpdatedKlasorTemel = new KlasorTemel();
        partialUpdatedKlasorTemel.setId(klasorTemel.getId());

        partialUpdatedKlasorTemel
            .odasi(UPDATED_ODASI)
            .dolabi(UPDATED_DOLABI)
            .klasoru(UPDATED_KLASORU)
            .fihristSirasi(UPDATED_FIHRIST_SIRASI);

        restKlasorTemelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKlasorTemel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedKlasorTemel))
            )
            .andExpect(status().isOk());

        // Validate the KlasorTemel in the database
        List<KlasorTemel> klasorTemelList = klasorTemelRepository.findAll();
        assertThat(klasorTemelList).hasSize(databaseSizeBeforeUpdate);
        KlasorTemel testKlasorTemel = klasorTemelList.get(klasorTemelList.size() - 1);
        assertThat(testKlasorTemel.getOdasi()).isEqualTo(UPDATED_ODASI);
        assertThat(testKlasorTemel.getDolabi()).isEqualTo(UPDATED_DOLABI);
        assertThat(testKlasorTemel.getKlasoru()).isEqualTo(UPDATED_KLASORU);
        assertThat(testKlasorTemel.getFihristSirasi()).isEqualTo(UPDATED_FIHRIST_SIRASI);
    }

    @Test
    @Transactional
    void patchNonExistingKlasorTemel() throws Exception {
        int databaseSizeBeforeUpdate = klasorTemelRepository.findAll().size();
        klasorTemel.setId(count.incrementAndGet());

        // Create the KlasorTemel
        KlasorTemelDTO klasorTemelDTO = klasorTemelMapper.toDto(klasorTemel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKlasorTemelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, klasorTemelDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(klasorTemelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the KlasorTemel in the database
        List<KlasorTemel> klasorTemelList = klasorTemelRepository.findAll();
        assertThat(klasorTemelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchKlasorTemel() throws Exception {
        int databaseSizeBeforeUpdate = klasorTemelRepository.findAll().size();
        klasorTemel.setId(count.incrementAndGet());

        // Create the KlasorTemel
        KlasorTemelDTO klasorTemelDTO = klasorTemelMapper.toDto(klasorTemel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKlasorTemelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(klasorTemelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the KlasorTemel in the database
        List<KlasorTemel> klasorTemelList = klasorTemelRepository.findAll();
        assertThat(klasorTemelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamKlasorTemel() throws Exception {
        int databaseSizeBeforeUpdate = klasorTemelRepository.findAll().size();
        klasorTemel.setId(count.incrementAndGet());

        // Create the KlasorTemel
        KlasorTemelDTO klasorTemelDTO = klasorTemelMapper.toDto(klasorTemel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKlasorTemelMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(klasorTemelDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the KlasorTemel in the database
        List<KlasorTemel> klasorTemelList = klasorTemelRepository.findAll();
        assertThat(klasorTemelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteKlasorTemel() throws Exception {
        // Initialize the database
        klasorTemelRepository.saveAndFlush(klasorTemel);

        int databaseSizeBeforeDelete = klasorTemelRepository.findAll().size();

        // Delete the klasorTemel
        restKlasorTemelMockMvc
            .perform(delete(ENTITY_API_URL_ID, klasorTemel.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<KlasorTemel> klasorTemelList = klasorTemelRepository.findAll();
        assertThat(klasorTemelList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
