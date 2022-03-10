package com.servistakip.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.servistakip.app.IntegrationTest;
import com.servistakip.app.domain.KurumTemel;
import com.servistakip.app.repository.KurumTemelRepository;
import com.servistakip.app.service.dto.KurumTemelDTO;
import com.servistakip.app.service.mapper.KurumTemelMapper;
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
 * Integration tests for the {@link KurumTemelResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class KurumTemelResourceIT {

    private static final String DEFAULT_BAKANLIK_ADI = "AAAAAAAAAA";
    private static final String UPDATED_BAKANLIK_ADI = "BBBBBBBBBB";

    private static final String DEFAULT_BIRIM_ADI = "AAAAAAAAAA";
    private static final String UPDATED_BIRIM_ADI = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/kurum-temels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private KurumTemelRepository kurumTemelRepository;

    @Autowired
    private KurumTemelMapper kurumTemelMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restKurumTemelMockMvc;

    private KurumTemel kurumTemel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static KurumTemel createEntity(EntityManager em) {
        KurumTemel kurumTemel = new KurumTemel().bakanlikAdi(DEFAULT_BAKANLIK_ADI).birimAdi(DEFAULT_BIRIM_ADI);
        return kurumTemel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static KurumTemel createUpdatedEntity(EntityManager em) {
        KurumTemel kurumTemel = new KurumTemel().bakanlikAdi(UPDATED_BAKANLIK_ADI).birimAdi(UPDATED_BIRIM_ADI);
        return kurumTemel;
    }

    @BeforeEach
    public void initTest() {
        kurumTemel = createEntity(em);
    }

    @Test
    @Transactional
    void createKurumTemel() throws Exception {
        int databaseSizeBeforeCreate = kurumTemelRepository.findAll().size();
        // Create the KurumTemel
        KurumTemelDTO kurumTemelDTO = kurumTemelMapper.toDto(kurumTemel);
        restKurumTemelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kurumTemelDTO)))
            .andExpect(status().isCreated());

        // Validate the KurumTemel in the database
        List<KurumTemel> kurumTemelList = kurumTemelRepository.findAll();
        assertThat(kurumTemelList).hasSize(databaseSizeBeforeCreate + 1);
        KurumTemel testKurumTemel = kurumTemelList.get(kurumTemelList.size() - 1);
        assertThat(testKurumTemel.getBakanlikAdi()).isEqualTo(DEFAULT_BAKANLIK_ADI);
        assertThat(testKurumTemel.getBirimAdi()).isEqualTo(DEFAULT_BIRIM_ADI);
    }

    @Test
    @Transactional
    void createKurumTemelWithExistingId() throws Exception {
        // Create the KurumTemel with an existing ID
        kurumTemel.setId(1L);
        KurumTemelDTO kurumTemelDTO = kurumTemelMapper.toDto(kurumTemel);

        int databaseSizeBeforeCreate = kurumTemelRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restKurumTemelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kurumTemelDTO)))
            .andExpect(status().isBadRequest());

        // Validate the KurumTemel in the database
        List<KurumTemel> kurumTemelList = kurumTemelRepository.findAll();
        assertThat(kurumTemelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkBakanlikAdiIsRequired() throws Exception {
        int databaseSizeBeforeTest = kurumTemelRepository.findAll().size();
        // set the field null
        kurumTemel.setBakanlikAdi(null);

        // Create the KurumTemel, which fails.
        KurumTemelDTO kurumTemelDTO = kurumTemelMapper.toDto(kurumTemel);

        restKurumTemelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kurumTemelDTO)))
            .andExpect(status().isBadRequest());

        List<KurumTemel> kurumTemelList = kurumTemelRepository.findAll();
        assertThat(kurumTemelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBirimAdiIsRequired() throws Exception {
        int databaseSizeBeforeTest = kurumTemelRepository.findAll().size();
        // set the field null
        kurumTemel.setBirimAdi(null);

        // Create the KurumTemel, which fails.
        KurumTemelDTO kurumTemelDTO = kurumTemelMapper.toDto(kurumTemel);

        restKurumTemelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kurumTemelDTO)))
            .andExpect(status().isBadRequest());

        List<KurumTemel> kurumTemelList = kurumTemelRepository.findAll();
        assertThat(kurumTemelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllKurumTemels() throws Exception {
        // Initialize the database
        kurumTemelRepository.saveAndFlush(kurumTemel);

        // Get all the kurumTemelList
        restKurumTemelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kurumTemel.getId().intValue())))
            .andExpect(jsonPath("$.[*].bakanlikAdi").value(hasItem(DEFAULT_BAKANLIK_ADI)))
            .andExpect(jsonPath("$.[*].birimAdi").value(hasItem(DEFAULT_BIRIM_ADI)));
    }

    @Test
    @Transactional
    void getKurumTemel() throws Exception {
        // Initialize the database
        kurumTemelRepository.saveAndFlush(kurumTemel);

        // Get the kurumTemel
        restKurumTemelMockMvc
            .perform(get(ENTITY_API_URL_ID, kurumTemel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(kurumTemel.getId().intValue()))
            .andExpect(jsonPath("$.bakanlikAdi").value(DEFAULT_BAKANLIK_ADI))
            .andExpect(jsonPath("$.birimAdi").value(DEFAULT_BIRIM_ADI));
    }

    @Test
    @Transactional
    void getNonExistingKurumTemel() throws Exception {
        // Get the kurumTemel
        restKurumTemelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewKurumTemel() throws Exception {
        // Initialize the database
        kurumTemelRepository.saveAndFlush(kurumTemel);

        int databaseSizeBeforeUpdate = kurumTemelRepository.findAll().size();

        // Update the kurumTemel
        KurumTemel updatedKurumTemel = kurumTemelRepository.findById(kurumTemel.getId()).get();
        // Disconnect from session so that the updates on updatedKurumTemel are not directly saved in db
        em.detach(updatedKurumTemel);
        updatedKurumTemel.bakanlikAdi(UPDATED_BAKANLIK_ADI).birimAdi(UPDATED_BIRIM_ADI);
        KurumTemelDTO kurumTemelDTO = kurumTemelMapper.toDto(updatedKurumTemel);

        restKurumTemelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, kurumTemelDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(kurumTemelDTO))
            )
            .andExpect(status().isOk());

        // Validate the KurumTemel in the database
        List<KurumTemel> kurumTemelList = kurumTemelRepository.findAll();
        assertThat(kurumTemelList).hasSize(databaseSizeBeforeUpdate);
        KurumTemel testKurumTemel = kurumTemelList.get(kurumTemelList.size() - 1);
        assertThat(testKurumTemel.getBakanlikAdi()).isEqualTo(UPDATED_BAKANLIK_ADI);
        assertThat(testKurumTemel.getBirimAdi()).isEqualTo(UPDATED_BIRIM_ADI);
    }

    @Test
    @Transactional
    void putNonExistingKurumTemel() throws Exception {
        int databaseSizeBeforeUpdate = kurumTemelRepository.findAll().size();
        kurumTemel.setId(count.incrementAndGet());

        // Create the KurumTemel
        KurumTemelDTO kurumTemelDTO = kurumTemelMapper.toDto(kurumTemel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKurumTemelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, kurumTemelDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(kurumTemelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the KurumTemel in the database
        List<KurumTemel> kurumTemelList = kurumTemelRepository.findAll();
        assertThat(kurumTemelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchKurumTemel() throws Exception {
        int databaseSizeBeforeUpdate = kurumTemelRepository.findAll().size();
        kurumTemel.setId(count.incrementAndGet());

        // Create the KurumTemel
        KurumTemelDTO kurumTemelDTO = kurumTemelMapper.toDto(kurumTemel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKurumTemelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(kurumTemelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the KurumTemel in the database
        List<KurumTemel> kurumTemelList = kurumTemelRepository.findAll();
        assertThat(kurumTemelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamKurumTemel() throws Exception {
        int databaseSizeBeforeUpdate = kurumTemelRepository.findAll().size();
        kurumTemel.setId(count.incrementAndGet());

        // Create the KurumTemel
        KurumTemelDTO kurumTemelDTO = kurumTemelMapper.toDto(kurumTemel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKurumTemelMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kurumTemelDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the KurumTemel in the database
        List<KurumTemel> kurumTemelList = kurumTemelRepository.findAll();
        assertThat(kurumTemelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateKurumTemelWithPatch() throws Exception {
        // Initialize the database
        kurumTemelRepository.saveAndFlush(kurumTemel);

        int databaseSizeBeforeUpdate = kurumTemelRepository.findAll().size();

        // Update the kurumTemel using partial update
        KurumTemel partialUpdatedKurumTemel = new KurumTemel();
        partialUpdatedKurumTemel.setId(kurumTemel.getId());

        restKurumTemelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKurumTemel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedKurumTemel))
            )
            .andExpect(status().isOk());

        // Validate the KurumTemel in the database
        List<KurumTemel> kurumTemelList = kurumTemelRepository.findAll();
        assertThat(kurumTemelList).hasSize(databaseSizeBeforeUpdate);
        KurumTemel testKurumTemel = kurumTemelList.get(kurumTemelList.size() - 1);
        assertThat(testKurumTemel.getBakanlikAdi()).isEqualTo(DEFAULT_BAKANLIK_ADI);
        assertThat(testKurumTemel.getBirimAdi()).isEqualTo(DEFAULT_BIRIM_ADI);
    }

    @Test
    @Transactional
    void fullUpdateKurumTemelWithPatch() throws Exception {
        // Initialize the database
        kurumTemelRepository.saveAndFlush(kurumTemel);

        int databaseSizeBeforeUpdate = kurumTemelRepository.findAll().size();

        // Update the kurumTemel using partial update
        KurumTemel partialUpdatedKurumTemel = new KurumTemel();
        partialUpdatedKurumTemel.setId(kurumTemel.getId());

        partialUpdatedKurumTemel.bakanlikAdi(UPDATED_BAKANLIK_ADI).birimAdi(UPDATED_BIRIM_ADI);

        restKurumTemelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKurumTemel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedKurumTemel))
            )
            .andExpect(status().isOk());

        // Validate the KurumTemel in the database
        List<KurumTemel> kurumTemelList = kurumTemelRepository.findAll();
        assertThat(kurumTemelList).hasSize(databaseSizeBeforeUpdate);
        KurumTemel testKurumTemel = kurumTemelList.get(kurumTemelList.size() - 1);
        assertThat(testKurumTemel.getBakanlikAdi()).isEqualTo(UPDATED_BAKANLIK_ADI);
        assertThat(testKurumTemel.getBirimAdi()).isEqualTo(UPDATED_BIRIM_ADI);
    }

    @Test
    @Transactional
    void patchNonExistingKurumTemel() throws Exception {
        int databaseSizeBeforeUpdate = kurumTemelRepository.findAll().size();
        kurumTemel.setId(count.incrementAndGet());

        // Create the KurumTemel
        KurumTemelDTO kurumTemelDTO = kurumTemelMapper.toDto(kurumTemel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKurumTemelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, kurumTemelDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(kurumTemelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the KurumTemel in the database
        List<KurumTemel> kurumTemelList = kurumTemelRepository.findAll();
        assertThat(kurumTemelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchKurumTemel() throws Exception {
        int databaseSizeBeforeUpdate = kurumTemelRepository.findAll().size();
        kurumTemel.setId(count.incrementAndGet());

        // Create the KurumTemel
        KurumTemelDTO kurumTemelDTO = kurumTemelMapper.toDto(kurumTemel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKurumTemelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(kurumTemelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the KurumTemel in the database
        List<KurumTemel> kurumTemelList = kurumTemelRepository.findAll();
        assertThat(kurumTemelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamKurumTemel() throws Exception {
        int databaseSizeBeforeUpdate = kurumTemelRepository.findAll().size();
        kurumTemel.setId(count.incrementAndGet());

        // Create the KurumTemel
        KurumTemelDTO kurumTemelDTO = kurumTemelMapper.toDto(kurumTemel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKurumTemelMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(kurumTemelDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the KurumTemel in the database
        List<KurumTemel> kurumTemelList = kurumTemelRepository.findAll();
        assertThat(kurumTemelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteKurumTemel() throws Exception {
        // Initialize the database
        kurumTemelRepository.saveAndFlush(kurumTemel);

        int databaseSizeBeforeDelete = kurumTemelRepository.findAll().size();

        // Delete the kurumTemel
        restKurumTemelMockMvc
            .perform(delete(ENTITY_API_URL_ID, kurumTemel.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<KurumTemel> kurumTemelList = kurumTemelRepository.findAll();
        assertThat(kurumTemelList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
