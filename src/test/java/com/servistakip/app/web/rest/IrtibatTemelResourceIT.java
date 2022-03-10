package com.servistakip.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.servistakip.app.IntegrationTest;
import com.servistakip.app.domain.IrtibatTemel;
import com.servistakip.app.domain.enumeration.Unvani;
import com.servistakip.app.repository.IrtibatTemelRepository;
import com.servistakip.app.service.dto.IrtibatTemelDTO;
import com.servistakip.app.service.mapper.IrtibatTemelMapper;
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
 * Integration tests for the {@link IrtibatTemelResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class IrtibatTemelResourceIT {

    private static final String DEFAULT_ADI = "AAAAAAAAAA";
    private static final String UPDATED_ADI = "BBBBBBBBBB";

    private static final String DEFAULT_SOYADI = "AAAAAAAAAA";
    private static final String UPDATED_SOYADI = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONU = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONU = "BBBBBBBBBB";

    private static final Integer DEFAULT_TCNU = 1;
    private static final Integer UPDATED_TCNU = 2;

    private static final String DEFAULT_EPOSTA = "AAAAAAAAAA";
    private static final String UPDATED_EPOSTA = "BBBBBBBBBB";

    private static final Unvani DEFAULT_UNVANI = Unvani.DEVLET_MEMURU;
    private static final Unvani UPDATED_UNVANI = Unvani.SOZLESMELI_BILISIM_PERSONELI;

    private static final String ENTITY_API_URL = "/api/irtibat-temels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private IrtibatTemelRepository irtibatTemelRepository;

    @Autowired
    private IrtibatTemelMapper irtibatTemelMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIrtibatTemelMockMvc;

    private IrtibatTemel irtibatTemel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IrtibatTemel createEntity(EntityManager em) {
        IrtibatTemel irtibatTemel = new IrtibatTemel()
            .adi(DEFAULT_ADI)
            .soyadi(DEFAULT_SOYADI)
            .telefonu(DEFAULT_TELEFONU)
            .tcnu(DEFAULT_TCNU)
            .eposta(DEFAULT_EPOSTA)
            .unvani(DEFAULT_UNVANI);
        return irtibatTemel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IrtibatTemel createUpdatedEntity(EntityManager em) {
        IrtibatTemel irtibatTemel = new IrtibatTemel()
            .adi(UPDATED_ADI)
            .soyadi(UPDATED_SOYADI)
            .telefonu(UPDATED_TELEFONU)
            .tcnu(UPDATED_TCNU)
            .eposta(UPDATED_EPOSTA)
            .unvani(UPDATED_UNVANI);
        return irtibatTemel;
    }

    @BeforeEach
    public void initTest() {
        irtibatTemel = createEntity(em);
    }

    @Test
    @Transactional
    void createIrtibatTemel() throws Exception {
        int databaseSizeBeforeCreate = irtibatTemelRepository.findAll().size();
        // Create the IrtibatTemel
        IrtibatTemelDTO irtibatTemelDTO = irtibatTemelMapper.toDto(irtibatTemel);
        restIrtibatTemelMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(irtibatTemelDTO))
            )
            .andExpect(status().isCreated());

        // Validate the IrtibatTemel in the database
        List<IrtibatTemel> irtibatTemelList = irtibatTemelRepository.findAll();
        assertThat(irtibatTemelList).hasSize(databaseSizeBeforeCreate + 1);
        IrtibatTemel testIrtibatTemel = irtibatTemelList.get(irtibatTemelList.size() - 1);
        assertThat(testIrtibatTemel.getAdi()).isEqualTo(DEFAULT_ADI);
        assertThat(testIrtibatTemel.getSoyadi()).isEqualTo(DEFAULT_SOYADI);
        assertThat(testIrtibatTemel.getTelefonu()).isEqualTo(DEFAULT_TELEFONU);
        assertThat(testIrtibatTemel.getTcnu()).isEqualTo(DEFAULT_TCNU);
        assertThat(testIrtibatTemel.getEposta()).isEqualTo(DEFAULT_EPOSTA);
        assertThat(testIrtibatTemel.getUnvani()).isEqualTo(DEFAULT_UNVANI);
    }

    @Test
    @Transactional
    void createIrtibatTemelWithExistingId() throws Exception {
        // Create the IrtibatTemel with an existing ID
        irtibatTemel.setId(1L);
        IrtibatTemelDTO irtibatTemelDTO = irtibatTemelMapper.toDto(irtibatTemel);

        int databaseSizeBeforeCreate = irtibatTemelRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restIrtibatTemelMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(irtibatTemelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IrtibatTemel in the database
        List<IrtibatTemel> irtibatTemelList = irtibatTemelRepository.findAll();
        assertThat(irtibatTemelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkAdiIsRequired() throws Exception {
        int databaseSizeBeforeTest = irtibatTemelRepository.findAll().size();
        // set the field null
        irtibatTemel.setAdi(null);

        // Create the IrtibatTemel, which fails.
        IrtibatTemelDTO irtibatTemelDTO = irtibatTemelMapper.toDto(irtibatTemel);

        restIrtibatTemelMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(irtibatTemelDTO))
            )
            .andExpect(status().isBadRequest());

        List<IrtibatTemel> irtibatTemelList = irtibatTemelRepository.findAll();
        assertThat(irtibatTemelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSoyadiIsRequired() throws Exception {
        int databaseSizeBeforeTest = irtibatTemelRepository.findAll().size();
        // set the field null
        irtibatTemel.setSoyadi(null);

        // Create the IrtibatTemel, which fails.
        IrtibatTemelDTO irtibatTemelDTO = irtibatTemelMapper.toDto(irtibatTemel);

        restIrtibatTemelMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(irtibatTemelDTO))
            )
            .andExpect(status().isBadRequest());

        List<IrtibatTemel> irtibatTemelList = irtibatTemelRepository.findAll();
        assertThat(irtibatTemelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTelefonuIsRequired() throws Exception {
        int databaseSizeBeforeTest = irtibatTemelRepository.findAll().size();
        // set the field null
        irtibatTemel.setTelefonu(null);

        // Create the IrtibatTemel, which fails.
        IrtibatTemelDTO irtibatTemelDTO = irtibatTemelMapper.toDto(irtibatTemel);

        restIrtibatTemelMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(irtibatTemelDTO))
            )
            .andExpect(status().isBadRequest());

        List<IrtibatTemel> irtibatTemelList = irtibatTemelRepository.findAll();
        assertThat(irtibatTemelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTcnuIsRequired() throws Exception {
        int databaseSizeBeforeTest = irtibatTemelRepository.findAll().size();
        // set the field null
        irtibatTemel.setTcnu(null);

        // Create the IrtibatTemel, which fails.
        IrtibatTemelDTO irtibatTemelDTO = irtibatTemelMapper.toDto(irtibatTemel);

        restIrtibatTemelMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(irtibatTemelDTO))
            )
            .andExpect(status().isBadRequest());

        List<IrtibatTemel> irtibatTemelList = irtibatTemelRepository.findAll();
        assertThat(irtibatTemelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEpostaIsRequired() throws Exception {
        int databaseSizeBeforeTest = irtibatTemelRepository.findAll().size();
        // set the field null
        irtibatTemel.setEposta(null);

        // Create the IrtibatTemel, which fails.
        IrtibatTemelDTO irtibatTemelDTO = irtibatTemelMapper.toDto(irtibatTemel);

        restIrtibatTemelMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(irtibatTemelDTO))
            )
            .andExpect(status().isBadRequest());

        List<IrtibatTemel> irtibatTemelList = irtibatTemelRepository.findAll();
        assertThat(irtibatTemelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUnvaniIsRequired() throws Exception {
        int databaseSizeBeforeTest = irtibatTemelRepository.findAll().size();
        // set the field null
        irtibatTemel.setUnvani(null);

        // Create the IrtibatTemel, which fails.
        IrtibatTemelDTO irtibatTemelDTO = irtibatTemelMapper.toDto(irtibatTemel);

        restIrtibatTemelMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(irtibatTemelDTO))
            )
            .andExpect(status().isBadRequest());

        List<IrtibatTemel> irtibatTemelList = irtibatTemelRepository.findAll();
        assertThat(irtibatTemelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllIrtibatTemels() throws Exception {
        // Initialize the database
        irtibatTemelRepository.saveAndFlush(irtibatTemel);

        // Get all the irtibatTemelList
        restIrtibatTemelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(irtibatTemel.getId().intValue())))
            .andExpect(jsonPath("$.[*].adi").value(hasItem(DEFAULT_ADI)))
            .andExpect(jsonPath("$.[*].soyadi").value(hasItem(DEFAULT_SOYADI)))
            .andExpect(jsonPath("$.[*].telefonu").value(hasItem(DEFAULT_TELEFONU)))
            .andExpect(jsonPath("$.[*].tcnu").value(hasItem(DEFAULT_TCNU)))
            .andExpect(jsonPath("$.[*].eposta").value(hasItem(DEFAULT_EPOSTA)))
            .andExpect(jsonPath("$.[*].unvani").value(hasItem(DEFAULT_UNVANI.toString())));
    }

    @Test
    @Transactional
    void getIrtibatTemel() throws Exception {
        // Initialize the database
        irtibatTemelRepository.saveAndFlush(irtibatTemel);

        // Get the irtibatTemel
        restIrtibatTemelMockMvc
            .perform(get(ENTITY_API_URL_ID, irtibatTemel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(irtibatTemel.getId().intValue()))
            .andExpect(jsonPath("$.adi").value(DEFAULT_ADI))
            .andExpect(jsonPath("$.soyadi").value(DEFAULT_SOYADI))
            .andExpect(jsonPath("$.telefonu").value(DEFAULT_TELEFONU))
            .andExpect(jsonPath("$.tcnu").value(DEFAULT_TCNU))
            .andExpect(jsonPath("$.eposta").value(DEFAULT_EPOSTA))
            .andExpect(jsonPath("$.unvani").value(DEFAULT_UNVANI.toString()));
    }

    @Test
    @Transactional
    void getNonExistingIrtibatTemel() throws Exception {
        // Get the irtibatTemel
        restIrtibatTemelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewIrtibatTemel() throws Exception {
        // Initialize the database
        irtibatTemelRepository.saveAndFlush(irtibatTemel);

        int databaseSizeBeforeUpdate = irtibatTemelRepository.findAll().size();

        // Update the irtibatTemel
        IrtibatTemel updatedIrtibatTemel = irtibatTemelRepository.findById(irtibatTemel.getId()).get();
        // Disconnect from session so that the updates on updatedIrtibatTemel are not directly saved in db
        em.detach(updatedIrtibatTemel);
        updatedIrtibatTemel
            .adi(UPDATED_ADI)
            .soyadi(UPDATED_SOYADI)
            .telefonu(UPDATED_TELEFONU)
            .tcnu(UPDATED_TCNU)
            .eposta(UPDATED_EPOSTA)
            .unvani(UPDATED_UNVANI);
        IrtibatTemelDTO irtibatTemelDTO = irtibatTemelMapper.toDto(updatedIrtibatTemel);

        restIrtibatTemelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, irtibatTemelDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(irtibatTemelDTO))
            )
            .andExpect(status().isOk());

        // Validate the IrtibatTemel in the database
        List<IrtibatTemel> irtibatTemelList = irtibatTemelRepository.findAll();
        assertThat(irtibatTemelList).hasSize(databaseSizeBeforeUpdate);
        IrtibatTemel testIrtibatTemel = irtibatTemelList.get(irtibatTemelList.size() - 1);
        assertThat(testIrtibatTemel.getAdi()).isEqualTo(UPDATED_ADI);
        assertThat(testIrtibatTemel.getSoyadi()).isEqualTo(UPDATED_SOYADI);
        assertThat(testIrtibatTemel.getTelefonu()).isEqualTo(UPDATED_TELEFONU);
        assertThat(testIrtibatTemel.getTcnu()).isEqualTo(UPDATED_TCNU);
        assertThat(testIrtibatTemel.getEposta()).isEqualTo(UPDATED_EPOSTA);
        assertThat(testIrtibatTemel.getUnvani()).isEqualTo(UPDATED_UNVANI);
    }

    @Test
    @Transactional
    void putNonExistingIrtibatTemel() throws Exception {
        int databaseSizeBeforeUpdate = irtibatTemelRepository.findAll().size();
        irtibatTemel.setId(count.incrementAndGet());

        // Create the IrtibatTemel
        IrtibatTemelDTO irtibatTemelDTO = irtibatTemelMapper.toDto(irtibatTemel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIrtibatTemelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, irtibatTemelDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(irtibatTemelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IrtibatTemel in the database
        List<IrtibatTemel> irtibatTemelList = irtibatTemelRepository.findAll();
        assertThat(irtibatTemelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchIrtibatTemel() throws Exception {
        int databaseSizeBeforeUpdate = irtibatTemelRepository.findAll().size();
        irtibatTemel.setId(count.incrementAndGet());

        // Create the IrtibatTemel
        IrtibatTemelDTO irtibatTemelDTO = irtibatTemelMapper.toDto(irtibatTemel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIrtibatTemelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(irtibatTemelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IrtibatTemel in the database
        List<IrtibatTemel> irtibatTemelList = irtibatTemelRepository.findAll();
        assertThat(irtibatTemelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamIrtibatTemel() throws Exception {
        int databaseSizeBeforeUpdate = irtibatTemelRepository.findAll().size();
        irtibatTemel.setId(count.incrementAndGet());

        // Create the IrtibatTemel
        IrtibatTemelDTO irtibatTemelDTO = irtibatTemelMapper.toDto(irtibatTemel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIrtibatTemelMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(irtibatTemelDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the IrtibatTemel in the database
        List<IrtibatTemel> irtibatTemelList = irtibatTemelRepository.findAll();
        assertThat(irtibatTemelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateIrtibatTemelWithPatch() throws Exception {
        // Initialize the database
        irtibatTemelRepository.saveAndFlush(irtibatTemel);

        int databaseSizeBeforeUpdate = irtibatTemelRepository.findAll().size();

        // Update the irtibatTemel using partial update
        IrtibatTemel partialUpdatedIrtibatTemel = new IrtibatTemel();
        partialUpdatedIrtibatTemel.setId(irtibatTemel.getId());

        partialUpdatedIrtibatTemel.soyadi(UPDATED_SOYADI).telefonu(UPDATED_TELEFONU).tcnu(UPDATED_TCNU).eposta(UPDATED_EPOSTA);

        restIrtibatTemelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIrtibatTemel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIrtibatTemel))
            )
            .andExpect(status().isOk());

        // Validate the IrtibatTemel in the database
        List<IrtibatTemel> irtibatTemelList = irtibatTemelRepository.findAll();
        assertThat(irtibatTemelList).hasSize(databaseSizeBeforeUpdate);
        IrtibatTemel testIrtibatTemel = irtibatTemelList.get(irtibatTemelList.size() - 1);
        assertThat(testIrtibatTemel.getAdi()).isEqualTo(DEFAULT_ADI);
        assertThat(testIrtibatTemel.getSoyadi()).isEqualTo(UPDATED_SOYADI);
        assertThat(testIrtibatTemel.getTelefonu()).isEqualTo(UPDATED_TELEFONU);
        assertThat(testIrtibatTemel.getTcnu()).isEqualTo(UPDATED_TCNU);
        assertThat(testIrtibatTemel.getEposta()).isEqualTo(UPDATED_EPOSTA);
        assertThat(testIrtibatTemel.getUnvani()).isEqualTo(DEFAULT_UNVANI);
    }

    @Test
    @Transactional
    void fullUpdateIrtibatTemelWithPatch() throws Exception {
        // Initialize the database
        irtibatTemelRepository.saveAndFlush(irtibatTemel);

        int databaseSizeBeforeUpdate = irtibatTemelRepository.findAll().size();

        // Update the irtibatTemel using partial update
        IrtibatTemel partialUpdatedIrtibatTemel = new IrtibatTemel();
        partialUpdatedIrtibatTemel.setId(irtibatTemel.getId());

        partialUpdatedIrtibatTemel
            .adi(UPDATED_ADI)
            .soyadi(UPDATED_SOYADI)
            .telefonu(UPDATED_TELEFONU)
            .tcnu(UPDATED_TCNU)
            .eposta(UPDATED_EPOSTA)
            .unvani(UPDATED_UNVANI);

        restIrtibatTemelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIrtibatTemel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIrtibatTemel))
            )
            .andExpect(status().isOk());

        // Validate the IrtibatTemel in the database
        List<IrtibatTemel> irtibatTemelList = irtibatTemelRepository.findAll();
        assertThat(irtibatTemelList).hasSize(databaseSizeBeforeUpdate);
        IrtibatTemel testIrtibatTemel = irtibatTemelList.get(irtibatTemelList.size() - 1);
        assertThat(testIrtibatTemel.getAdi()).isEqualTo(UPDATED_ADI);
        assertThat(testIrtibatTemel.getSoyadi()).isEqualTo(UPDATED_SOYADI);
        assertThat(testIrtibatTemel.getTelefonu()).isEqualTo(UPDATED_TELEFONU);
        assertThat(testIrtibatTemel.getTcnu()).isEqualTo(UPDATED_TCNU);
        assertThat(testIrtibatTemel.getEposta()).isEqualTo(UPDATED_EPOSTA);
        assertThat(testIrtibatTemel.getUnvani()).isEqualTo(UPDATED_UNVANI);
    }

    @Test
    @Transactional
    void patchNonExistingIrtibatTemel() throws Exception {
        int databaseSizeBeforeUpdate = irtibatTemelRepository.findAll().size();
        irtibatTemel.setId(count.incrementAndGet());

        // Create the IrtibatTemel
        IrtibatTemelDTO irtibatTemelDTO = irtibatTemelMapper.toDto(irtibatTemel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIrtibatTemelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, irtibatTemelDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(irtibatTemelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IrtibatTemel in the database
        List<IrtibatTemel> irtibatTemelList = irtibatTemelRepository.findAll();
        assertThat(irtibatTemelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchIrtibatTemel() throws Exception {
        int databaseSizeBeforeUpdate = irtibatTemelRepository.findAll().size();
        irtibatTemel.setId(count.incrementAndGet());

        // Create the IrtibatTemel
        IrtibatTemelDTO irtibatTemelDTO = irtibatTemelMapper.toDto(irtibatTemel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIrtibatTemelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(irtibatTemelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IrtibatTemel in the database
        List<IrtibatTemel> irtibatTemelList = irtibatTemelRepository.findAll();
        assertThat(irtibatTemelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamIrtibatTemel() throws Exception {
        int databaseSizeBeforeUpdate = irtibatTemelRepository.findAll().size();
        irtibatTemel.setId(count.incrementAndGet());

        // Create the IrtibatTemel
        IrtibatTemelDTO irtibatTemelDTO = irtibatTemelMapper.toDto(irtibatTemel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIrtibatTemelMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(irtibatTemelDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the IrtibatTemel in the database
        List<IrtibatTemel> irtibatTemelList = irtibatTemelRepository.findAll();
        assertThat(irtibatTemelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteIrtibatTemel() throws Exception {
        // Initialize the database
        irtibatTemelRepository.saveAndFlush(irtibatTemel);

        int databaseSizeBeforeDelete = irtibatTemelRepository.findAll().size();

        // Delete the irtibatTemel
        restIrtibatTemelMockMvc
            .perform(delete(ENTITY_API_URL_ID, irtibatTemel.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<IrtibatTemel> irtibatTemelList = irtibatTemelRepository.findAll();
        assertThat(irtibatTemelList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
