package com.servistakip.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.servistakip.app.IntegrationTest;
import com.servistakip.app.domain.Personel;
import com.servistakip.app.domain.enumeration.Unvani;
import com.servistakip.app.repository.PersonelRepository;
import com.servistakip.app.service.dto.PersonelDTO;
import com.servistakip.app.service.mapper.PersonelMapper;
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
 * Integration tests for the {@link PersonelResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PersonelResourceIT {

    private static final String DEFAULT_ADI = "AAAAAAAAAA";
    private static final String UPDATED_ADI = "BBBBBBBBBB";

    private static final String DEFAULT_SOYADI = "AAAAAAAAAA";
    private static final String UPDATED_SOYADI = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONU = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONU = "BBBBBBBBBB";

    private static final String DEFAULT_EPOSTA = "AAAAAAAAAA";
    private static final String UPDATED_EPOSTA = "BBBBBBBBBB";

    private static final Unvani DEFAULT_UNVANI = Unvani.DEVLET_MEMURU;
    private static final Unvani UPDATED_UNVANI = Unvani.SOZLESMELI_BILISIM_PERSONELI;

    private static final String ENTITY_API_URL = "/api/personels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PersonelRepository personelRepository;

    @Autowired
    private PersonelMapper personelMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPersonelMockMvc;

    private Personel personel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Personel createEntity(EntityManager em) {
        Personel personel = new Personel()
            .adi(DEFAULT_ADI)
            .soyadi(DEFAULT_SOYADI)
            .telefonu(DEFAULT_TELEFONU)
            .eposta(DEFAULT_EPOSTA)
            .unvani(DEFAULT_UNVANI);
        return personel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Personel createUpdatedEntity(EntityManager em) {
        Personel personel = new Personel()
            .adi(UPDATED_ADI)
            .soyadi(UPDATED_SOYADI)
            .telefonu(UPDATED_TELEFONU)
            .eposta(UPDATED_EPOSTA)
            .unvani(UPDATED_UNVANI);
        return personel;
    }

    @BeforeEach
    public void initTest() {
        personel = createEntity(em);
    }

    @Test
    @Transactional
    void createPersonel() throws Exception {
        int databaseSizeBeforeCreate = personelRepository.findAll().size();
        // Create the Personel
        PersonelDTO personelDTO = personelMapper.toDto(personel);
        restPersonelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(personelDTO)))
            .andExpect(status().isCreated());

        // Validate the Personel in the database
        List<Personel> personelList = personelRepository.findAll();
        assertThat(personelList).hasSize(databaseSizeBeforeCreate + 1);
        Personel testPersonel = personelList.get(personelList.size() - 1);
        assertThat(testPersonel.getAdi()).isEqualTo(DEFAULT_ADI);
        assertThat(testPersonel.getSoyadi()).isEqualTo(DEFAULT_SOYADI);
        assertThat(testPersonel.getTelefonu()).isEqualTo(DEFAULT_TELEFONU);
        assertThat(testPersonel.getEposta()).isEqualTo(DEFAULT_EPOSTA);
        assertThat(testPersonel.getUnvani()).isEqualTo(DEFAULT_UNVANI);
    }

    @Test
    @Transactional
    void createPersonelWithExistingId() throws Exception {
        // Create the Personel with an existing ID
        personel.setId(1L);
        PersonelDTO personelDTO = personelMapper.toDto(personel);

        int databaseSizeBeforeCreate = personelRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(personelDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Personel in the database
        List<Personel> personelList = personelRepository.findAll();
        assertThat(personelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkAdiIsRequired() throws Exception {
        int databaseSizeBeforeTest = personelRepository.findAll().size();
        // set the field null
        personel.setAdi(null);

        // Create the Personel, which fails.
        PersonelDTO personelDTO = personelMapper.toDto(personel);

        restPersonelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(personelDTO)))
            .andExpect(status().isBadRequest());

        List<Personel> personelList = personelRepository.findAll();
        assertThat(personelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSoyadiIsRequired() throws Exception {
        int databaseSizeBeforeTest = personelRepository.findAll().size();
        // set the field null
        personel.setSoyadi(null);

        // Create the Personel, which fails.
        PersonelDTO personelDTO = personelMapper.toDto(personel);

        restPersonelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(personelDTO)))
            .andExpect(status().isBadRequest());

        List<Personel> personelList = personelRepository.findAll();
        assertThat(personelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTelefonuIsRequired() throws Exception {
        int databaseSizeBeforeTest = personelRepository.findAll().size();
        // set the field null
        personel.setTelefonu(null);

        // Create the Personel, which fails.
        PersonelDTO personelDTO = personelMapper.toDto(personel);

        restPersonelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(personelDTO)))
            .andExpect(status().isBadRequest());

        List<Personel> personelList = personelRepository.findAll();
        assertThat(personelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUnvaniIsRequired() throws Exception {
        int databaseSizeBeforeTest = personelRepository.findAll().size();
        // set the field null
        personel.setUnvani(null);

        // Create the Personel, which fails.
        PersonelDTO personelDTO = personelMapper.toDto(personel);

        restPersonelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(personelDTO)))
            .andExpect(status().isBadRequest());

        List<Personel> personelList = personelRepository.findAll();
        assertThat(personelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPersonels() throws Exception {
        // Initialize the database
        personelRepository.saveAndFlush(personel);

        // Get all the personelList
        restPersonelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personel.getId().intValue())))
            .andExpect(jsonPath("$.[*].adi").value(hasItem(DEFAULT_ADI)))
            .andExpect(jsonPath("$.[*].soyadi").value(hasItem(DEFAULT_SOYADI)))
            .andExpect(jsonPath("$.[*].telefonu").value(hasItem(DEFAULT_TELEFONU)))
            .andExpect(jsonPath("$.[*].eposta").value(hasItem(DEFAULT_EPOSTA)))
            .andExpect(jsonPath("$.[*].unvani").value(hasItem(DEFAULT_UNVANI.toString())));
    }

    @Test
    @Transactional
    void getPersonel() throws Exception {
        // Initialize the database
        personelRepository.saveAndFlush(personel);

        // Get the personel
        restPersonelMockMvc
            .perform(get(ENTITY_API_URL_ID, personel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(personel.getId().intValue()))
            .andExpect(jsonPath("$.adi").value(DEFAULT_ADI))
            .andExpect(jsonPath("$.soyadi").value(DEFAULT_SOYADI))
            .andExpect(jsonPath("$.telefonu").value(DEFAULT_TELEFONU))
            .andExpect(jsonPath("$.eposta").value(DEFAULT_EPOSTA))
            .andExpect(jsonPath("$.unvani").value(DEFAULT_UNVANI.toString()));
    }

    @Test
    @Transactional
    void getNonExistingPersonel() throws Exception {
        // Get the personel
        restPersonelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPersonel() throws Exception {
        // Initialize the database
        personelRepository.saveAndFlush(personel);

        int databaseSizeBeforeUpdate = personelRepository.findAll().size();

        // Update the personel
        Personel updatedPersonel = personelRepository.findById(personel.getId()).get();
        // Disconnect from session so that the updates on updatedPersonel are not directly saved in db
        em.detach(updatedPersonel);
        updatedPersonel.adi(UPDATED_ADI).soyadi(UPDATED_SOYADI).telefonu(UPDATED_TELEFONU).eposta(UPDATED_EPOSTA).unvani(UPDATED_UNVANI);
        PersonelDTO personelDTO = personelMapper.toDto(updatedPersonel);

        restPersonelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, personelDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(personelDTO))
            )
            .andExpect(status().isOk());

        // Validate the Personel in the database
        List<Personel> personelList = personelRepository.findAll();
        assertThat(personelList).hasSize(databaseSizeBeforeUpdate);
        Personel testPersonel = personelList.get(personelList.size() - 1);
        assertThat(testPersonel.getAdi()).isEqualTo(UPDATED_ADI);
        assertThat(testPersonel.getSoyadi()).isEqualTo(UPDATED_SOYADI);
        assertThat(testPersonel.getTelefonu()).isEqualTo(UPDATED_TELEFONU);
        assertThat(testPersonel.getEposta()).isEqualTo(UPDATED_EPOSTA);
        assertThat(testPersonel.getUnvani()).isEqualTo(UPDATED_UNVANI);
    }

    @Test
    @Transactional
    void putNonExistingPersonel() throws Exception {
        int databaseSizeBeforeUpdate = personelRepository.findAll().size();
        personel.setId(count.incrementAndGet());

        // Create the Personel
        PersonelDTO personelDTO = personelMapper.toDto(personel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, personelDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(personelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Personel in the database
        List<Personel> personelList = personelRepository.findAll();
        assertThat(personelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPersonel() throws Exception {
        int databaseSizeBeforeUpdate = personelRepository.findAll().size();
        personel.setId(count.incrementAndGet());

        // Create the Personel
        PersonelDTO personelDTO = personelMapper.toDto(personel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(personelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Personel in the database
        List<Personel> personelList = personelRepository.findAll();
        assertThat(personelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPersonel() throws Exception {
        int databaseSizeBeforeUpdate = personelRepository.findAll().size();
        personel.setId(count.incrementAndGet());

        // Create the Personel
        PersonelDTO personelDTO = personelMapper.toDto(personel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonelMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(personelDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Personel in the database
        List<Personel> personelList = personelRepository.findAll();
        assertThat(personelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePersonelWithPatch() throws Exception {
        // Initialize the database
        personelRepository.saveAndFlush(personel);

        int databaseSizeBeforeUpdate = personelRepository.findAll().size();

        // Update the personel using partial update
        Personel partialUpdatedPersonel = new Personel();
        partialUpdatedPersonel.setId(personel.getId());

        restPersonelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPersonel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPersonel))
            )
            .andExpect(status().isOk());

        // Validate the Personel in the database
        List<Personel> personelList = personelRepository.findAll();
        assertThat(personelList).hasSize(databaseSizeBeforeUpdate);
        Personel testPersonel = personelList.get(personelList.size() - 1);
        assertThat(testPersonel.getAdi()).isEqualTo(DEFAULT_ADI);
        assertThat(testPersonel.getSoyadi()).isEqualTo(DEFAULT_SOYADI);
        assertThat(testPersonel.getTelefonu()).isEqualTo(DEFAULT_TELEFONU);
        assertThat(testPersonel.getEposta()).isEqualTo(DEFAULT_EPOSTA);
        assertThat(testPersonel.getUnvani()).isEqualTo(DEFAULT_UNVANI);
    }

    @Test
    @Transactional
    void fullUpdatePersonelWithPatch() throws Exception {
        // Initialize the database
        personelRepository.saveAndFlush(personel);

        int databaseSizeBeforeUpdate = personelRepository.findAll().size();

        // Update the personel using partial update
        Personel partialUpdatedPersonel = new Personel();
        partialUpdatedPersonel.setId(personel.getId());

        partialUpdatedPersonel
            .adi(UPDATED_ADI)
            .soyadi(UPDATED_SOYADI)
            .telefonu(UPDATED_TELEFONU)
            .eposta(UPDATED_EPOSTA)
            .unvani(UPDATED_UNVANI);

        restPersonelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPersonel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPersonel))
            )
            .andExpect(status().isOk());

        // Validate the Personel in the database
        List<Personel> personelList = personelRepository.findAll();
        assertThat(personelList).hasSize(databaseSizeBeforeUpdate);
        Personel testPersonel = personelList.get(personelList.size() - 1);
        assertThat(testPersonel.getAdi()).isEqualTo(UPDATED_ADI);
        assertThat(testPersonel.getSoyadi()).isEqualTo(UPDATED_SOYADI);
        assertThat(testPersonel.getTelefonu()).isEqualTo(UPDATED_TELEFONU);
        assertThat(testPersonel.getEposta()).isEqualTo(UPDATED_EPOSTA);
        assertThat(testPersonel.getUnvani()).isEqualTo(UPDATED_UNVANI);
    }

    @Test
    @Transactional
    void patchNonExistingPersonel() throws Exception {
        int databaseSizeBeforeUpdate = personelRepository.findAll().size();
        personel.setId(count.incrementAndGet());

        // Create the Personel
        PersonelDTO personelDTO = personelMapper.toDto(personel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, personelDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(personelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Personel in the database
        List<Personel> personelList = personelRepository.findAll();
        assertThat(personelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPersonel() throws Exception {
        int databaseSizeBeforeUpdate = personelRepository.findAll().size();
        personel.setId(count.incrementAndGet());

        // Create the Personel
        PersonelDTO personelDTO = personelMapper.toDto(personel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(personelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Personel in the database
        List<Personel> personelList = personelRepository.findAll();
        assertThat(personelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPersonel() throws Exception {
        int databaseSizeBeforeUpdate = personelRepository.findAll().size();
        personel.setId(count.incrementAndGet());

        // Create the Personel
        PersonelDTO personelDTO = personelMapper.toDto(personel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonelMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(personelDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Personel in the database
        List<Personel> personelList = personelRepository.findAll();
        assertThat(personelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePersonel() throws Exception {
        // Initialize the database
        personelRepository.saveAndFlush(personel);

        int databaseSizeBeforeDelete = personelRepository.findAll().size();

        // Delete the personel
        restPersonelMockMvc
            .perform(delete(ENTITY_API_URL_ID, personel.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Personel> personelList = personelRepository.findAll();
        assertThat(personelList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
