package com.servistakip.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.servistakip.app.IntegrationTest;
import com.servistakip.app.domain.ServisProjeTemel;
import com.servistakip.app.repository.ServisProjeTemelRepository;
import com.servistakip.app.service.dto.ServisProjeTemelDTO;
import com.servistakip.app.service.mapper.ServisProjeTemelMapper;
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
 * Integration tests for the {@link ServisProjeTemelResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ServisProjeTemelResourceIT {

    private static final String DEFAULT_KULLANILDIGI_YER = "AAAAAAAAAA";
    private static final String UPDATED_KULLANILDIGI_YER = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/servis-proje-temels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ServisProjeTemelRepository servisProjeTemelRepository;

    @Autowired
    private ServisProjeTemelMapper servisProjeTemelMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restServisProjeTemelMockMvc;

    private ServisProjeTemel servisProjeTemel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServisProjeTemel createEntity(EntityManager em) {
        ServisProjeTemel servisProjeTemel = new ServisProjeTemel().kullanildigiYer(DEFAULT_KULLANILDIGI_YER);
        return servisProjeTemel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServisProjeTemel createUpdatedEntity(EntityManager em) {
        ServisProjeTemel servisProjeTemel = new ServisProjeTemel().kullanildigiYer(UPDATED_KULLANILDIGI_YER);
        return servisProjeTemel;
    }

    @BeforeEach
    public void initTest() {
        servisProjeTemel = createEntity(em);
    }

    @Test
    @Transactional
    void createServisProjeTemel() throws Exception {
        int databaseSizeBeforeCreate = servisProjeTemelRepository.findAll().size();
        // Create the ServisProjeTemel
        ServisProjeTemelDTO servisProjeTemelDTO = servisProjeTemelMapper.toDto(servisProjeTemel);
        restServisProjeTemelMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(servisProjeTemelDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ServisProjeTemel in the database
        List<ServisProjeTemel> servisProjeTemelList = servisProjeTemelRepository.findAll();
        assertThat(servisProjeTemelList).hasSize(databaseSizeBeforeCreate + 1);
        ServisProjeTemel testServisProjeTemel = servisProjeTemelList.get(servisProjeTemelList.size() - 1);
        assertThat(testServisProjeTemel.getKullanildigiYer()).isEqualTo(DEFAULT_KULLANILDIGI_YER);
    }

    @Test
    @Transactional
    void createServisProjeTemelWithExistingId() throws Exception {
        // Create the ServisProjeTemel with an existing ID
        servisProjeTemel.setId(1L);
        ServisProjeTemelDTO servisProjeTemelDTO = servisProjeTemelMapper.toDto(servisProjeTemel);

        int databaseSizeBeforeCreate = servisProjeTemelRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restServisProjeTemelMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(servisProjeTemelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServisProjeTemel in the database
        List<ServisProjeTemel> servisProjeTemelList = servisProjeTemelRepository.findAll();
        assertThat(servisProjeTemelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkKullanildigiYerIsRequired() throws Exception {
        int databaseSizeBeforeTest = servisProjeTemelRepository.findAll().size();
        // set the field null
        servisProjeTemel.setKullanildigiYer(null);

        // Create the ServisProjeTemel, which fails.
        ServisProjeTemelDTO servisProjeTemelDTO = servisProjeTemelMapper.toDto(servisProjeTemel);

        restServisProjeTemelMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(servisProjeTemelDTO))
            )
            .andExpect(status().isBadRequest());

        List<ServisProjeTemel> servisProjeTemelList = servisProjeTemelRepository.findAll();
        assertThat(servisProjeTemelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllServisProjeTemels() throws Exception {
        // Initialize the database
        servisProjeTemelRepository.saveAndFlush(servisProjeTemel);

        // Get all the servisProjeTemelList
        restServisProjeTemelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(servisProjeTemel.getId().intValue())))
            .andExpect(jsonPath("$.[*].kullanildigiYer").value(hasItem(DEFAULT_KULLANILDIGI_YER)));
    }

    @Test
    @Transactional
    void getServisProjeTemel() throws Exception {
        // Initialize the database
        servisProjeTemelRepository.saveAndFlush(servisProjeTemel);

        // Get the servisProjeTemel
        restServisProjeTemelMockMvc
            .perform(get(ENTITY_API_URL_ID, servisProjeTemel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(servisProjeTemel.getId().intValue()))
            .andExpect(jsonPath("$.kullanildigiYer").value(DEFAULT_KULLANILDIGI_YER));
    }

    @Test
    @Transactional
    void getNonExistingServisProjeTemel() throws Exception {
        // Get the servisProjeTemel
        restServisProjeTemelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewServisProjeTemel() throws Exception {
        // Initialize the database
        servisProjeTemelRepository.saveAndFlush(servisProjeTemel);

        int databaseSizeBeforeUpdate = servisProjeTemelRepository.findAll().size();

        // Update the servisProjeTemel
        ServisProjeTemel updatedServisProjeTemel = servisProjeTemelRepository.findById(servisProjeTemel.getId()).get();
        // Disconnect from session so that the updates on updatedServisProjeTemel are not directly saved in db
        em.detach(updatedServisProjeTemel);
        updatedServisProjeTemel.kullanildigiYer(UPDATED_KULLANILDIGI_YER);
        ServisProjeTemelDTO servisProjeTemelDTO = servisProjeTemelMapper.toDto(updatedServisProjeTemel);

        restServisProjeTemelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, servisProjeTemelDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(servisProjeTemelDTO))
            )
            .andExpect(status().isOk());

        // Validate the ServisProjeTemel in the database
        List<ServisProjeTemel> servisProjeTemelList = servisProjeTemelRepository.findAll();
        assertThat(servisProjeTemelList).hasSize(databaseSizeBeforeUpdate);
        ServisProjeTemel testServisProjeTemel = servisProjeTemelList.get(servisProjeTemelList.size() - 1);
        assertThat(testServisProjeTemel.getKullanildigiYer()).isEqualTo(UPDATED_KULLANILDIGI_YER);
    }

    @Test
    @Transactional
    void putNonExistingServisProjeTemel() throws Exception {
        int databaseSizeBeforeUpdate = servisProjeTemelRepository.findAll().size();
        servisProjeTemel.setId(count.incrementAndGet());

        // Create the ServisProjeTemel
        ServisProjeTemelDTO servisProjeTemelDTO = servisProjeTemelMapper.toDto(servisProjeTemel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServisProjeTemelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, servisProjeTemelDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(servisProjeTemelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServisProjeTemel in the database
        List<ServisProjeTemel> servisProjeTemelList = servisProjeTemelRepository.findAll();
        assertThat(servisProjeTemelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchServisProjeTemel() throws Exception {
        int databaseSizeBeforeUpdate = servisProjeTemelRepository.findAll().size();
        servisProjeTemel.setId(count.incrementAndGet());

        // Create the ServisProjeTemel
        ServisProjeTemelDTO servisProjeTemelDTO = servisProjeTemelMapper.toDto(servisProjeTemel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServisProjeTemelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(servisProjeTemelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServisProjeTemel in the database
        List<ServisProjeTemel> servisProjeTemelList = servisProjeTemelRepository.findAll();
        assertThat(servisProjeTemelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamServisProjeTemel() throws Exception {
        int databaseSizeBeforeUpdate = servisProjeTemelRepository.findAll().size();
        servisProjeTemel.setId(count.incrementAndGet());

        // Create the ServisProjeTemel
        ServisProjeTemelDTO servisProjeTemelDTO = servisProjeTemelMapper.toDto(servisProjeTemel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServisProjeTemelMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(servisProjeTemelDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ServisProjeTemel in the database
        List<ServisProjeTemel> servisProjeTemelList = servisProjeTemelRepository.findAll();
        assertThat(servisProjeTemelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateServisProjeTemelWithPatch() throws Exception {
        // Initialize the database
        servisProjeTemelRepository.saveAndFlush(servisProjeTemel);

        int databaseSizeBeforeUpdate = servisProjeTemelRepository.findAll().size();

        // Update the servisProjeTemel using partial update
        ServisProjeTemel partialUpdatedServisProjeTemel = new ServisProjeTemel();
        partialUpdatedServisProjeTemel.setId(servisProjeTemel.getId());

        restServisProjeTemelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedServisProjeTemel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedServisProjeTemel))
            )
            .andExpect(status().isOk());

        // Validate the ServisProjeTemel in the database
        List<ServisProjeTemel> servisProjeTemelList = servisProjeTemelRepository.findAll();
        assertThat(servisProjeTemelList).hasSize(databaseSizeBeforeUpdate);
        ServisProjeTemel testServisProjeTemel = servisProjeTemelList.get(servisProjeTemelList.size() - 1);
        assertThat(testServisProjeTemel.getKullanildigiYer()).isEqualTo(DEFAULT_KULLANILDIGI_YER);
    }

    @Test
    @Transactional
    void fullUpdateServisProjeTemelWithPatch() throws Exception {
        // Initialize the database
        servisProjeTemelRepository.saveAndFlush(servisProjeTemel);

        int databaseSizeBeforeUpdate = servisProjeTemelRepository.findAll().size();

        // Update the servisProjeTemel using partial update
        ServisProjeTemel partialUpdatedServisProjeTemel = new ServisProjeTemel();
        partialUpdatedServisProjeTemel.setId(servisProjeTemel.getId());

        partialUpdatedServisProjeTemel.kullanildigiYer(UPDATED_KULLANILDIGI_YER);

        restServisProjeTemelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedServisProjeTemel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedServisProjeTemel))
            )
            .andExpect(status().isOk());

        // Validate the ServisProjeTemel in the database
        List<ServisProjeTemel> servisProjeTemelList = servisProjeTemelRepository.findAll();
        assertThat(servisProjeTemelList).hasSize(databaseSizeBeforeUpdate);
        ServisProjeTemel testServisProjeTemel = servisProjeTemelList.get(servisProjeTemelList.size() - 1);
        assertThat(testServisProjeTemel.getKullanildigiYer()).isEqualTo(UPDATED_KULLANILDIGI_YER);
    }

    @Test
    @Transactional
    void patchNonExistingServisProjeTemel() throws Exception {
        int databaseSizeBeforeUpdate = servisProjeTemelRepository.findAll().size();
        servisProjeTemel.setId(count.incrementAndGet());

        // Create the ServisProjeTemel
        ServisProjeTemelDTO servisProjeTemelDTO = servisProjeTemelMapper.toDto(servisProjeTemel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServisProjeTemelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, servisProjeTemelDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(servisProjeTemelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServisProjeTemel in the database
        List<ServisProjeTemel> servisProjeTemelList = servisProjeTemelRepository.findAll();
        assertThat(servisProjeTemelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchServisProjeTemel() throws Exception {
        int databaseSizeBeforeUpdate = servisProjeTemelRepository.findAll().size();
        servisProjeTemel.setId(count.incrementAndGet());

        // Create the ServisProjeTemel
        ServisProjeTemelDTO servisProjeTemelDTO = servisProjeTemelMapper.toDto(servisProjeTemel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServisProjeTemelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(servisProjeTemelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServisProjeTemel in the database
        List<ServisProjeTemel> servisProjeTemelList = servisProjeTemelRepository.findAll();
        assertThat(servisProjeTemelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamServisProjeTemel() throws Exception {
        int databaseSizeBeforeUpdate = servisProjeTemelRepository.findAll().size();
        servisProjeTemel.setId(count.incrementAndGet());

        // Create the ServisProjeTemel
        ServisProjeTemelDTO servisProjeTemelDTO = servisProjeTemelMapper.toDto(servisProjeTemel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServisProjeTemelMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(servisProjeTemelDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ServisProjeTemel in the database
        List<ServisProjeTemel> servisProjeTemelList = servisProjeTemelRepository.findAll();
        assertThat(servisProjeTemelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteServisProjeTemel() throws Exception {
        // Initialize the database
        servisProjeTemelRepository.saveAndFlush(servisProjeTemel);

        int databaseSizeBeforeDelete = servisProjeTemelRepository.findAll().size();

        // Delete the servisProjeTemel
        restServisProjeTemelMockMvc
            .perform(delete(ENTITY_API_URL_ID, servisProjeTemel.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ServisProjeTemel> servisProjeTemelList = servisProjeTemelRepository.findAll();
        assertThat(servisProjeTemelList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
