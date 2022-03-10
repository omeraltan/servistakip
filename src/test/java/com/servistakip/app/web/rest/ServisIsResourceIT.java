package com.servistakip.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.servistakip.app.IntegrationTest;
import com.servistakip.app.domain.ServisIs;
import com.servistakip.app.repository.ServisIsRepository;
import com.servistakip.app.service.dto.ServisIsDTO;
import com.servistakip.app.service.mapper.ServisIsMapper;
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
 * Integration tests for the {@link ServisIsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ServisIsResourceIT {

    private static final LocalDate DEFAULT_BASLAMA_TARIHI = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BASLAMA_TARIHI = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_TAMAMLAMA_TARIHI = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TAMAMLAMA_TARIHI = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/servises";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ServisIsRepository servisIsRepository;

    @Autowired
    private ServisIsMapper servisIsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restServisIsMockMvc;

    private ServisIs servisIs;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServisIs createEntity(EntityManager em) {
        ServisIs servisIs = new ServisIs().baslamaTarihi(DEFAULT_BASLAMA_TARIHI).tamamlamaTarihi(DEFAULT_TAMAMLAMA_TARIHI);
        return servisIs;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServisIs createUpdatedEntity(EntityManager em) {
        ServisIs servisIs = new ServisIs().baslamaTarihi(UPDATED_BASLAMA_TARIHI).tamamlamaTarihi(UPDATED_TAMAMLAMA_TARIHI);
        return servisIs;
    }

    @BeforeEach
    public void initTest() {
        servisIs = createEntity(em);
    }

    @Test
    @Transactional
    void createServisIs() throws Exception {
        int databaseSizeBeforeCreate = servisIsRepository.findAll().size();
        // Create the ServisIs
        ServisIsDTO servisIsDTO = servisIsMapper.toDto(servisIs);
        restServisIsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(servisIsDTO)))
            .andExpect(status().isCreated());

        // Validate the ServisIs in the database
        List<ServisIs> servisIsList = servisIsRepository.findAll();
        assertThat(servisIsList).hasSize(databaseSizeBeforeCreate + 1);
        ServisIs testServisIs = servisIsList.get(servisIsList.size() - 1);
        assertThat(testServisIs.getBaslamaTarihi()).isEqualTo(DEFAULT_BASLAMA_TARIHI);
        assertThat(testServisIs.getTamamlamaTarihi()).isEqualTo(DEFAULT_TAMAMLAMA_TARIHI);
    }

    @Test
    @Transactional
    void createServisIsWithExistingId() throws Exception {
        // Create the ServisIs with an existing ID
        servisIs.setId(1L);
        ServisIsDTO servisIsDTO = servisIsMapper.toDto(servisIs);

        int databaseSizeBeforeCreate = servisIsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restServisIsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(servisIsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ServisIs in the database
        List<ServisIs> servisIsList = servisIsRepository.findAll();
        assertThat(servisIsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkBaslamaTarihiIsRequired() throws Exception {
        int databaseSizeBeforeTest = servisIsRepository.findAll().size();
        // set the field null
        servisIs.setBaslamaTarihi(null);

        // Create the ServisIs, which fails.
        ServisIsDTO servisIsDTO = servisIsMapper.toDto(servisIs);

        restServisIsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(servisIsDTO)))
            .andExpect(status().isBadRequest());

        List<ServisIs> servisIsList = servisIsRepository.findAll();
        assertThat(servisIsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTamamlamaTarihiIsRequired() throws Exception {
        int databaseSizeBeforeTest = servisIsRepository.findAll().size();
        // set the field null
        servisIs.setTamamlamaTarihi(null);

        // Create the ServisIs, which fails.
        ServisIsDTO servisIsDTO = servisIsMapper.toDto(servisIs);

        restServisIsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(servisIsDTO)))
            .andExpect(status().isBadRequest());

        List<ServisIs> servisIsList = servisIsRepository.findAll();
        assertThat(servisIsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllServises() throws Exception {
        // Initialize the database
        servisIsRepository.saveAndFlush(servisIs);

        // Get all the servisIsList
        restServisIsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(servisIs.getId().intValue())))
            .andExpect(jsonPath("$.[*].baslamaTarihi").value(hasItem(DEFAULT_BASLAMA_TARIHI.toString())))
            .andExpect(jsonPath("$.[*].tamamlamaTarihi").value(hasItem(DEFAULT_TAMAMLAMA_TARIHI.toString())));
    }

    @Test
    @Transactional
    void getServisIs() throws Exception {
        // Initialize the database
        servisIsRepository.saveAndFlush(servisIs);

        // Get the servisIs
        restServisIsMockMvc
            .perform(get(ENTITY_API_URL_ID, servisIs.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(servisIs.getId().intValue()))
            .andExpect(jsonPath("$.baslamaTarihi").value(DEFAULT_BASLAMA_TARIHI.toString()))
            .andExpect(jsonPath("$.tamamlamaTarihi").value(DEFAULT_TAMAMLAMA_TARIHI.toString()));
    }

    @Test
    @Transactional
    void getNonExistingServisIs() throws Exception {
        // Get the servisIs
        restServisIsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewServisIs() throws Exception {
        // Initialize the database
        servisIsRepository.saveAndFlush(servisIs);

        int databaseSizeBeforeUpdate = servisIsRepository.findAll().size();

        // Update the servisIs
        ServisIs updatedServisIs = servisIsRepository.findById(servisIs.getId()).get();
        // Disconnect from session so that the updates on updatedServisIs are not directly saved in db
        em.detach(updatedServisIs);
        updatedServisIs.baslamaTarihi(UPDATED_BASLAMA_TARIHI).tamamlamaTarihi(UPDATED_TAMAMLAMA_TARIHI);
        ServisIsDTO servisIsDTO = servisIsMapper.toDto(updatedServisIs);

        restServisIsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, servisIsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(servisIsDTO))
            )
            .andExpect(status().isOk());

        // Validate the ServisIs in the database
        List<ServisIs> servisIsList = servisIsRepository.findAll();
        assertThat(servisIsList).hasSize(databaseSizeBeforeUpdate);
        ServisIs testServisIs = servisIsList.get(servisIsList.size() - 1);
        assertThat(testServisIs.getBaslamaTarihi()).isEqualTo(UPDATED_BASLAMA_TARIHI);
        assertThat(testServisIs.getTamamlamaTarihi()).isEqualTo(UPDATED_TAMAMLAMA_TARIHI);
    }

    @Test
    @Transactional
    void putNonExistingServisIs() throws Exception {
        int databaseSizeBeforeUpdate = servisIsRepository.findAll().size();
        servisIs.setId(count.incrementAndGet());

        // Create the ServisIs
        ServisIsDTO servisIsDTO = servisIsMapper.toDto(servisIs);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServisIsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, servisIsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(servisIsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServisIs in the database
        List<ServisIs> servisIsList = servisIsRepository.findAll();
        assertThat(servisIsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchServisIs() throws Exception {
        int databaseSizeBeforeUpdate = servisIsRepository.findAll().size();
        servisIs.setId(count.incrementAndGet());

        // Create the ServisIs
        ServisIsDTO servisIsDTO = servisIsMapper.toDto(servisIs);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServisIsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(servisIsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServisIs in the database
        List<ServisIs> servisIsList = servisIsRepository.findAll();
        assertThat(servisIsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamServisIs() throws Exception {
        int databaseSizeBeforeUpdate = servisIsRepository.findAll().size();
        servisIs.setId(count.incrementAndGet());

        // Create the ServisIs
        ServisIsDTO servisIsDTO = servisIsMapper.toDto(servisIs);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServisIsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(servisIsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ServisIs in the database
        List<ServisIs> servisIsList = servisIsRepository.findAll();
        assertThat(servisIsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateServisIsWithPatch() throws Exception {
        // Initialize the database
        servisIsRepository.saveAndFlush(servisIs);

        int databaseSizeBeforeUpdate = servisIsRepository.findAll().size();

        // Update the servisIs using partial update
        ServisIs partialUpdatedServisIs = new ServisIs();
        partialUpdatedServisIs.setId(servisIs.getId());

        partialUpdatedServisIs.baslamaTarihi(UPDATED_BASLAMA_TARIHI).tamamlamaTarihi(UPDATED_TAMAMLAMA_TARIHI);

        restServisIsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedServisIs.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedServisIs))
            )
            .andExpect(status().isOk());

        // Validate the ServisIs in the database
        List<ServisIs> servisIsList = servisIsRepository.findAll();
        assertThat(servisIsList).hasSize(databaseSizeBeforeUpdate);
        ServisIs testServisIs = servisIsList.get(servisIsList.size() - 1);
        assertThat(testServisIs.getBaslamaTarihi()).isEqualTo(UPDATED_BASLAMA_TARIHI);
        assertThat(testServisIs.getTamamlamaTarihi()).isEqualTo(UPDATED_TAMAMLAMA_TARIHI);
    }

    @Test
    @Transactional
    void fullUpdateServisIsWithPatch() throws Exception {
        // Initialize the database
        servisIsRepository.saveAndFlush(servisIs);

        int databaseSizeBeforeUpdate = servisIsRepository.findAll().size();

        // Update the servisIs using partial update
        ServisIs partialUpdatedServisIs = new ServisIs();
        partialUpdatedServisIs.setId(servisIs.getId());

        partialUpdatedServisIs.baslamaTarihi(UPDATED_BASLAMA_TARIHI).tamamlamaTarihi(UPDATED_TAMAMLAMA_TARIHI);

        restServisIsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedServisIs.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedServisIs))
            )
            .andExpect(status().isOk());

        // Validate the ServisIs in the database
        List<ServisIs> servisIsList = servisIsRepository.findAll();
        assertThat(servisIsList).hasSize(databaseSizeBeforeUpdate);
        ServisIs testServisIs = servisIsList.get(servisIsList.size() - 1);
        assertThat(testServisIs.getBaslamaTarihi()).isEqualTo(UPDATED_BASLAMA_TARIHI);
        assertThat(testServisIs.getTamamlamaTarihi()).isEqualTo(UPDATED_TAMAMLAMA_TARIHI);
    }

    @Test
    @Transactional
    void patchNonExistingServisIs() throws Exception {
        int databaseSizeBeforeUpdate = servisIsRepository.findAll().size();
        servisIs.setId(count.incrementAndGet());

        // Create the ServisIs
        ServisIsDTO servisIsDTO = servisIsMapper.toDto(servisIs);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServisIsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, servisIsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(servisIsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServisIs in the database
        List<ServisIs> servisIsList = servisIsRepository.findAll();
        assertThat(servisIsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchServisIs() throws Exception {
        int databaseSizeBeforeUpdate = servisIsRepository.findAll().size();
        servisIs.setId(count.incrementAndGet());

        // Create the ServisIs
        ServisIsDTO servisIsDTO = servisIsMapper.toDto(servisIs);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServisIsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(servisIsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServisIs in the database
        List<ServisIs> servisIsList = servisIsRepository.findAll();
        assertThat(servisIsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamServisIs() throws Exception {
        int databaseSizeBeforeUpdate = servisIsRepository.findAll().size();
        servisIs.setId(count.incrementAndGet());

        // Create the ServisIs
        ServisIsDTO servisIsDTO = servisIsMapper.toDto(servisIs);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServisIsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(servisIsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ServisIs in the database
        List<ServisIs> servisIsList = servisIsRepository.findAll();
        assertThat(servisIsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteServisIs() throws Exception {
        // Initialize the database
        servisIsRepository.saveAndFlush(servisIs);

        int databaseSizeBeforeDelete = servisIsRepository.findAll().size();

        // Delete the servisIs
        restServisIsMockMvc
            .perform(delete(ENTITY_API_URL_ID, servisIs.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ServisIs> servisIsList = servisIsRepository.findAll();
        assertThat(servisIsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
