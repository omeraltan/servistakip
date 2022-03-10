package com.servistakip.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.servistakip.app.IntegrationTest;
import com.servistakip.app.domain.TabloTemel;
import com.servistakip.app.repository.TabloTemelRepository;
import com.servistakip.app.service.dto.TabloTemelDTO;
import com.servistakip.app.service.mapper.TabloTemelMapper;
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
 * Integration tests for the {@link TabloTemelResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TabloTemelResourceIT {

    private static final String DEFAULT_GERCEK_VERITABANI = "AAAAAAAAAA";
    private static final String UPDATED_GERCEK_VERITABANI = "BBBBBBBBBB";

    private static final String DEFAULT_GERCEK_LOG_TABLO = "AAAAAAAAAA";
    private static final String UPDATED_GERCEK_LOG_TABLO = "BBBBBBBBBB";

    private static final String DEFAULT_GERCEKACIKLAMASI = "AAAAAAAAAA";
    private static final String UPDATED_GERCEKACIKLAMASI = "BBBBBBBBBB";

    private static final String DEFAULT_TEST_VERITABANI = "AAAAAAAAAA";
    private static final String UPDATED_TEST_VERITABANI = "BBBBBBBBBB";

    private static final String DEFAULT_TEST_LOG_TABLO = "AAAAAAAAAA";
    private static final String UPDATED_TEST_LOG_TABLO = "BBBBBBBBBB";

    private static final String DEFAULT_TESTACIKLAMASI = "AAAAAAAAAA";
    private static final String UPDATED_TESTACIKLAMASI = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/tablo-temels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TabloTemelRepository tabloTemelRepository;

    @Autowired
    private TabloTemelMapper tabloTemelMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTabloTemelMockMvc;

    private TabloTemel tabloTemel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TabloTemel createEntity(EntityManager em) {
        TabloTemel tabloTemel = new TabloTemel()
            .gercekVeritabani(DEFAULT_GERCEK_VERITABANI)
            .gercekLogTablo(DEFAULT_GERCEK_LOG_TABLO)
            .gercekaciklamasi(DEFAULT_GERCEKACIKLAMASI)
            .testVeritabani(DEFAULT_TEST_VERITABANI)
            .testLogTablo(DEFAULT_TEST_LOG_TABLO)
            .testaciklamasi(DEFAULT_TESTACIKLAMASI);
        return tabloTemel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TabloTemel createUpdatedEntity(EntityManager em) {
        TabloTemel tabloTemel = new TabloTemel()
            .gercekVeritabani(UPDATED_GERCEK_VERITABANI)
            .gercekLogTablo(UPDATED_GERCEK_LOG_TABLO)
            .gercekaciklamasi(UPDATED_GERCEKACIKLAMASI)
            .testVeritabani(UPDATED_TEST_VERITABANI)
            .testLogTablo(UPDATED_TEST_LOG_TABLO)
            .testaciklamasi(UPDATED_TESTACIKLAMASI);
        return tabloTemel;
    }

    @BeforeEach
    public void initTest() {
        tabloTemel = createEntity(em);
    }

    @Test
    @Transactional
    void createTabloTemel() throws Exception {
        int databaseSizeBeforeCreate = tabloTemelRepository.findAll().size();
        // Create the TabloTemel
        TabloTemelDTO tabloTemelDTO = tabloTemelMapper.toDto(tabloTemel);
        restTabloTemelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tabloTemelDTO)))
            .andExpect(status().isCreated());

        // Validate the TabloTemel in the database
        List<TabloTemel> tabloTemelList = tabloTemelRepository.findAll();
        assertThat(tabloTemelList).hasSize(databaseSizeBeforeCreate + 1);
        TabloTemel testTabloTemel = tabloTemelList.get(tabloTemelList.size() - 1);
        assertThat(testTabloTemel.getGercekVeritabani()).isEqualTo(DEFAULT_GERCEK_VERITABANI);
        assertThat(testTabloTemel.getGercekLogTablo()).isEqualTo(DEFAULT_GERCEK_LOG_TABLO);
        assertThat(testTabloTemel.getGercekaciklamasi()).isEqualTo(DEFAULT_GERCEKACIKLAMASI);
        assertThat(testTabloTemel.getTestVeritabani()).isEqualTo(DEFAULT_TEST_VERITABANI);
        assertThat(testTabloTemel.getTestLogTablo()).isEqualTo(DEFAULT_TEST_LOG_TABLO);
        assertThat(testTabloTemel.getTestaciklamasi()).isEqualTo(DEFAULT_TESTACIKLAMASI);
    }

    @Test
    @Transactional
    void createTabloTemelWithExistingId() throws Exception {
        // Create the TabloTemel with an existing ID
        tabloTemel.setId(1L);
        TabloTemelDTO tabloTemelDTO = tabloTemelMapper.toDto(tabloTemel);

        int databaseSizeBeforeCreate = tabloTemelRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTabloTemelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tabloTemelDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TabloTemel in the database
        List<TabloTemel> tabloTemelList = tabloTemelRepository.findAll();
        assertThat(tabloTemelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkGercekVeritabaniIsRequired() throws Exception {
        int databaseSizeBeforeTest = tabloTemelRepository.findAll().size();
        // set the field null
        tabloTemel.setGercekVeritabani(null);

        // Create the TabloTemel, which fails.
        TabloTemelDTO tabloTemelDTO = tabloTemelMapper.toDto(tabloTemel);

        restTabloTemelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tabloTemelDTO)))
            .andExpect(status().isBadRequest());

        List<TabloTemel> tabloTemelList = tabloTemelRepository.findAll();
        assertThat(tabloTemelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkGercekLogTabloIsRequired() throws Exception {
        int databaseSizeBeforeTest = tabloTemelRepository.findAll().size();
        // set the field null
        tabloTemel.setGercekLogTablo(null);

        // Create the TabloTemel, which fails.
        TabloTemelDTO tabloTemelDTO = tabloTemelMapper.toDto(tabloTemel);

        restTabloTemelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tabloTemelDTO)))
            .andExpect(status().isBadRequest());

        List<TabloTemel> tabloTemelList = tabloTemelRepository.findAll();
        assertThat(tabloTemelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTestVeritabaniIsRequired() throws Exception {
        int databaseSizeBeforeTest = tabloTemelRepository.findAll().size();
        // set the field null
        tabloTemel.setTestVeritabani(null);

        // Create the TabloTemel, which fails.
        TabloTemelDTO tabloTemelDTO = tabloTemelMapper.toDto(tabloTemel);

        restTabloTemelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tabloTemelDTO)))
            .andExpect(status().isBadRequest());

        List<TabloTemel> tabloTemelList = tabloTemelRepository.findAll();
        assertThat(tabloTemelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTestLogTabloIsRequired() throws Exception {
        int databaseSizeBeforeTest = tabloTemelRepository.findAll().size();
        // set the field null
        tabloTemel.setTestLogTablo(null);

        // Create the TabloTemel, which fails.
        TabloTemelDTO tabloTemelDTO = tabloTemelMapper.toDto(tabloTemel);

        restTabloTemelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tabloTemelDTO)))
            .andExpect(status().isBadRequest());

        List<TabloTemel> tabloTemelList = tabloTemelRepository.findAll();
        assertThat(tabloTemelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTabloTemels() throws Exception {
        // Initialize the database
        tabloTemelRepository.saveAndFlush(tabloTemel);

        // Get all the tabloTemelList
        restTabloTemelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tabloTemel.getId().intValue())))
            .andExpect(jsonPath("$.[*].gercekVeritabani").value(hasItem(DEFAULT_GERCEK_VERITABANI)))
            .andExpect(jsonPath("$.[*].gercekLogTablo").value(hasItem(DEFAULT_GERCEK_LOG_TABLO)))
            .andExpect(jsonPath("$.[*].gercekaciklamasi").value(hasItem(DEFAULT_GERCEKACIKLAMASI)))
            .andExpect(jsonPath("$.[*].testVeritabani").value(hasItem(DEFAULT_TEST_VERITABANI)))
            .andExpect(jsonPath("$.[*].testLogTablo").value(hasItem(DEFAULT_TEST_LOG_TABLO)))
            .andExpect(jsonPath("$.[*].testaciklamasi").value(hasItem(DEFAULT_TESTACIKLAMASI)));
    }

    @Test
    @Transactional
    void getTabloTemel() throws Exception {
        // Initialize the database
        tabloTemelRepository.saveAndFlush(tabloTemel);

        // Get the tabloTemel
        restTabloTemelMockMvc
            .perform(get(ENTITY_API_URL_ID, tabloTemel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tabloTemel.getId().intValue()))
            .andExpect(jsonPath("$.gercekVeritabani").value(DEFAULT_GERCEK_VERITABANI))
            .andExpect(jsonPath("$.gercekLogTablo").value(DEFAULT_GERCEK_LOG_TABLO))
            .andExpect(jsonPath("$.gercekaciklamasi").value(DEFAULT_GERCEKACIKLAMASI))
            .andExpect(jsonPath("$.testVeritabani").value(DEFAULT_TEST_VERITABANI))
            .andExpect(jsonPath("$.testLogTablo").value(DEFAULT_TEST_LOG_TABLO))
            .andExpect(jsonPath("$.testaciklamasi").value(DEFAULT_TESTACIKLAMASI));
    }

    @Test
    @Transactional
    void getNonExistingTabloTemel() throws Exception {
        // Get the tabloTemel
        restTabloTemelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTabloTemel() throws Exception {
        // Initialize the database
        tabloTemelRepository.saveAndFlush(tabloTemel);

        int databaseSizeBeforeUpdate = tabloTemelRepository.findAll().size();

        // Update the tabloTemel
        TabloTemel updatedTabloTemel = tabloTemelRepository.findById(tabloTemel.getId()).get();
        // Disconnect from session so that the updates on updatedTabloTemel are not directly saved in db
        em.detach(updatedTabloTemel);
        updatedTabloTemel
            .gercekVeritabani(UPDATED_GERCEK_VERITABANI)
            .gercekLogTablo(UPDATED_GERCEK_LOG_TABLO)
            .gercekaciklamasi(UPDATED_GERCEKACIKLAMASI)
            .testVeritabani(UPDATED_TEST_VERITABANI)
            .testLogTablo(UPDATED_TEST_LOG_TABLO)
            .testaciklamasi(UPDATED_TESTACIKLAMASI);
        TabloTemelDTO tabloTemelDTO = tabloTemelMapper.toDto(updatedTabloTemel);

        restTabloTemelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tabloTemelDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tabloTemelDTO))
            )
            .andExpect(status().isOk());

        // Validate the TabloTemel in the database
        List<TabloTemel> tabloTemelList = tabloTemelRepository.findAll();
        assertThat(tabloTemelList).hasSize(databaseSizeBeforeUpdate);
        TabloTemel testTabloTemel = tabloTemelList.get(tabloTemelList.size() - 1);
        assertThat(testTabloTemel.getGercekVeritabani()).isEqualTo(UPDATED_GERCEK_VERITABANI);
        assertThat(testTabloTemel.getGercekLogTablo()).isEqualTo(UPDATED_GERCEK_LOG_TABLO);
        assertThat(testTabloTemel.getGercekaciklamasi()).isEqualTo(UPDATED_GERCEKACIKLAMASI);
        assertThat(testTabloTemel.getTestVeritabani()).isEqualTo(UPDATED_TEST_VERITABANI);
        assertThat(testTabloTemel.getTestLogTablo()).isEqualTo(UPDATED_TEST_LOG_TABLO);
        assertThat(testTabloTemel.getTestaciklamasi()).isEqualTo(UPDATED_TESTACIKLAMASI);
    }

    @Test
    @Transactional
    void putNonExistingTabloTemel() throws Exception {
        int databaseSizeBeforeUpdate = tabloTemelRepository.findAll().size();
        tabloTemel.setId(count.incrementAndGet());

        // Create the TabloTemel
        TabloTemelDTO tabloTemelDTO = tabloTemelMapper.toDto(tabloTemel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTabloTemelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tabloTemelDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tabloTemelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TabloTemel in the database
        List<TabloTemel> tabloTemelList = tabloTemelRepository.findAll();
        assertThat(tabloTemelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTabloTemel() throws Exception {
        int databaseSizeBeforeUpdate = tabloTemelRepository.findAll().size();
        tabloTemel.setId(count.incrementAndGet());

        // Create the TabloTemel
        TabloTemelDTO tabloTemelDTO = tabloTemelMapper.toDto(tabloTemel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTabloTemelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tabloTemelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TabloTemel in the database
        List<TabloTemel> tabloTemelList = tabloTemelRepository.findAll();
        assertThat(tabloTemelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTabloTemel() throws Exception {
        int databaseSizeBeforeUpdate = tabloTemelRepository.findAll().size();
        tabloTemel.setId(count.incrementAndGet());

        // Create the TabloTemel
        TabloTemelDTO tabloTemelDTO = tabloTemelMapper.toDto(tabloTemel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTabloTemelMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tabloTemelDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TabloTemel in the database
        List<TabloTemel> tabloTemelList = tabloTemelRepository.findAll();
        assertThat(tabloTemelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTabloTemelWithPatch() throws Exception {
        // Initialize the database
        tabloTemelRepository.saveAndFlush(tabloTemel);

        int databaseSizeBeforeUpdate = tabloTemelRepository.findAll().size();

        // Update the tabloTemel using partial update
        TabloTemel partialUpdatedTabloTemel = new TabloTemel();
        partialUpdatedTabloTemel.setId(tabloTemel.getId());

        partialUpdatedTabloTemel
            .gercekVeritabani(UPDATED_GERCEK_VERITABANI)
            .gercekLogTablo(UPDATED_GERCEK_LOG_TABLO)
            .testLogTablo(UPDATED_TEST_LOG_TABLO)
            .testaciklamasi(UPDATED_TESTACIKLAMASI);

        restTabloTemelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTabloTemel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTabloTemel))
            )
            .andExpect(status().isOk());

        // Validate the TabloTemel in the database
        List<TabloTemel> tabloTemelList = tabloTemelRepository.findAll();
        assertThat(tabloTemelList).hasSize(databaseSizeBeforeUpdate);
        TabloTemel testTabloTemel = tabloTemelList.get(tabloTemelList.size() - 1);
        assertThat(testTabloTemel.getGercekVeritabani()).isEqualTo(UPDATED_GERCEK_VERITABANI);
        assertThat(testTabloTemel.getGercekLogTablo()).isEqualTo(UPDATED_GERCEK_LOG_TABLO);
        assertThat(testTabloTemel.getGercekaciklamasi()).isEqualTo(DEFAULT_GERCEKACIKLAMASI);
        assertThat(testTabloTemel.getTestVeritabani()).isEqualTo(DEFAULT_TEST_VERITABANI);
        assertThat(testTabloTemel.getTestLogTablo()).isEqualTo(UPDATED_TEST_LOG_TABLO);
        assertThat(testTabloTemel.getTestaciklamasi()).isEqualTo(UPDATED_TESTACIKLAMASI);
    }

    @Test
    @Transactional
    void fullUpdateTabloTemelWithPatch() throws Exception {
        // Initialize the database
        tabloTemelRepository.saveAndFlush(tabloTemel);

        int databaseSizeBeforeUpdate = tabloTemelRepository.findAll().size();

        // Update the tabloTemel using partial update
        TabloTemel partialUpdatedTabloTemel = new TabloTemel();
        partialUpdatedTabloTemel.setId(tabloTemel.getId());

        partialUpdatedTabloTemel
            .gercekVeritabani(UPDATED_GERCEK_VERITABANI)
            .gercekLogTablo(UPDATED_GERCEK_LOG_TABLO)
            .gercekaciklamasi(UPDATED_GERCEKACIKLAMASI)
            .testVeritabani(UPDATED_TEST_VERITABANI)
            .testLogTablo(UPDATED_TEST_LOG_TABLO)
            .testaciklamasi(UPDATED_TESTACIKLAMASI);

        restTabloTemelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTabloTemel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTabloTemel))
            )
            .andExpect(status().isOk());

        // Validate the TabloTemel in the database
        List<TabloTemel> tabloTemelList = tabloTemelRepository.findAll();
        assertThat(tabloTemelList).hasSize(databaseSizeBeforeUpdate);
        TabloTemel testTabloTemel = tabloTemelList.get(tabloTemelList.size() - 1);
        assertThat(testTabloTemel.getGercekVeritabani()).isEqualTo(UPDATED_GERCEK_VERITABANI);
        assertThat(testTabloTemel.getGercekLogTablo()).isEqualTo(UPDATED_GERCEK_LOG_TABLO);
        assertThat(testTabloTemel.getGercekaciklamasi()).isEqualTo(UPDATED_GERCEKACIKLAMASI);
        assertThat(testTabloTemel.getTestVeritabani()).isEqualTo(UPDATED_TEST_VERITABANI);
        assertThat(testTabloTemel.getTestLogTablo()).isEqualTo(UPDATED_TEST_LOG_TABLO);
        assertThat(testTabloTemel.getTestaciklamasi()).isEqualTo(UPDATED_TESTACIKLAMASI);
    }

    @Test
    @Transactional
    void patchNonExistingTabloTemel() throws Exception {
        int databaseSizeBeforeUpdate = tabloTemelRepository.findAll().size();
        tabloTemel.setId(count.incrementAndGet());

        // Create the TabloTemel
        TabloTemelDTO tabloTemelDTO = tabloTemelMapper.toDto(tabloTemel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTabloTemelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tabloTemelDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tabloTemelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TabloTemel in the database
        List<TabloTemel> tabloTemelList = tabloTemelRepository.findAll();
        assertThat(tabloTemelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTabloTemel() throws Exception {
        int databaseSizeBeforeUpdate = tabloTemelRepository.findAll().size();
        tabloTemel.setId(count.incrementAndGet());

        // Create the TabloTemel
        TabloTemelDTO tabloTemelDTO = tabloTemelMapper.toDto(tabloTemel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTabloTemelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tabloTemelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TabloTemel in the database
        List<TabloTemel> tabloTemelList = tabloTemelRepository.findAll();
        assertThat(tabloTemelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTabloTemel() throws Exception {
        int databaseSizeBeforeUpdate = tabloTemelRepository.findAll().size();
        tabloTemel.setId(count.incrementAndGet());

        // Create the TabloTemel
        TabloTemelDTO tabloTemelDTO = tabloTemelMapper.toDto(tabloTemel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTabloTemelMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(tabloTemelDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TabloTemel in the database
        List<TabloTemel> tabloTemelList = tabloTemelRepository.findAll();
        assertThat(tabloTemelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTabloTemel() throws Exception {
        // Initialize the database
        tabloTemelRepository.saveAndFlush(tabloTemel);

        int databaseSizeBeforeDelete = tabloTemelRepository.findAll().size();

        // Delete the tabloTemel
        restTabloTemelMockMvc
            .perform(delete(ENTITY_API_URL_ID, tabloTemel.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TabloTemel> tabloTemelList = tabloTemelRepository.findAll();
        assertThat(tabloTemelList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
