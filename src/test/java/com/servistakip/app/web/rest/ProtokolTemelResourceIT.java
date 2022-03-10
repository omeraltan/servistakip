package com.servistakip.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.servistakip.app.IntegrationTest;
import com.servistakip.app.domain.ProtokolTemel;
import com.servistakip.app.domain.enumeration.SureDurum;
import com.servistakip.app.repository.ProtokolTemelRepository;
import com.servistakip.app.service.dto.ProtokolTemelDTO;
import com.servistakip.app.service.mapper.ProtokolTemelMapper;
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
 * Integration tests for the {@link ProtokolTemelResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProtokolTemelResourceIT {

    private static final SureDurum DEFAULT_SURE_DURUM = SureDurum.SURELI;
    private static final SureDurum UPDATED_SURE_DURUM = SureDurum.SURESIZ;

    private static final LocalDate DEFAULT_SURE_BITIS_TARIHI = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_SURE_BITIS_TARIHI = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_PROTOKOL_NU = "AAAAAAAAAA";
    private static final String UPDATED_PROTOKOL_NU = "BBBBBBBBBB";

    private static final String DEFAULT_OLUR_YAZI_NU = "AAAAAAAAAA";
    private static final String UPDATED_OLUR_YAZI_NU = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_PROTOKOL_IMZA_TARIHI = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PROTOKOL_IMZA_TARIHI = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_PROTOKOL_ACIKLAMASI = "AAAAAAAAAA";
    private static final String UPDATED_PROTOKOL_ACIKLAMASI = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/protokol-temels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProtokolTemelRepository protokolTemelRepository;

    @Autowired
    private ProtokolTemelMapper protokolTemelMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProtokolTemelMockMvc;

    private ProtokolTemel protokolTemel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProtokolTemel createEntity(EntityManager em) {
        ProtokolTemel protokolTemel = new ProtokolTemel()
            .sureDurum(DEFAULT_SURE_DURUM)
            .sureBitisTarihi(DEFAULT_SURE_BITIS_TARIHI)
            .protokolNu(DEFAULT_PROTOKOL_NU)
            .olurYaziNu(DEFAULT_OLUR_YAZI_NU)
            .protokolImzaTarihi(DEFAULT_PROTOKOL_IMZA_TARIHI)
            .protokolAciklamasi(DEFAULT_PROTOKOL_ACIKLAMASI);
        return protokolTemel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProtokolTemel createUpdatedEntity(EntityManager em) {
        ProtokolTemel protokolTemel = new ProtokolTemel()
            .sureDurum(UPDATED_SURE_DURUM)
            .sureBitisTarihi(UPDATED_SURE_BITIS_TARIHI)
            .protokolNu(UPDATED_PROTOKOL_NU)
            .olurYaziNu(UPDATED_OLUR_YAZI_NU)
            .protokolImzaTarihi(UPDATED_PROTOKOL_IMZA_TARIHI)
            .protokolAciklamasi(UPDATED_PROTOKOL_ACIKLAMASI);
        return protokolTemel;
    }

    @BeforeEach
    public void initTest() {
        protokolTemel = createEntity(em);
    }

    @Test
    @Transactional
    void createProtokolTemel() throws Exception {
        int databaseSizeBeforeCreate = protokolTemelRepository.findAll().size();
        // Create the ProtokolTemel
        ProtokolTemelDTO protokolTemelDTO = protokolTemelMapper.toDto(protokolTemel);
        restProtokolTemelMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(protokolTemelDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ProtokolTemel in the database
        List<ProtokolTemel> protokolTemelList = protokolTemelRepository.findAll();
        assertThat(protokolTemelList).hasSize(databaseSizeBeforeCreate + 1);
        ProtokolTemel testProtokolTemel = protokolTemelList.get(protokolTemelList.size() - 1);
        assertThat(testProtokolTemel.getSureDurum()).isEqualTo(DEFAULT_SURE_DURUM);
        assertThat(testProtokolTemel.getSureBitisTarihi()).isEqualTo(DEFAULT_SURE_BITIS_TARIHI);
        assertThat(testProtokolTemel.getProtokolNu()).isEqualTo(DEFAULT_PROTOKOL_NU);
        assertThat(testProtokolTemel.getOlurYaziNu()).isEqualTo(DEFAULT_OLUR_YAZI_NU);
        assertThat(testProtokolTemel.getProtokolImzaTarihi()).isEqualTo(DEFAULT_PROTOKOL_IMZA_TARIHI);
        assertThat(testProtokolTemel.getProtokolAciklamasi()).isEqualTo(DEFAULT_PROTOKOL_ACIKLAMASI);
    }

    @Test
    @Transactional
    void createProtokolTemelWithExistingId() throws Exception {
        // Create the ProtokolTemel with an existing ID
        protokolTemel.setId(1L);
        ProtokolTemelDTO protokolTemelDTO = protokolTemelMapper.toDto(protokolTemel);

        int databaseSizeBeforeCreate = protokolTemelRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProtokolTemelMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(protokolTemelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProtokolTemel in the database
        List<ProtokolTemel> protokolTemelList = protokolTemelRepository.findAll();
        assertThat(protokolTemelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkSureDurumIsRequired() throws Exception {
        int databaseSizeBeforeTest = protokolTemelRepository.findAll().size();
        // set the field null
        protokolTemel.setSureDurum(null);

        // Create the ProtokolTemel, which fails.
        ProtokolTemelDTO protokolTemelDTO = protokolTemelMapper.toDto(protokolTemel);

        restProtokolTemelMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(protokolTemelDTO))
            )
            .andExpect(status().isBadRequest());

        List<ProtokolTemel> protokolTemelList = protokolTemelRepository.findAll();
        assertThat(protokolTemelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkProtokolNuIsRequired() throws Exception {
        int databaseSizeBeforeTest = protokolTemelRepository.findAll().size();
        // set the field null
        protokolTemel.setProtokolNu(null);

        // Create the ProtokolTemel, which fails.
        ProtokolTemelDTO protokolTemelDTO = protokolTemelMapper.toDto(protokolTemel);

        restProtokolTemelMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(protokolTemelDTO))
            )
            .andExpect(status().isBadRequest());

        List<ProtokolTemel> protokolTemelList = protokolTemelRepository.findAll();
        assertThat(protokolTemelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkOlurYaziNuIsRequired() throws Exception {
        int databaseSizeBeforeTest = protokolTemelRepository.findAll().size();
        // set the field null
        protokolTemel.setOlurYaziNu(null);

        // Create the ProtokolTemel, which fails.
        ProtokolTemelDTO protokolTemelDTO = protokolTemelMapper.toDto(protokolTemel);

        restProtokolTemelMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(protokolTemelDTO))
            )
            .andExpect(status().isBadRequest());

        List<ProtokolTemel> protokolTemelList = protokolTemelRepository.findAll();
        assertThat(protokolTemelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkProtokolImzaTarihiIsRequired() throws Exception {
        int databaseSizeBeforeTest = protokolTemelRepository.findAll().size();
        // set the field null
        protokolTemel.setProtokolImzaTarihi(null);

        // Create the ProtokolTemel, which fails.
        ProtokolTemelDTO protokolTemelDTO = protokolTemelMapper.toDto(protokolTemel);

        restProtokolTemelMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(protokolTemelDTO))
            )
            .andExpect(status().isBadRequest());

        List<ProtokolTemel> protokolTemelList = protokolTemelRepository.findAll();
        assertThat(protokolTemelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllProtokolTemels() throws Exception {
        // Initialize the database
        protokolTemelRepository.saveAndFlush(protokolTemel);

        // Get all the protokolTemelList
        restProtokolTemelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(protokolTemel.getId().intValue())))
            .andExpect(jsonPath("$.[*].sureDurum").value(hasItem(DEFAULT_SURE_DURUM.toString())))
            .andExpect(jsonPath("$.[*].sureBitisTarihi").value(hasItem(DEFAULT_SURE_BITIS_TARIHI.toString())))
            .andExpect(jsonPath("$.[*].protokolNu").value(hasItem(DEFAULT_PROTOKOL_NU)))
            .andExpect(jsonPath("$.[*].olurYaziNu").value(hasItem(DEFAULT_OLUR_YAZI_NU)))
            .andExpect(jsonPath("$.[*].protokolImzaTarihi").value(hasItem(DEFAULT_PROTOKOL_IMZA_TARIHI.toString())))
            .andExpect(jsonPath("$.[*].protokolAciklamasi").value(hasItem(DEFAULT_PROTOKOL_ACIKLAMASI)));
    }

    @Test
    @Transactional
    void getProtokolTemel() throws Exception {
        // Initialize the database
        protokolTemelRepository.saveAndFlush(protokolTemel);

        // Get the protokolTemel
        restProtokolTemelMockMvc
            .perform(get(ENTITY_API_URL_ID, protokolTemel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(protokolTemel.getId().intValue()))
            .andExpect(jsonPath("$.sureDurum").value(DEFAULT_SURE_DURUM.toString()))
            .andExpect(jsonPath("$.sureBitisTarihi").value(DEFAULT_SURE_BITIS_TARIHI.toString()))
            .andExpect(jsonPath("$.protokolNu").value(DEFAULT_PROTOKOL_NU))
            .andExpect(jsonPath("$.olurYaziNu").value(DEFAULT_OLUR_YAZI_NU))
            .andExpect(jsonPath("$.protokolImzaTarihi").value(DEFAULT_PROTOKOL_IMZA_TARIHI.toString()))
            .andExpect(jsonPath("$.protokolAciklamasi").value(DEFAULT_PROTOKOL_ACIKLAMASI));
    }

    @Test
    @Transactional
    void getNonExistingProtokolTemel() throws Exception {
        // Get the protokolTemel
        restProtokolTemelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProtokolTemel() throws Exception {
        // Initialize the database
        protokolTemelRepository.saveAndFlush(protokolTemel);

        int databaseSizeBeforeUpdate = protokolTemelRepository.findAll().size();

        // Update the protokolTemel
        ProtokolTemel updatedProtokolTemel = protokolTemelRepository.findById(protokolTemel.getId()).get();
        // Disconnect from session so that the updates on updatedProtokolTemel are not directly saved in db
        em.detach(updatedProtokolTemel);
        updatedProtokolTemel
            .sureDurum(UPDATED_SURE_DURUM)
            .sureBitisTarihi(UPDATED_SURE_BITIS_TARIHI)
            .protokolNu(UPDATED_PROTOKOL_NU)
            .olurYaziNu(UPDATED_OLUR_YAZI_NU)
            .protokolImzaTarihi(UPDATED_PROTOKOL_IMZA_TARIHI)
            .protokolAciklamasi(UPDATED_PROTOKOL_ACIKLAMASI);
        ProtokolTemelDTO protokolTemelDTO = protokolTemelMapper.toDto(updatedProtokolTemel);

        restProtokolTemelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, protokolTemelDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(protokolTemelDTO))
            )
            .andExpect(status().isOk());

        // Validate the ProtokolTemel in the database
        List<ProtokolTemel> protokolTemelList = protokolTemelRepository.findAll();
        assertThat(protokolTemelList).hasSize(databaseSizeBeforeUpdate);
        ProtokolTemel testProtokolTemel = protokolTemelList.get(protokolTemelList.size() - 1);
        assertThat(testProtokolTemel.getSureDurum()).isEqualTo(UPDATED_SURE_DURUM);
        assertThat(testProtokolTemel.getSureBitisTarihi()).isEqualTo(UPDATED_SURE_BITIS_TARIHI);
        assertThat(testProtokolTemel.getProtokolNu()).isEqualTo(UPDATED_PROTOKOL_NU);
        assertThat(testProtokolTemel.getOlurYaziNu()).isEqualTo(UPDATED_OLUR_YAZI_NU);
        assertThat(testProtokolTemel.getProtokolImzaTarihi()).isEqualTo(UPDATED_PROTOKOL_IMZA_TARIHI);
        assertThat(testProtokolTemel.getProtokolAciklamasi()).isEqualTo(UPDATED_PROTOKOL_ACIKLAMASI);
    }

    @Test
    @Transactional
    void putNonExistingProtokolTemel() throws Exception {
        int databaseSizeBeforeUpdate = protokolTemelRepository.findAll().size();
        protokolTemel.setId(count.incrementAndGet());

        // Create the ProtokolTemel
        ProtokolTemelDTO protokolTemelDTO = protokolTemelMapper.toDto(protokolTemel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProtokolTemelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, protokolTemelDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(protokolTemelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProtokolTemel in the database
        List<ProtokolTemel> protokolTemelList = protokolTemelRepository.findAll();
        assertThat(protokolTemelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProtokolTemel() throws Exception {
        int databaseSizeBeforeUpdate = protokolTemelRepository.findAll().size();
        protokolTemel.setId(count.incrementAndGet());

        // Create the ProtokolTemel
        ProtokolTemelDTO protokolTemelDTO = protokolTemelMapper.toDto(protokolTemel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProtokolTemelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(protokolTemelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProtokolTemel in the database
        List<ProtokolTemel> protokolTemelList = protokolTemelRepository.findAll();
        assertThat(protokolTemelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProtokolTemel() throws Exception {
        int databaseSizeBeforeUpdate = protokolTemelRepository.findAll().size();
        protokolTemel.setId(count.incrementAndGet());

        // Create the ProtokolTemel
        ProtokolTemelDTO protokolTemelDTO = protokolTemelMapper.toDto(protokolTemel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProtokolTemelMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(protokolTemelDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProtokolTemel in the database
        List<ProtokolTemel> protokolTemelList = protokolTemelRepository.findAll();
        assertThat(protokolTemelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProtokolTemelWithPatch() throws Exception {
        // Initialize the database
        protokolTemelRepository.saveAndFlush(protokolTemel);

        int databaseSizeBeforeUpdate = protokolTemelRepository.findAll().size();

        // Update the protokolTemel using partial update
        ProtokolTemel partialUpdatedProtokolTemel = new ProtokolTemel();
        partialUpdatedProtokolTemel.setId(protokolTemel.getId());

        partialUpdatedProtokolTemel
            .sureDurum(UPDATED_SURE_DURUM)
            .sureBitisTarihi(UPDATED_SURE_BITIS_TARIHI)
            .olurYaziNu(UPDATED_OLUR_YAZI_NU)
            .protokolImzaTarihi(UPDATED_PROTOKOL_IMZA_TARIHI);

        restProtokolTemelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProtokolTemel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProtokolTemel))
            )
            .andExpect(status().isOk());

        // Validate the ProtokolTemel in the database
        List<ProtokolTemel> protokolTemelList = protokolTemelRepository.findAll();
        assertThat(protokolTemelList).hasSize(databaseSizeBeforeUpdate);
        ProtokolTemel testProtokolTemel = protokolTemelList.get(protokolTemelList.size() - 1);
        assertThat(testProtokolTemel.getSureDurum()).isEqualTo(UPDATED_SURE_DURUM);
        assertThat(testProtokolTemel.getSureBitisTarihi()).isEqualTo(UPDATED_SURE_BITIS_TARIHI);
        assertThat(testProtokolTemel.getProtokolNu()).isEqualTo(DEFAULT_PROTOKOL_NU);
        assertThat(testProtokolTemel.getOlurYaziNu()).isEqualTo(UPDATED_OLUR_YAZI_NU);
        assertThat(testProtokolTemel.getProtokolImzaTarihi()).isEqualTo(UPDATED_PROTOKOL_IMZA_TARIHI);
        assertThat(testProtokolTemel.getProtokolAciklamasi()).isEqualTo(DEFAULT_PROTOKOL_ACIKLAMASI);
    }

    @Test
    @Transactional
    void fullUpdateProtokolTemelWithPatch() throws Exception {
        // Initialize the database
        protokolTemelRepository.saveAndFlush(protokolTemel);

        int databaseSizeBeforeUpdate = protokolTemelRepository.findAll().size();

        // Update the protokolTemel using partial update
        ProtokolTemel partialUpdatedProtokolTemel = new ProtokolTemel();
        partialUpdatedProtokolTemel.setId(protokolTemel.getId());

        partialUpdatedProtokolTemel
            .sureDurum(UPDATED_SURE_DURUM)
            .sureBitisTarihi(UPDATED_SURE_BITIS_TARIHI)
            .protokolNu(UPDATED_PROTOKOL_NU)
            .olurYaziNu(UPDATED_OLUR_YAZI_NU)
            .protokolImzaTarihi(UPDATED_PROTOKOL_IMZA_TARIHI)
            .protokolAciklamasi(UPDATED_PROTOKOL_ACIKLAMASI);

        restProtokolTemelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProtokolTemel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProtokolTemel))
            )
            .andExpect(status().isOk());

        // Validate the ProtokolTemel in the database
        List<ProtokolTemel> protokolTemelList = protokolTemelRepository.findAll();
        assertThat(protokolTemelList).hasSize(databaseSizeBeforeUpdate);
        ProtokolTemel testProtokolTemel = protokolTemelList.get(protokolTemelList.size() - 1);
        assertThat(testProtokolTemel.getSureDurum()).isEqualTo(UPDATED_SURE_DURUM);
        assertThat(testProtokolTemel.getSureBitisTarihi()).isEqualTo(UPDATED_SURE_BITIS_TARIHI);
        assertThat(testProtokolTemel.getProtokolNu()).isEqualTo(UPDATED_PROTOKOL_NU);
        assertThat(testProtokolTemel.getOlurYaziNu()).isEqualTo(UPDATED_OLUR_YAZI_NU);
        assertThat(testProtokolTemel.getProtokolImzaTarihi()).isEqualTo(UPDATED_PROTOKOL_IMZA_TARIHI);
        assertThat(testProtokolTemel.getProtokolAciklamasi()).isEqualTo(UPDATED_PROTOKOL_ACIKLAMASI);
    }

    @Test
    @Transactional
    void patchNonExistingProtokolTemel() throws Exception {
        int databaseSizeBeforeUpdate = protokolTemelRepository.findAll().size();
        protokolTemel.setId(count.incrementAndGet());

        // Create the ProtokolTemel
        ProtokolTemelDTO protokolTemelDTO = protokolTemelMapper.toDto(protokolTemel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProtokolTemelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, protokolTemelDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(protokolTemelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProtokolTemel in the database
        List<ProtokolTemel> protokolTemelList = protokolTemelRepository.findAll();
        assertThat(protokolTemelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProtokolTemel() throws Exception {
        int databaseSizeBeforeUpdate = protokolTemelRepository.findAll().size();
        protokolTemel.setId(count.incrementAndGet());

        // Create the ProtokolTemel
        ProtokolTemelDTO protokolTemelDTO = protokolTemelMapper.toDto(protokolTemel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProtokolTemelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(protokolTemelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProtokolTemel in the database
        List<ProtokolTemel> protokolTemelList = protokolTemelRepository.findAll();
        assertThat(protokolTemelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProtokolTemel() throws Exception {
        int databaseSizeBeforeUpdate = protokolTemelRepository.findAll().size();
        protokolTemel.setId(count.incrementAndGet());

        // Create the ProtokolTemel
        ProtokolTemelDTO protokolTemelDTO = protokolTemelMapper.toDto(protokolTemel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProtokolTemelMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(protokolTemelDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProtokolTemel in the database
        List<ProtokolTemel> protokolTemelList = protokolTemelRepository.findAll();
        assertThat(protokolTemelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProtokolTemel() throws Exception {
        // Initialize the database
        protokolTemelRepository.saveAndFlush(protokolTemel);

        int databaseSizeBeforeDelete = protokolTemelRepository.findAll().size();

        // Delete the protokolTemel
        restProtokolTemelMockMvc
            .perform(delete(ENTITY_API_URL_ID, protokolTemel.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProtokolTemel> protokolTemelList = protokolTemelRepository.findAll();
        assertThat(protokolTemelList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
