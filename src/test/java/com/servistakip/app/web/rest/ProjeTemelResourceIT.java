package com.servistakip.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.servistakip.app.IntegrationTest;
import com.servistakip.app.domain.ProjeTemel;
import com.servistakip.app.repository.ProjeTemelRepository;
import com.servistakip.app.service.dto.ProjeTemelDTO;
import com.servistakip.app.service.mapper.ProjeTemelMapper;
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
 * Integration tests for the {@link ProjeTemelResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProjeTemelResourceIT {

    private static final String DEFAULT_PROJEDI = "AAAAAAAAAA";
    private static final String UPDATED_PROJEDI = "BBBBBBBBBB";

    private static final String DEFAULT_PROJE_KODU = "AAAAAAAAAA";
    private static final String UPDATED_PROJE_KODU = "BBBBBBBBBB";

    private static final String DEFAULT_ACIKLAMA = "AAAAAAAAAA";
    private static final String UPDATED_ACIKLAMA = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/proje-temels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProjeTemelRepository projeTemelRepository;

    @Autowired
    private ProjeTemelMapper projeTemelMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProjeTemelMockMvc;

    private ProjeTemel projeTemel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProjeTemel createEntity(EntityManager em) {
        ProjeTemel projeTemel = new ProjeTemel().projedi(DEFAULT_PROJEDI).projeKodu(DEFAULT_PROJE_KODU).aciklama(DEFAULT_ACIKLAMA);
        return projeTemel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProjeTemel createUpdatedEntity(EntityManager em) {
        ProjeTemel projeTemel = new ProjeTemel().projedi(UPDATED_PROJEDI).projeKodu(UPDATED_PROJE_KODU).aciklama(UPDATED_ACIKLAMA);
        return projeTemel;
    }

    @BeforeEach
    public void initTest() {
        projeTemel = createEntity(em);
    }

    @Test
    @Transactional
    void createProjeTemel() throws Exception {
        int databaseSizeBeforeCreate = projeTemelRepository.findAll().size();
        // Create the ProjeTemel
        ProjeTemelDTO projeTemelDTO = projeTemelMapper.toDto(projeTemel);
        restProjeTemelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(projeTemelDTO)))
            .andExpect(status().isCreated());

        // Validate the ProjeTemel in the database
        List<ProjeTemel> projeTemelList = projeTemelRepository.findAll();
        assertThat(projeTemelList).hasSize(databaseSizeBeforeCreate + 1);
        ProjeTemel testProjeTemel = projeTemelList.get(projeTemelList.size() - 1);
        assertThat(testProjeTemel.getProjedi()).isEqualTo(DEFAULT_PROJEDI);
        assertThat(testProjeTemel.getProjeKodu()).isEqualTo(DEFAULT_PROJE_KODU);
        assertThat(testProjeTemel.getAciklama()).isEqualTo(DEFAULT_ACIKLAMA);
    }

    @Test
    @Transactional
    void createProjeTemelWithExistingId() throws Exception {
        // Create the ProjeTemel with an existing ID
        projeTemel.setId(1L);
        ProjeTemelDTO projeTemelDTO = projeTemelMapper.toDto(projeTemel);

        int databaseSizeBeforeCreate = projeTemelRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjeTemelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(projeTemelDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProjeTemel in the database
        List<ProjeTemel> projeTemelList = projeTemelRepository.findAll();
        assertThat(projeTemelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkProjediIsRequired() throws Exception {
        int databaseSizeBeforeTest = projeTemelRepository.findAll().size();
        // set the field null
        projeTemel.setProjedi(null);

        // Create the ProjeTemel, which fails.
        ProjeTemelDTO projeTemelDTO = projeTemelMapper.toDto(projeTemel);

        restProjeTemelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(projeTemelDTO)))
            .andExpect(status().isBadRequest());

        List<ProjeTemel> projeTemelList = projeTemelRepository.findAll();
        assertThat(projeTemelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAciklamaIsRequired() throws Exception {
        int databaseSizeBeforeTest = projeTemelRepository.findAll().size();
        // set the field null
        projeTemel.setAciklama(null);

        // Create the ProjeTemel, which fails.
        ProjeTemelDTO projeTemelDTO = projeTemelMapper.toDto(projeTemel);

        restProjeTemelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(projeTemelDTO)))
            .andExpect(status().isBadRequest());

        List<ProjeTemel> projeTemelList = projeTemelRepository.findAll();
        assertThat(projeTemelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllProjeTemels() throws Exception {
        // Initialize the database
        projeTemelRepository.saveAndFlush(projeTemel);

        // Get all the projeTemelList
        restProjeTemelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projeTemel.getId().intValue())))
            .andExpect(jsonPath("$.[*].projedi").value(hasItem(DEFAULT_PROJEDI)))
            .andExpect(jsonPath("$.[*].projeKodu").value(hasItem(DEFAULT_PROJE_KODU)))
            .andExpect(jsonPath("$.[*].aciklama").value(hasItem(DEFAULT_ACIKLAMA)));
    }

    @Test
    @Transactional
    void getProjeTemel() throws Exception {
        // Initialize the database
        projeTemelRepository.saveAndFlush(projeTemel);

        // Get the projeTemel
        restProjeTemelMockMvc
            .perform(get(ENTITY_API_URL_ID, projeTemel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(projeTemel.getId().intValue()))
            .andExpect(jsonPath("$.projedi").value(DEFAULT_PROJEDI))
            .andExpect(jsonPath("$.projeKodu").value(DEFAULT_PROJE_KODU))
            .andExpect(jsonPath("$.aciklama").value(DEFAULT_ACIKLAMA));
    }

    @Test
    @Transactional
    void getNonExistingProjeTemel() throws Exception {
        // Get the projeTemel
        restProjeTemelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProjeTemel() throws Exception {
        // Initialize the database
        projeTemelRepository.saveAndFlush(projeTemel);

        int databaseSizeBeforeUpdate = projeTemelRepository.findAll().size();

        // Update the projeTemel
        ProjeTemel updatedProjeTemel = projeTemelRepository.findById(projeTemel.getId()).get();
        // Disconnect from session so that the updates on updatedProjeTemel are not directly saved in db
        em.detach(updatedProjeTemel);
        updatedProjeTemel.projedi(UPDATED_PROJEDI).projeKodu(UPDATED_PROJE_KODU).aciklama(UPDATED_ACIKLAMA);
        ProjeTemelDTO projeTemelDTO = projeTemelMapper.toDto(updatedProjeTemel);

        restProjeTemelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, projeTemelDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projeTemelDTO))
            )
            .andExpect(status().isOk());

        // Validate the ProjeTemel in the database
        List<ProjeTemel> projeTemelList = projeTemelRepository.findAll();
        assertThat(projeTemelList).hasSize(databaseSizeBeforeUpdate);
        ProjeTemel testProjeTemel = projeTemelList.get(projeTemelList.size() - 1);
        assertThat(testProjeTemel.getProjedi()).isEqualTo(UPDATED_PROJEDI);
        assertThat(testProjeTemel.getProjeKodu()).isEqualTo(UPDATED_PROJE_KODU);
        assertThat(testProjeTemel.getAciklama()).isEqualTo(UPDATED_ACIKLAMA);
    }

    @Test
    @Transactional
    void putNonExistingProjeTemel() throws Exception {
        int databaseSizeBeforeUpdate = projeTemelRepository.findAll().size();
        projeTemel.setId(count.incrementAndGet());

        // Create the ProjeTemel
        ProjeTemelDTO projeTemelDTO = projeTemelMapper.toDto(projeTemel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjeTemelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, projeTemelDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projeTemelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjeTemel in the database
        List<ProjeTemel> projeTemelList = projeTemelRepository.findAll();
        assertThat(projeTemelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProjeTemel() throws Exception {
        int databaseSizeBeforeUpdate = projeTemelRepository.findAll().size();
        projeTemel.setId(count.incrementAndGet());

        // Create the ProjeTemel
        ProjeTemelDTO projeTemelDTO = projeTemelMapper.toDto(projeTemel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjeTemelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projeTemelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjeTemel in the database
        List<ProjeTemel> projeTemelList = projeTemelRepository.findAll();
        assertThat(projeTemelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProjeTemel() throws Exception {
        int databaseSizeBeforeUpdate = projeTemelRepository.findAll().size();
        projeTemel.setId(count.incrementAndGet());

        // Create the ProjeTemel
        ProjeTemelDTO projeTemelDTO = projeTemelMapper.toDto(projeTemel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjeTemelMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(projeTemelDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProjeTemel in the database
        List<ProjeTemel> projeTemelList = projeTemelRepository.findAll();
        assertThat(projeTemelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProjeTemelWithPatch() throws Exception {
        // Initialize the database
        projeTemelRepository.saveAndFlush(projeTemel);

        int databaseSizeBeforeUpdate = projeTemelRepository.findAll().size();

        // Update the projeTemel using partial update
        ProjeTemel partialUpdatedProjeTemel = new ProjeTemel();
        partialUpdatedProjeTemel.setId(projeTemel.getId());

        partialUpdatedProjeTemel.projedi(UPDATED_PROJEDI).aciklama(UPDATED_ACIKLAMA);

        restProjeTemelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProjeTemel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProjeTemel))
            )
            .andExpect(status().isOk());

        // Validate the ProjeTemel in the database
        List<ProjeTemel> projeTemelList = projeTemelRepository.findAll();
        assertThat(projeTemelList).hasSize(databaseSizeBeforeUpdate);
        ProjeTemel testProjeTemel = projeTemelList.get(projeTemelList.size() - 1);
        assertThat(testProjeTemel.getProjedi()).isEqualTo(UPDATED_PROJEDI);
        assertThat(testProjeTemel.getProjeKodu()).isEqualTo(DEFAULT_PROJE_KODU);
        assertThat(testProjeTemel.getAciklama()).isEqualTo(UPDATED_ACIKLAMA);
    }

    @Test
    @Transactional
    void fullUpdateProjeTemelWithPatch() throws Exception {
        // Initialize the database
        projeTemelRepository.saveAndFlush(projeTemel);

        int databaseSizeBeforeUpdate = projeTemelRepository.findAll().size();

        // Update the projeTemel using partial update
        ProjeTemel partialUpdatedProjeTemel = new ProjeTemel();
        partialUpdatedProjeTemel.setId(projeTemel.getId());

        partialUpdatedProjeTemel.projedi(UPDATED_PROJEDI).projeKodu(UPDATED_PROJE_KODU).aciklama(UPDATED_ACIKLAMA);

        restProjeTemelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProjeTemel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProjeTemel))
            )
            .andExpect(status().isOk());

        // Validate the ProjeTemel in the database
        List<ProjeTemel> projeTemelList = projeTemelRepository.findAll();
        assertThat(projeTemelList).hasSize(databaseSizeBeforeUpdate);
        ProjeTemel testProjeTemel = projeTemelList.get(projeTemelList.size() - 1);
        assertThat(testProjeTemel.getProjedi()).isEqualTo(UPDATED_PROJEDI);
        assertThat(testProjeTemel.getProjeKodu()).isEqualTo(UPDATED_PROJE_KODU);
        assertThat(testProjeTemel.getAciklama()).isEqualTo(UPDATED_ACIKLAMA);
    }

    @Test
    @Transactional
    void patchNonExistingProjeTemel() throws Exception {
        int databaseSizeBeforeUpdate = projeTemelRepository.findAll().size();
        projeTemel.setId(count.incrementAndGet());

        // Create the ProjeTemel
        ProjeTemelDTO projeTemelDTO = projeTemelMapper.toDto(projeTemel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjeTemelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, projeTemelDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(projeTemelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjeTemel in the database
        List<ProjeTemel> projeTemelList = projeTemelRepository.findAll();
        assertThat(projeTemelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProjeTemel() throws Exception {
        int databaseSizeBeforeUpdate = projeTemelRepository.findAll().size();
        projeTemel.setId(count.incrementAndGet());

        // Create the ProjeTemel
        ProjeTemelDTO projeTemelDTO = projeTemelMapper.toDto(projeTemel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjeTemelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(projeTemelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjeTemel in the database
        List<ProjeTemel> projeTemelList = projeTemelRepository.findAll();
        assertThat(projeTemelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProjeTemel() throws Exception {
        int databaseSizeBeforeUpdate = projeTemelRepository.findAll().size();
        projeTemel.setId(count.incrementAndGet());

        // Create the ProjeTemel
        ProjeTemelDTO projeTemelDTO = projeTemelMapper.toDto(projeTemel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjeTemelMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(projeTemelDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProjeTemel in the database
        List<ProjeTemel> projeTemelList = projeTemelRepository.findAll();
        assertThat(projeTemelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProjeTemel() throws Exception {
        // Initialize the database
        projeTemelRepository.saveAndFlush(projeTemel);

        int databaseSizeBeforeDelete = projeTemelRepository.findAll().size();

        // Delete the projeTemel
        restProjeTemelMockMvc
            .perform(delete(ENTITY_API_URL_ID, projeTemel.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProjeTemel> projeTemelList = projeTemelRepository.findAll();
        assertThat(projeTemelList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
