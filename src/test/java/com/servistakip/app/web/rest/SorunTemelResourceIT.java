package com.servistakip.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.servistakip.app.IntegrationTest;
import com.servistakip.app.domain.SorunTemel;
import com.servistakip.app.repository.SorunTemelRepository;
import com.servistakip.app.service.dto.SorunTemelDTO;
import com.servistakip.app.service.mapper.SorunTemelMapper;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link SorunTemelResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SorunTemelResourceIT {

    private static final String DEFAULT_SORUN_ACIKLAMASI = "AAAAAAAAAA";
    private static final String UPDATED_SORUN_ACIKLAMASI = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_SORUN_TARIHI = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_SORUN_TARIHI = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_COZUM_ACIKLAMASI = "AAAAAAAAAA";
    private static final String UPDATED_COZUM_ACIKLAMASI = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_COZUM_TARIHI = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_COZUM_TARIHI = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/sorun-temels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SorunTemelRepository sorunTemelRepository;

    @Autowired
    private SorunTemelMapper sorunTemelMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSorunTemelMockMvc;

    private SorunTemel sorunTemel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SorunTemel createEntity(EntityManager em) {
        SorunTemel sorunTemel = new SorunTemel()
            .sorunAciklamasi(DEFAULT_SORUN_ACIKLAMASI)
            .sorunTarihi(DEFAULT_SORUN_TARIHI)
            .cozumAciklamasi(DEFAULT_COZUM_ACIKLAMASI)
            .cozumTarihi(DEFAULT_COZUM_TARIHI);
        return sorunTemel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SorunTemel createUpdatedEntity(EntityManager em) {
        SorunTemel sorunTemel = new SorunTemel()
            .sorunAciklamasi(UPDATED_SORUN_ACIKLAMASI)
            .sorunTarihi(UPDATED_SORUN_TARIHI)
            .cozumAciklamasi(UPDATED_COZUM_ACIKLAMASI)
            .cozumTarihi(UPDATED_COZUM_TARIHI);
        return sorunTemel;
    }

    @BeforeEach
    public void initTest() {
        sorunTemel = createEntity(em);
    }

    @Test
    @Transactional
    void createSorunTemel() throws Exception {
        int databaseSizeBeforeCreate = sorunTemelRepository.findAll().size();
        // Create the SorunTemel
        SorunTemelDTO sorunTemelDTO = sorunTemelMapper.toDto(sorunTemel);
        restSorunTemelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sorunTemelDTO)))
            .andExpect(status().isCreated());

        // Validate the SorunTemel in the database
        List<SorunTemel> sorunTemelList = sorunTemelRepository.findAll();
        assertThat(sorunTemelList).hasSize(databaseSizeBeforeCreate + 1);
        SorunTemel testSorunTemel = sorunTemelList.get(sorunTemelList.size() - 1);
        assertThat(testSorunTemel.getSorunAciklamasi()).isEqualTo(DEFAULT_SORUN_ACIKLAMASI);
        assertThat(testSorunTemel.getSorunTarihi()).isEqualTo(DEFAULT_SORUN_TARIHI);
        assertThat(testSorunTemel.getCozumAciklamasi()).isEqualTo(DEFAULT_COZUM_ACIKLAMASI);
        assertThat(testSorunTemel.getCozumTarihi()).isEqualTo(DEFAULT_COZUM_TARIHI);
    }

    @Test
    @Transactional
    void createSorunTemelWithExistingId() throws Exception {
        // Create the SorunTemel with an existing ID
        sorunTemel.setId(1L);
        SorunTemelDTO sorunTemelDTO = sorunTemelMapper.toDto(sorunTemel);

        int databaseSizeBeforeCreate = sorunTemelRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSorunTemelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sorunTemelDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SorunTemel in the database
        List<SorunTemel> sorunTemelList = sorunTemelRepository.findAll();
        assertThat(sorunTemelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkSorunAciklamasiIsRequired() throws Exception {
        int databaseSizeBeforeTest = sorunTemelRepository.findAll().size();
        // set the field null
        sorunTemel.setSorunAciklamasi(null);

        // Create the SorunTemel, which fails.
        SorunTemelDTO sorunTemelDTO = sorunTemelMapper.toDto(sorunTemel);

        restSorunTemelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sorunTemelDTO)))
            .andExpect(status().isBadRequest());

        List<SorunTemel> sorunTemelList = sorunTemelRepository.findAll();
        assertThat(sorunTemelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSorunTarihiIsRequired() throws Exception {
        int databaseSizeBeforeTest = sorunTemelRepository.findAll().size();
        // set the field null
        sorunTemel.setSorunTarihi(null);

        // Create the SorunTemel, which fails.
        SorunTemelDTO sorunTemelDTO = sorunTemelMapper.toDto(sorunTemel);

        restSorunTemelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sorunTemelDTO)))
            .andExpect(status().isBadRequest());

        List<SorunTemel> sorunTemelList = sorunTemelRepository.findAll();
        assertThat(sorunTemelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSorunTemels() throws Exception {
        // Initialize the database
        sorunTemelRepository.saveAndFlush(sorunTemel);

        // Get all the sorunTemelList
        restSorunTemelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sorunTemel.getId().intValue())))
            .andExpect(jsonPath("$.[*].sorunAciklamasi").value(hasItem(DEFAULT_SORUN_ACIKLAMASI)))
            .andExpect(jsonPath("$.[*].sorunTarihi").value(hasItem(DEFAULT_SORUN_TARIHI.toString())))
            .andExpect(jsonPath("$.[*].cozumAciklamasi").value(hasItem(DEFAULT_COZUM_ACIKLAMASI)))
            .andExpect(jsonPath("$.[*].cozumTarihi").value(hasItem(DEFAULT_COZUM_TARIHI.toString())));
    }

    @Test
    @Transactional
    void getSorunTemel() throws Exception {
        // Initialize the database
        sorunTemelRepository.saveAndFlush(sorunTemel);

        // Get the sorunTemel
        restSorunTemelMockMvc
            .perform(get(ENTITY_API_URL_ID, sorunTemel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sorunTemel.getId().intValue()))
            .andExpect(jsonPath("$.sorunAciklamasi").value(DEFAULT_SORUN_ACIKLAMASI))
            .andExpect(jsonPath("$.sorunTarihi").value(DEFAULT_SORUN_TARIHI.toString()))
            .andExpect(jsonPath("$.cozumAciklamasi").value(DEFAULT_COZUM_ACIKLAMASI))
            .andExpect(jsonPath("$.cozumTarihi").value(DEFAULT_COZUM_TARIHI.toString()));
    }

    @Test
    @Transactional
    void getNonExistingSorunTemel() throws Exception {
        // Get the sorunTemel
        restSorunTemelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSorunTemel() throws Exception {
        // Initialize the database
        sorunTemelRepository.saveAndFlush(sorunTemel);

        int databaseSizeBeforeUpdate = sorunTemelRepository.findAll().size();

        // Update the sorunTemel
        SorunTemel updatedSorunTemel = sorunTemelRepository.findById(sorunTemel.getId()).get();
        // Disconnect from session so that the updates on updatedSorunTemel are not directly saved in db
        em.detach(updatedSorunTemel);
        updatedSorunTemel
            .sorunAciklamasi(UPDATED_SORUN_ACIKLAMASI)
            .sorunTarihi(UPDATED_SORUN_TARIHI)
            .cozumAciklamasi(UPDATED_COZUM_ACIKLAMASI)
            .cozumTarihi(UPDATED_COZUM_TARIHI);
        SorunTemelDTO sorunTemelDTO = sorunTemelMapper.toDto(updatedSorunTemel);

        restSorunTemelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sorunTemelDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sorunTemelDTO))
            )
            .andExpect(status().isOk());

        // Validate the SorunTemel in the database
        List<SorunTemel> sorunTemelList = sorunTemelRepository.findAll();
        assertThat(sorunTemelList).hasSize(databaseSizeBeforeUpdate);
        SorunTemel testSorunTemel = sorunTemelList.get(sorunTemelList.size() - 1);
        assertThat(testSorunTemel.getSorunAciklamasi()).isEqualTo(UPDATED_SORUN_ACIKLAMASI);
        assertThat(testSorunTemel.getSorunTarihi()).isEqualTo(UPDATED_SORUN_TARIHI);
        assertThat(testSorunTemel.getCozumAciklamasi()).isEqualTo(UPDATED_COZUM_ACIKLAMASI);
        assertThat(testSorunTemel.getCozumTarihi()).isEqualTo(UPDATED_COZUM_TARIHI);
    }

    @Test
    @Transactional
    void putNonExistingSorunTemel() throws Exception {
        int databaseSizeBeforeUpdate = sorunTemelRepository.findAll().size();
        sorunTemel.setId(count.incrementAndGet());

        // Create the SorunTemel
        SorunTemelDTO sorunTemelDTO = sorunTemelMapper.toDto(sorunTemel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSorunTemelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sorunTemelDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sorunTemelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SorunTemel in the database
        List<SorunTemel> sorunTemelList = sorunTemelRepository.findAll();
        assertThat(sorunTemelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSorunTemel() throws Exception {
        int databaseSizeBeforeUpdate = sorunTemelRepository.findAll().size();
        sorunTemel.setId(count.incrementAndGet());

        // Create the SorunTemel
        SorunTemelDTO sorunTemelDTO = sorunTemelMapper.toDto(sorunTemel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSorunTemelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sorunTemelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SorunTemel in the database
        List<SorunTemel> sorunTemelList = sorunTemelRepository.findAll();
        assertThat(sorunTemelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSorunTemel() throws Exception {
        int databaseSizeBeforeUpdate = sorunTemelRepository.findAll().size();
        sorunTemel.setId(count.incrementAndGet());

        // Create the SorunTemel
        SorunTemelDTO sorunTemelDTO = sorunTemelMapper.toDto(sorunTemel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSorunTemelMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sorunTemelDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SorunTemel in the database
        List<SorunTemel> sorunTemelList = sorunTemelRepository.findAll();
        assertThat(sorunTemelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSorunTemelWithPatch() throws Exception {
        // Initialize the database
        sorunTemelRepository.saveAndFlush(sorunTemel);

        int databaseSizeBeforeUpdate = sorunTemelRepository.findAll().size();

        // Update the sorunTemel using partial update
        SorunTemel partialUpdatedSorunTemel = new SorunTemel();
        partialUpdatedSorunTemel.setId(sorunTemel.getId());

        partialUpdatedSorunTemel
            .sorunTarihi(UPDATED_SORUN_TARIHI)
            .cozumAciklamasi(UPDATED_COZUM_ACIKLAMASI)
            .cozumTarihi(UPDATED_COZUM_TARIHI);

        restSorunTemelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSorunTemel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSorunTemel))
            )
            .andExpect(status().isOk());

        // Validate the SorunTemel in the database
        List<SorunTemel> sorunTemelList = sorunTemelRepository.findAll();
        assertThat(sorunTemelList).hasSize(databaseSizeBeforeUpdate);
        SorunTemel testSorunTemel = sorunTemelList.get(sorunTemelList.size() - 1);
        assertThat(testSorunTemel.getSorunAciklamasi()).isEqualTo(DEFAULT_SORUN_ACIKLAMASI);
        assertThat(testSorunTemel.getSorunTarihi()).isEqualTo(UPDATED_SORUN_TARIHI);
        assertThat(testSorunTemel.getCozumAciklamasi()).isEqualTo(UPDATED_COZUM_ACIKLAMASI);
        assertThat(testSorunTemel.getCozumTarihi()).isEqualTo(UPDATED_COZUM_TARIHI);
    }

    @Test
    @Transactional
    void fullUpdateSorunTemelWithPatch() throws Exception {
        // Initialize the database
        sorunTemelRepository.saveAndFlush(sorunTemel);

        int databaseSizeBeforeUpdate = sorunTemelRepository.findAll().size();

        // Update the sorunTemel using partial update
        SorunTemel partialUpdatedSorunTemel = new SorunTemel();
        partialUpdatedSorunTemel.setId(sorunTemel.getId());

        partialUpdatedSorunTemel
            .sorunAciklamasi(UPDATED_SORUN_ACIKLAMASI)
            .sorunTarihi(UPDATED_SORUN_TARIHI)
            .cozumAciklamasi(UPDATED_COZUM_ACIKLAMASI)
            .cozumTarihi(UPDATED_COZUM_TARIHI);

        restSorunTemelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSorunTemel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSorunTemel))
            )
            .andExpect(status().isOk());

        // Validate the SorunTemel in the database
        List<SorunTemel> sorunTemelList = sorunTemelRepository.findAll();
        assertThat(sorunTemelList).hasSize(databaseSizeBeforeUpdate);
        SorunTemel testSorunTemel = sorunTemelList.get(sorunTemelList.size() - 1);
        assertThat(testSorunTemel.getSorunAciklamasi()).isEqualTo(UPDATED_SORUN_ACIKLAMASI);
        assertThat(testSorunTemel.getSorunTarihi()).isEqualTo(UPDATED_SORUN_TARIHI);
        assertThat(testSorunTemel.getCozumAciklamasi()).isEqualTo(UPDATED_COZUM_ACIKLAMASI);
        assertThat(testSorunTemel.getCozumTarihi()).isEqualTo(UPDATED_COZUM_TARIHI);
    }

    @Test
    @Transactional
    void patchNonExistingSorunTemel() throws Exception {
        int databaseSizeBeforeUpdate = sorunTemelRepository.findAll().size();
        sorunTemel.setId(count.incrementAndGet());

        // Create the SorunTemel
        SorunTemelDTO sorunTemelDTO = sorunTemelMapper.toDto(sorunTemel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSorunTemelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, sorunTemelDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sorunTemelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SorunTemel in the database
        List<SorunTemel> sorunTemelList = sorunTemelRepository.findAll();
        assertThat(sorunTemelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSorunTemel() throws Exception {
        int databaseSizeBeforeUpdate = sorunTemelRepository.findAll().size();
        sorunTemel.setId(count.incrementAndGet());

        // Create the SorunTemel
        SorunTemelDTO sorunTemelDTO = sorunTemelMapper.toDto(sorunTemel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSorunTemelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sorunTemelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SorunTemel in the database
        List<SorunTemel> sorunTemelList = sorunTemelRepository.findAll();
        assertThat(sorunTemelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSorunTemel() throws Exception {
        int databaseSizeBeforeUpdate = sorunTemelRepository.findAll().size();
        sorunTemel.setId(count.incrementAndGet());

        // Create the SorunTemel
        SorunTemelDTO sorunTemelDTO = sorunTemelMapper.toDto(sorunTemel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSorunTemelMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(sorunTemelDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SorunTemel in the database
        List<SorunTemel> sorunTemelList = sorunTemelRepository.findAll();
        assertThat(sorunTemelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSorunTemel() throws Exception {
        // Initialize the database
        sorunTemelRepository.saveAndFlush(sorunTemel);

        int databaseSizeBeforeDelete = sorunTemelRepository.findAll().size();

        // Delete the sorunTemel
        restSorunTemelMockMvc
            .perform(delete(ENTITY_API_URL_ID, sorunTemel.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SorunTemel> sorunTemelList = sorunTemelRepository.findAll();
        assertThat(sorunTemelList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
