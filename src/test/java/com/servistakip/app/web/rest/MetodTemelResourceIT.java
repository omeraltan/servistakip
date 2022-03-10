package com.servistakip.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.servistakip.app.IntegrationTest;
import com.servistakip.app.domain.MetodTemel;
import com.servistakip.app.repository.MetodTemelRepository;
import com.servistakip.app.service.dto.MetodTemelDTO;
import com.servistakip.app.service.mapper.MetodTemelMapper;
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
 * Integration tests for the {@link MetodTemelResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MetodTemelResourceIT {

    private static final String DEFAULT_METOD_ADI = "AAAAAAAAAA";
    private static final String UPDATED_METOD_ADI = "BBBBBBBBBB";

    private static final Integer DEFAULT_METOD_NU = 1;
    private static final Integer UPDATED_METOD_NU = 2;

    private static final String DEFAULT_METOD_ACIKLAMASI = "AAAAAAAAAA";
    private static final String UPDATED_METOD_ACIKLAMASI = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/metod-temels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MetodTemelRepository metodTemelRepository;

    @Autowired
    private MetodTemelMapper metodTemelMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMetodTemelMockMvc;

    private MetodTemel metodTemel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MetodTemel createEntity(EntityManager em) {
        MetodTemel metodTemel = new MetodTemel()
            .metodAdi(DEFAULT_METOD_ADI)
            .metodNu(DEFAULT_METOD_NU)
            .metodAciklamasi(DEFAULT_METOD_ACIKLAMASI);
        return metodTemel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MetodTemel createUpdatedEntity(EntityManager em) {
        MetodTemel metodTemel = new MetodTemel()
            .metodAdi(UPDATED_METOD_ADI)
            .metodNu(UPDATED_METOD_NU)
            .metodAciklamasi(UPDATED_METOD_ACIKLAMASI);
        return metodTemel;
    }

    @BeforeEach
    public void initTest() {
        metodTemel = createEntity(em);
    }

    @Test
    @Transactional
    void createMetodTemel() throws Exception {
        int databaseSizeBeforeCreate = metodTemelRepository.findAll().size();
        // Create the MetodTemel
        MetodTemelDTO metodTemelDTO = metodTemelMapper.toDto(metodTemel);
        restMetodTemelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(metodTemelDTO)))
            .andExpect(status().isCreated());

        // Validate the MetodTemel in the database
        List<MetodTemel> metodTemelList = metodTemelRepository.findAll();
        assertThat(metodTemelList).hasSize(databaseSizeBeforeCreate + 1);
        MetodTemel testMetodTemel = metodTemelList.get(metodTemelList.size() - 1);
        assertThat(testMetodTemel.getMetodAdi()).isEqualTo(DEFAULT_METOD_ADI);
        assertThat(testMetodTemel.getMetodNu()).isEqualTo(DEFAULT_METOD_NU);
        assertThat(testMetodTemel.getMetodAciklamasi()).isEqualTo(DEFAULT_METOD_ACIKLAMASI);
    }

    @Test
    @Transactional
    void createMetodTemelWithExistingId() throws Exception {
        // Create the MetodTemel with an existing ID
        metodTemel.setId(1L);
        MetodTemelDTO metodTemelDTO = metodTemelMapper.toDto(metodTemel);

        int databaseSizeBeforeCreate = metodTemelRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMetodTemelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(metodTemelDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MetodTemel in the database
        List<MetodTemel> metodTemelList = metodTemelRepository.findAll();
        assertThat(metodTemelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkMetodAdiIsRequired() throws Exception {
        int databaseSizeBeforeTest = metodTemelRepository.findAll().size();
        // set the field null
        metodTemel.setMetodAdi(null);

        // Create the MetodTemel, which fails.
        MetodTemelDTO metodTemelDTO = metodTemelMapper.toDto(metodTemel);

        restMetodTemelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(metodTemelDTO)))
            .andExpect(status().isBadRequest());

        List<MetodTemel> metodTemelList = metodTemelRepository.findAll();
        assertThat(metodTemelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMetodNuIsRequired() throws Exception {
        int databaseSizeBeforeTest = metodTemelRepository.findAll().size();
        // set the field null
        metodTemel.setMetodNu(null);

        // Create the MetodTemel, which fails.
        MetodTemelDTO metodTemelDTO = metodTemelMapper.toDto(metodTemel);

        restMetodTemelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(metodTemelDTO)))
            .andExpect(status().isBadRequest());

        List<MetodTemel> metodTemelList = metodTemelRepository.findAll();
        assertThat(metodTemelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMetodAciklamasiIsRequired() throws Exception {
        int databaseSizeBeforeTest = metodTemelRepository.findAll().size();
        // set the field null
        metodTemel.setMetodAciklamasi(null);

        // Create the MetodTemel, which fails.
        MetodTemelDTO metodTemelDTO = metodTemelMapper.toDto(metodTemel);

        restMetodTemelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(metodTemelDTO)))
            .andExpect(status().isBadRequest());

        List<MetodTemel> metodTemelList = metodTemelRepository.findAll();
        assertThat(metodTemelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMetodTemels() throws Exception {
        // Initialize the database
        metodTemelRepository.saveAndFlush(metodTemel);

        // Get all the metodTemelList
        restMetodTemelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(metodTemel.getId().intValue())))
            .andExpect(jsonPath("$.[*].metodAdi").value(hasItem(DEFAULT_METOD_ADI)))
            .andExpect(jsonPath("$.[*].metodNu").value(hasItem(DEFAULT_METOD_NU)))
            .andExpect(jsonPath("$.[*].metodAciklamasi").value(hasItem(DEFAULT_METOD_ACIKLAMASI)));
    }

    @Test
    @Transactional
    void getMetodTemel() throws Exception {
        // Initialize the database
        metodTemelRepository.saveAndFlush(metodTemel);

        // Get the metodTemel
        restMetodTemelMockMvc
            .perform(get(ENTITY_API_URL_ID, metodTemel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(metodTemel.getId().intValue()))
            .andExpect(jsonPath("$.metodAdi").value(DEFAULT_METOD_ADI))
            .andExpect(jsonPath("$.metodNu").value(DEFAULT_METOD_NU))
            .andExpect(jsonPath("$.metodAciklamasi").value(DEFAULT_METOD_ACIKLAMASI));
    }

    @Test
    @Transactional
    void getNonExistingMetodTemel() throws Exception {
        // Get the metodTemel
        restMetodTemelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewMetodTemel() throws Exception {
        // Initialize the database
        metodTemelRepository.saveAndFlush(metodTemel);

        int databaseSizeBeforeUpdate = metodTemelRepository.findAll().size();

        // Update the metodTemel
        MetodTemel updatedMetodTemel = metodTemelRepository.findById(metodTemel.getId()).get();
        // Disconnect from session so that the updates on updatedMetodTemel are not directly saved in db
        em.detach(updatedMetodTemel);
        updatedMetodTemel.metodAdi(UPDATED_METOD_ADI).metodNu(UPDATED_METOD_NU).metodAciklamasi(UPDATED_METOD_ACIKLAMASI);
        MetodTemelDTO metodTemelDTO = metodTemelMapper.toDto(updatedMetodTemel);

        restMetodTemelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, metodTemelDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(metodTemelDTO))
            )
            .andExpect(status().isOk());

        // Validate the MetodTemel in the database
        List<MetodTemel> metodTemelList = metodTemelRepository.findAll();
        assertThat(metodTemelList).hasSize(databaseSizeBeforeUpdate);
        MetodTemel testMetodTemel = metodTemelList.get(metodTemelList.size() - 1);
        assertThat(testMetodTemel.getMetodAdi()).isEqualTo(UPDATED_METOD_ADI);
        assertThat(testMetodTemel.getMetodNu()).isEqualTo(UPDATED_METOD_NU);
        assertThat(testMetodTemel.getMetodAciklamasi()).isEqualTo(UPDATED_METOD_ACIKLAMASI);
    }

    @Test
    @Transactional
    void putNonExistingMetodTemel() throws Exception {
        int databaseSizeBeforeUpdate = metodTemelRepository.findAll().size();
        metodTemel.setId(count.incrementAndGet());

        // Create the MetodTemel
        MetodTemelDTO metodTemelDTO = metodTemelMapper.toDto(metodTemel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMetodTemelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, metodTemelDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(metodTemelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MetodTemel in the database
        List<MetodTemel> metodTemelList = metodTemelRepository.findAll();
        assertThat(metodTemelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMetodTemel() throws Exception {
        int databaseSizeBeforeUpdate = metodTemelRepository.findAll().size();
        metodTemel.setId(count.incrementAndGet());

        // Create the MetodTemel
        MetodTemelDTO metodTemelDTO = metodTemelMapper.toDto(metodTemel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMetodTemelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(metodTemelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MetodTemel in the database
        List<MetodTemel> metodTemelList = metodTemelRepository.findAll();
        assertThat(metodTemelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMetodTemel() throws Exception {
        int databaseSizeBeforeUpdate = metodTemelRepository.findAll().size();
        metodTemel.setId(count.incrementAndGet());

        // Create the MetodTemel
        MetodTemelDTO metodTemelDTO = metodTemelMapper.toDto(metodTemel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMetodTemelMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(metodTemelDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MetodTemel in the database
        List<MetodTemel> metodTemelList = metodTemelRepository.findAll();
        assertThat(metodTemelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMetodTemelWithPatch() throws Exception {
        // Initialize the database
        metodTemelRepository.saveAndFlush(metodTemel);

        int databaseSizeBeforeUpdate = metodTemelRepository.findAll().size();

        // Update the metodTemel using partial update
        MetodTemel partialUpdatedMetodTemel = new MetodTemel();
        partialUpdatedMetodTemel.setId(metodTemel.getId());

        partialUpdatedMetodTemel.metodAciklamasi(UPDATED_METOD_ACIKLAMASI);

        restMetodTemelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMetodTemel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMetodTemel))
            )
            .andExpect(status().isOk());

        // Validate the MetodTemel in the database
        List<MetodTemel> metodTemelList = metodTemelRepository.findAll();
        assertThat(metodTemelList).hasSize(databaseSizeBeforeUpdate);
        MetodTemel testMetodTemel = metodTemelList.get(metodTemelList.size() - 1);
        assertThat(testMetodTemel.getMetodAdi()).isEqualTo(DEFAULT_METOD_ADI);
        assertThat(testMetodTemel.getMetodNu()).isEqualTo(DEFAULT_METOD_NU);
        assertThat(testMetodTemel.getMetodAciklamasi()).isEqualTo(UPDATED_METOD_ACIKLAMASI);
    }

    @Test
    @Transactional
    void fullUpdateMetodTemelWithPatch() throws Exception {
        // Initialize the database
        metodTemelRepository.saveAndFlush(metodTemel);

        int databaseSizeBeforeUpdate = metodTemelRepository.findAll().size();

        // Update the metodTemel using partial update
        MetodTemel partialUpdatedMetodTemel = new MetodTemel();
        partialUpdatedMetodTemel.setId(metodTemel.getId());

        partialUpdatedMetodTemel.metodAdi(UPDATED_METOD_ADI).metodNu(UPDATED_METOD_NU).metodAciklamasi(UPDATED_METOD_ACIKLAMASI);

        restMetodTemelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMetodTemel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMetodTemel))
            )
            .andExpect(status().isOk());

        // Validate the MetodTemel in the database
        List<MetodTemel> metodTemelList = metodTemelRepository.findAll();
        assertThat(metodTemelList).hasSize(databaseSizeBeforeUpdate);
        MetodTemel testMetodTemel = metodTemelList.get(metodTemelList.size() - 1);
        assertThat(testMetodTemel.getMetodAdi()).isEqualTo(UPDATED_METOD_ADI);
        assertThat(testMetodTemel.getMetodNu()).isEqualTo(UPDATED_METOD_NU);
        assertThat(testMetodTemel.getMetodAciklamasi()).isEqualTo(UPDATED_METOD_ACIKLAMASI);
    }

    @Test
    @Transactional
    void patchNonExistingMetodTemel() throws Exception {
        int databaseSizeBeforeUpdate = metodTemelRepository.findAll().size();
        metodTemel.setId(count.incrementAndGet());

        // Create the MetodTemel
        MetodTemelDTO metodTemelDTO = metodTemelMapper.toDto(metodTemel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMetodTemelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, metodTemelDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(metodTemelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MetodTemel in the database
        List<MetodTemel> metodTemelList = metodTemelRepository.findAll();
        assertThat(metodTemelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMetodTemel() throws Exception {
        int databaseSizeBeforeUpdate = metodTemelRepository.findAll().size();
        metodTemel.setId(count.incrementAndGet());

        // Create the MetodTemel
        MetodTemelDTO metodTemelDTO = metodTemelMapper.toDto(metodTemel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMetodTemelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(metodTemelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MetodTemel in the database
        List<MetodTemel> metodTemelList = metodTemelRepository.findAll();
        assertThat(metodTemelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMetodTemel() throws Exception {
        int databaseSizeBeforeUpdate = metodTemelRepository.findAll().size();
        metodTemel.setId(count.incrementAndGet());

        // Create the MetodTemel
        MetodTemelDTO metodTemelDTO = metodTemelMapper.toDto(metodTemel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMetodTemelMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(metodTemelDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MetodTemel in the database
        List<MetodTemel> metodTemelList = metodTemelRepository.findAll();
        assertThat(metodTemelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMetodTemel() throws Exception {
        // Initialize the database
        metodTemelRepository.saveAndFlush(metodTemel);

        int databaseSizeBeforeDelete = metodTemelRepository.findAll().size();

        // Delete the metodTemel
        restMetodTemelMockMvc
            .perform(delete(ENTITY_API_URL_ID, metodTemel.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MetodTemel> metodTemelList = metodTemelRepository.findAll();
        assertThat(metodTemelList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
