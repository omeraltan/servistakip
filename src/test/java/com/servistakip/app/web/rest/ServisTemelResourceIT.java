package com.servistakip.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.servistakip.app.IntegrationTest;
import com.servistakip.app.domain.ServisTemel;
import com.servistakip.app.domain.enumeration.BaglantiSekli;
import com.servistakip.app.domain.enumeration.ServisDurum;
import com.servistakip.app.domain.enumeration.ServisSekli;
import com.servistakip.app.domain.enumeration.ServisTipi;
import com.servistakip.app.repository.ServisTemelRepository;
import com.servistakip.app.service.dto.ServisTemelDTO;
import com.servistakip.app.service.mapper.ServisTemelMapper;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link ServisTemelResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ServisTemelResourceIT {

    private static final String DEFAULT_SERVIS_ADI = "AAAAAAAAAA";
    private static final String UPDATED_SERVIS_ADI = "BBBBBBBBBB";

    private static final String DEFAULT_SERVIS_URL = "AAAAAAAAAA";
    private static final String UPDATED_SERVIS_URL = "BBBBBBBBBB";

    private static final String DEFAULT_SERVIS_VERI_TIP = "AAAAAAAAAA";
    private static final String UPDATED_SERVIS_VERI_TIP = "BBBBBBBBBB";

    private static final String DEFAULT_ACIKLAMASI = "AAAAAAAAAA";
    private static final String UPDATED_ACIKLAMASI = "BBBBBBBBBB";

    private static final byte[] DEFAULT_DOSYASI = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_DOSYASI = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_DOSYASI_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_DOSYASI_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_RESMI = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_RESMI = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_RESMI_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_RESMI_CONTENT_TYPE = "image/png";

    private static final ServisSekli DEFAULT_SERVIS_SEKLI = ServisSekli.KURUMDAN_ALINAN;
    private static final ServisSekli UPDATED_SERVIS_SEKLI = ServisSekli.KURUMA_VERILEN;

    private static final ServisTipi DEFAULT_SERVIS_TIPI = ServisTipi.WEB;
    private static final ServisTipi UPDATED_SERVIS_TIPI = ServisTipi.WMS;

    private static final BaglantiSekli DEFAULT_BAGLANTI_SEKLI = BaglantiSekli.ACIK_INTERNET;
    private static final BaglantiSekli UPDATED_BAGLANTI_SEKLI = BaglantiSekli.KAMUNET;

    private static final ServisDurum DEFAULT_SERVIS_DURUM = ServisDurum.PLANLANAN;
    private static final ServisDurum UPDATED_SERVIS_DURUM = ServisDurum.DEVAM_EDEN;

    private static final String DEFAULT_ANLIK_SORGU = "AAAAAAAAAA";
    private static final String UPDATED_ANLIK_SORGU = "BBBBBBBBBB";

    private static final String DEFAULT_YIGIN_VERI = "AAAAAAAAAA";
    private static final String UPDATED_YIGIN_VERI = "BBBBBBBBBB";

    private static final String DEFAULT_GDS = "AAAAAAAAAA";
    private static final String UPDATED_GDS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/servis-temels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ServisTemelRepository servisTemelRepository;

    @Autowired
    private ServisTemelMapper servisTemelMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restServisTemelMockMvc;

    private ServisTemel servisTemel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServisTemel createEntity(EntityManager em) {
        ServisTemel servisTemel = new ServisTemel()
            .servisAdi(DEFAULT_SERVIS_ADI)
            .servisUrl(DEFAULT_SERVIS_URL)
            .servisVeriTip(DEFAULT_SERVIS_VERI_TIP)
            .aciklamasi(DEFAULT_ACIKLAMASI)
            .dosyasi(DEFAULT_DOSYASI)
            .dosyasiContentType(DEFAULT_DOSYASI_CONTENT_TYPE)
            .resmi(DEFAULT_RESMI)
            .resmiContentType(DEFAULT_RESMI_CONTENT_TYPE)
            .servisSekli(DEFAULT_SERVIS_SEKLI)
            .servisTipi(DEFAULT_SERVIS_TIPI)
            .baglantiSekli(DEFAULT_BAGLANTI_SEKLI)
            .servisDurum(DEFAULT_SERVIS_DURUM)
            .anlikSorgu(DEFAULT_ANLIK_SORGU)
            .yiginVeri(DEFAULT_YIGIN_VERI)
            .gds(DEFAULT_GDS);
        return servisTemel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServisTemel createUpdatedEntity(EntityManager em) {
        ServisTemel servisTemel = new ServisTemel()
            .servisAdi(UPDATED_SERVIS_ADI)
            .servisUrl(UPDATED_SERVIS_URL)
            .servisVeriTip(UPDATED_SERVIS_VERI_TIP)
            .aciklamasi(UPDATED_ACIKLAMASI)
            .dosyasi(UPDATED_DOSYASI)
            .dosyasiContentType(UPDATED_DOSYASI_CONTENT_TYPE)
            .resmi(UPDATED_RESMI)
            .resmiContentType(UPDATED_RESMI_CONTENT_TYPE)
            .servisSekli(UPDATED_SERVIS_SEKLI)
            .servisTipi(UPDATED_SERVIS_TIPI)
            .baglantiSekli(UPDATED_BAGLANTI_SEKLI)
            .servisDurum(UPDATED_SERVIS_DURUM)
            .anlikSorgu(UPDATED_ANLIK_SORGU)
            .yiginVeri(UPDATED_YIGIN_VERI)
            .gds(UPDATED_GDS);
        return servisTemel;
    }

    @BeforeEach
    public void initTest() {
        servisTemel = createEntity(em);
    }

    @Test
    @Transactional
    void createServisTemel() throws Exception {
        int databaseSizeBeforeCreate = servisTemelRepository.findAll().size();
        // Create the ServisTemel
        ServisTemelDTO servisTemelDTO = servisTemelMapper.toDto(servisTemel);
        restServisTemelMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(servisTemelDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ServisTemel in the database
        List<ServisTemel> servisTemelList = servisTemelRepository.findAll();
        assertThat(servisTemelList).hasSize(databaseSizeBeforeCreate + 1);
        ServisTemel testServisTemel = servisTemelList.get(servisTemelList.size() - 1);
        assertThat(testServisTemel.getServisAdi()).isEqualTo(DEFAULT_SERVIS_ADI);
        assertThat(testServisTemel.getServisUrl()).isEqualTo(DEFAULT_SERVIS_URL);
        assertThat(testServisTemel.getServisVeriTip()).isEqualTo(DEFAULT_SERVIS_VERI_TIP);
        assertThat(testServisTemel.getAciklamasi()).isEqualTo(DEFAULT_ACIKLAMASI);
        assertThat(testServisTemel.getDosyasi()).isEqualTo(DEFAULT_DOSYASI);
        assertThat(testServisTemel.getDosyasiContentType()).isEqualTo(DEFAULT_DOSYASI_CONTENT_TYPE);
        assertThat(testServisTemel.getResmi()).isEqualTo(DEFAULT_RESMI);
        assertThat(testServisTemel.getResmiContentType()).isEqualTo(DEFAULT_RESMI_CONTENT_TYPE);
        assertThat(testServisTemel.getServisSekli()).isEqualTo(DEFAULT_SERVIS_SEKLI);
        assertThat(testServisTemel.getServisTipi()).isEqualTo(DEFAULT_SERVIS_TIPI);
        assertThat(testServisTemel.getBaglantiSekli()).isEqualTo(DEFAULT_BAGLANTI_SEKLI);
        assertThat(testServisTemel.getServisDurum()).isEqualTo(DEFAULT_SERVIS_DURUM);
        assertThat(testServisTemel.getAnlikSorgu()).isEqualTo(DEFAULT_ANLIK_SORGU);
        assertThat(testServisTemel.getYiginVeri()).isEqualTo(DEFAULT_YIGIN_VERI);
        assertThat(testServisTemel.getGds()).isEqualTo(DEFAULT_GDS);
    }

    @Test
    @Transactional
    void createServisTemelWithExistingId() throws Exception {
        // Create the ServisTemel with an existing ID
        servisTemel.setId(1L);
        ServisTemelDTO servisTemelDTO = servisTemelMapper.toDto(servisTemel);

        int databaseSizeBeforeCreate = servisTemelRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restServisTemelMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(servisTemelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServisTemel in the database
        List<ServisTemel> servisTemelList = servisTemelRepository.findAll();
        assertThat(servisTemelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkServisAdiIsRequired() throws Exception {
        int databaseSizeBeforeTest = servisTemelRepository.findAll().size();
        // set the field null
        servisTemel.setServisAdi(null);

        // Create the ServisTemel, which fails.
        ServisTemelDTO servisTemelDTO = servisTemelMapper.toDto(servisTemel);

        restServisTemelMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(servisTemelDTO))
            )
            .andExpect(status().isBadRequest());

        List<ServisTemel> servisTemelList = servisTemelRepository.findAll();
        assertThat(servisTemelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkServisUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = servisTemelRepository.findAll().size();
        // set the field null
        servisTemel.setServisUrl(null);

        // Create the ServisTemel, which fails.
        ServisTemelDTO servisTemelDTO = servisTemelMapper.toDto(servisTemel);

        restServisTemelMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(servisTemelDTO))
            )
            .andExpect(status().isBadRequest());

        List<ServisTemel> servisTemelList = servisTemelRepository.findAll();
        assertThat(servisTemelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkServisVeriTipIsRequired() throws Exception {
        int databaseSizeBeforeTest = servisTemelRepository.findAll().size();
        // set the field null
        servisTemel.setServisVeriTip(null);

        // Create the ServisTemel, which fails.
        ServisTemelDTO servisTemelDTO = servisTemelMapper.toDto(servisTemel);

        restServisTemelMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(servisTemelDTO))
            )
            .andExpect(status().isBadRequest());

        List<ServisTemel> servisTemelList = servisTemelRepository.findAll();
        assertThat(servisTemelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAciklamasiIsRequired() throws Exception {
        int databaseSizeBeforeTest = servisTemelRepository.findAll().size();
        // set the field null
        servisTemel.setAciklamasi(null);

        // Create the ServisTemel, which fails.
        ServisTemelDTO servisTemelDTO = servisTemelMapper.toDto(servisTemel);

        restServisTemelMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(servisTemelDTO))
            )
            .andExpect(status().isBadRequest());

        List<ServisTemel> servisTemelList = servisTemelRepository.findAll();
        assertThat(servisTemelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkServisSekliIsRequired() throws Exception {
        int databaseSizeBeforeTest = servisTemelRepository.findAll().size();
        // set the field null
        servisTemel.setServisSekli(null);

        // Create the ServisTemel, which fails.
        ServisTemelDTO servisTemelDTO = servisTemelMapper.toDto(servisTemel);

        restServisTemelMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(servisTemelDTO))
            )
            .andExpect(status().isBadRequest());

        List<ServisTemel> servisTemelList = servisTemelRepository.findAll();
        assertThat(servisTemelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkServisTipiIsRequired() throws Exception {
        int databaseSizeBeforeTest = servisTemelRepository.findAll().size();
        // set the field null
        servisTemel.setServisTipi(null);

        // Create the ServisTemel, which fails.
        ServisTemelDTO servisTemelDTO = servisTemelMapper.toDto(servisTemel);

        restServisTemelMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(servisTemelDTO))
            )
            .andExpect(status().isBadRequest());

        List<ServisTemel> servisTemelList = servisTemelRepository.findAll();
        assertThat(servisTemelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBaglantiSekliIsRequired() throws Exception {
        int databaseSizeBeforeTest = servisTemelRepository.findAll().size();
        // set the field null
        servisTemel.setBaglantiSekli(null);

        // Create the ServisTemel, which fails.
        ServisTemelDTO servisTemelDTO = servisTemelMapper.toDto(servisTemel);

        restServisTemelMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(servisTemelDTO))
            )
            .andExpect(status().isBadRequest());

        List<ServisTemel> servisTemelList = servisTemelRepository.findAll();
        assertThat(servisTemelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllServisTemels() throws Exception {
        // Initialize the database
        servisTemelRepository.saveAndFlush(servisTemel);

        // Get all the servisTemelList
        restServisTemelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(servisTemel.getId().intValue())))
            .andExpect(jsonPath("$.[*].servisAdi").value(hasItem(DEFAULT_SERVIS_ADI)))
            .andExpect(jsonPath("$.[*].servisUrl").value(hasItem(DEFAULT_SERVIS_URL)))
            .andExpect(jsonPath("$.[*].servisVeriTip").value(hasItem(DEFAULT_SERVIS_VERI_TIP)))
            .andExpect(jsonPath("$.[*].aciklamasi").value(hasItem(DEFAULT_ACIKLAMASI)))
            .andExpect(jsonPath("$.[*].dosyasiContentType").value(hasItem(DEFAULT_DOSYASI_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].dosyasi").value(hasItem(Base64Utils.encodeToString(DEFAULT_DOSYASI))))
            .andExpect(jsonPath("$.[*].resmiContentType").value(hasItem(DEFAULT_RESMI_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].resmi").value(hasItem(Base64Utils.encodeToString(DEFAULT_RESMI))))
            .andExpect(jsonPath("$.[*].servisSekli").value(hasItem(DEFAULT_SERVIS_SEKLI.toString())))
            .andExpect(jsonPath("$.[*].servisTipi").value(hasItem(DEFAULT_SERVIS_TIPI.toString())))
            .andExpect(jsonPath("$.[*].baglantiSekli").value(hasItem(DEFAULT_BAGLANTI_SEKLI.toString())))
            .andExpect(jsonPath("$.[*].servisDurum").value(hasItem(DEFAULT_SERVIS_DURUM.toString())))
            .andExpect(jsonPath("$.[*].anlikSorgu").value(hasItem(DEFAULT_ANLIK_SORGU)))
            .andExpect(jsonPath("$.[*].yiginVeri").value(hasItem(DEFAULT_YIGIN_VERI)))
            .andExpect(jsonPath("$.[*].gds").value(hasItem(DEFAULT_GDS)));
    }

    @Test
    @Transactional
    void getServisTemel() throws Exception {
        // Initialize the database
        servisTemelRepository.saveAndFlush(servisTemel);

        // Get the servisTemel
        restServisTemelMockMvc
            .perform(get(ENTITY_API_URL_ID, servisTemel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(servisTemel.getId().intValue()))
            .andExpect(jsonPath("$.servisAdi").value(DEFAULT_SERVIS_ADI))
            .andExpect(jsonPath("$.servisUrl").value(DEFAULT_SERVIS_URL))
            .andExpect(jsonPath("$.servisVeriTip").value(DEFAULT_SERVIS_VERI_TIP))
            .andExpect(jsonPath("$.aciklamasi").value(DEFAULT_ACIKLAMASI))
            .andExpect(jsonPath("$.dosyasiContentType").value(DEFAULT_DOSYASI_CONTENT_TYPE))
            .andExpect(jsonPath("$.dosyasi").value(Base64Utils.encodeToString(DEFAULT_DOSYASI)))
            .andExpect(jsonPath("$.resmiContentType").value(DEFAULT_RESMI_CONTENT_TYPE))
            .andExpect(jsonPath("$.resmi").value(Base64Utils.encodeToString(DEFAULT_RESMI)))
            .andExpect(jsonPath("$.servisSekli").value(DEFAULT_SERVIS_SEKLI.toString()))
            .andExpect(jsonPath("$.servisTipi").value(DEFAULT_SERVIS_TIPI.toString()))
            .andExpect(jsonPath("$.baglantiSekli").value(DEFAULT_BAGLANTI_SEKLI.toString()))
            .andExpect(jsonPath("$.servisDurum").value(DEFAULT_SERVIS_DURUM.toString()))
            .andExpect(jsonPath("$.anlikSorgu").value(DEFAULT_ANLIK_SORGU))
            .andExpect(jsonPath("$.yiginVeri").value(DEFAULT_YIGIN_VERI))
            .andExpect(jsonPath("$.gds").value(DEFAULT_GDS));
    }

    @Test
    @Transactional
    void getNonExistingServisTemel() throws Exception {
        // Get the servisTemel
        restServisTemelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewServisTemel() throws Exception {
        // Initialize the database
        servisTemelRepository.saveAndFlush(servisTemel);

        int databaseSizeBeforeUpdate = servisTemelRepository.findAll().size();

        // Update the servisTemel
        ServisTemel updatedServisTemel = servisTemelRepository.findById(servisTemel.getId()).get();
        // Disconnect from session so that the updates on updatedServisTemel are not directly saved in db
        em.detach(updatedServisTemel);
        updatedServisTemel
            .servisAdi(UPDATED_SERVIS_ADI)
            .servisUrl(UPDATED_SERVIS_URL)
            .servisVeriTip(UPDATED_SERVIS_VERI_TIP)
            .aciklamasi(UPDATED_ACIKLAMASI)
            .dosyasi(UPDATED_DOSYASI)
            .dosyasiContentType(UPDATED_DOSYASI_CONTENT_TYPE)
            .resmi(UPDATED_RESMI)
            .resmiContentType(UPDATED_RESMI_CONTENT_TYPE)
            .servisSekli(UPDATED_SERVIS_SEKLI)
            .servisTipi(UPDATED_SERVIS_TIPI)
            .baglantiSekli(UPDATED_BAGLANTI_SEKLI)
            .servisDurum(UPDATED_SERVIS_DURUM)
            .anlikSorgu(UPDATED_ANLIK_SORGU)
            .yiginVeri(UPDATED_YIGIN_VERI)
            .gds(UPDATED_GDS);
        ServisTemelDTO servisTemelDTO = servisTemelMapper.toDto(updatedServisTemel);

        restServisTemelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, servisTemelDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(servisTemelDTO))
            )
            .andExpect(status().isOk());

        // Validate the ServisTemel in the database
        List<ServisTemel> servisTemelList = servisTemelRepository.findAll();
        assertThat(servisTemelList).hasSize(databaseSizeBeforeUpdate);
        ServisTemel testServisTemel = servisTemelList.get(servisTemelList.size() - 1);
        assertThat(testServisTemel.getServisAdi()).isEqualTo(UPDATED_SERVIS_ADI);
        assertThat(testServisTemel.getServisUrl()).isEqualTo(UPDATED_SERVIS_URL);
        assertThat(testServisTemel.getServisVeriTip()).isEqualTo(UPDATED_SERVIS_VERI_TIP);
        assertThat(testServisTemel.getAciklamasi()).isEqualTo(UPDATED_ACIKLAMASI);
        assertThat(testServisTemel.getDosyasi()).isEqualTo(UPDATED_DOSYASI);
        assertThat(testServisTemel.getDosyasiContentType()).isEqualTo(UPDATED_DOSYASI_CONTENT_TYPE);
        assertThat(testServisTemel.getResmi()).isEqualTo(UPDATED_RESMI);
        assertThat(testServisTemel.getResmiContentType()).isEqualTo(UPDATED_RESMI_CONTENT_TYPE);
        assertThat(testServisTemel.getServisSekli()).isEqualTo(UPDATED_SERVIS_SEKLI);
        assertThat(testServisTemel.getServisTipi()).isEqualTo(UPDATED_SERVIS_TIPI);
        assertThat(testServisTemel.getBaglantiSekli()).isEqualTo(UPDATED_BAGLANTI_SEKLI);
        assertThat(testServisTemel.getServisDurum()).isEqualTo(UPDATED_SERVIS_DURUM);
        assertThat(testServisTemel.getAnlikSorgu()).isEqualTo(UPDATED_ANLIK_SORGU);
        assertThat(testServisTemel.getYiginVeri()).isEqualTo(UPDATED_YIGIN_VERI);
        assertThat(testServisTemel.getGds()).isEqualTo(UPDATED_GDS);
    }

    @Test
    @Transactional
    void putNonExistingServisTemel() throws Exception {
        int databaseSizeBeforeUpdate = servisTemelRepository.findAll().size();
        servisTemel.setId(count.incrementAndGet());

        // Create the ServisTemel
        ServisTemelDTO servisTemelDTO = servisTemelMapper.toDto(servisTemel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServisTemelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, servisTemelDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(servisTemelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServisTemel in the database
        List<ServisTemel> servisTemelList = servisTemelRepository.findAll();
        assertThat(servisTemelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchServisTemel() throws Exception {
        int databaseSizeBeforeUpdate = servisTemelRepository.findAll().size();
        servisTemel.setId(count.incrementAndGet());

        // Create the ServisTemel
        ServisTemelDTO servisTemelDTO = servisTemelMapper.toDto(servisTemel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServisTemelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(servisTemelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServisTemel in the database
        List<ServisTemel> servisTemelList = servisTemelRepository.findAll();
        assertThat(servisTemelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamServisTemel() throws Exception {
        int databaseSizeBeforeUpdate = servisTemelRepository.findAll().size();
        servisTemel.setId(count.incrementAndGet());

        // Create the ServisTemel
        ServisTemelDTO servisTemelDTO = servisTemelMapper.toDto(servisTemel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServisTemelMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(servisTemelDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ServisTemel in the database
        List<ServisTemel> servisTemelList = servisTemelRepository.findAll();
        assertThat(servisTemelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateServisTemelWithPatch() throws Exception {
        // Initialize the database
        servisTemelRepository.saveAndFlush(servisTemel);

        int databaseSizeBeforeUpdate = servisTemelRepository.findAll().size();

        // Update the servisTemel using partial update
        ServisTemel partialUpdatedServisTemel = new ServisTemel();
        partialUpdatedServisTemel.setId(servisTemel.getId());

        partialUpdatedServisTemel
            .servisAdi(UPDATED_SERVIS_ADI)
            .servisVeriTip(UPDATED_SERVIS_VERI_TIP)
            .resmi(UPDATED_RESMI)
            .resmiContentType(UPDATED_RESMI_CONTENT_TYPE)
            .servisTipi(UPDATED_SERVIS_TIPI)
            .servisDurum(UPDATED_SERVIS_DURUM)
            .anlikSorgu(UPDATED_ANLIK_SORGU)
            .gds(UPDATED_GDS);

        restServisTemelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedServisTemel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedServisTemel))
            )
            .andExpect(status().isOk());

        // Validate the ServisTemel in the database
        List<ServisTemel> servisTemelList = servisTemelRepository.findAll();
        assertThat(servisTemelList).hasSize(databaseSizeBeforeUpdate);
        ServisTemel testServisTemel = servisTemelList.get(servisTemelList.size() - 1);
        assertThat(testServisTemel.getServisAdi()).isEqualTo(UPDATED_SERVIS_ADI);
        assertThat(testServisTemel.getServisUrl()).isEqualTo(DEFAULT_SERVIS_URL);
        assertThat(testServisTemel.getServisVeriTip()).isEqualTo(UPDATED_SERVIS_VERI_TIP);
        assertThat(testServisTemel.getAciklamasi()).isEqualTo(DEFAULT_ACIKLAMASI);
        assertThat(testServisTemel.getDosyasi()).isEqualTo(DEFAULT_DOSYASI);
        assertThat(testServisTemel.getDosyasiContentType()).isEqualTo(DEFAULT_DOSYASI_CONTENT_TYPE);
        assertThat(testServisTemel.getResmi()).isEqualTo(UPDATED_RESMI);
        assertThat(testServisTemel.getResmiContentType()).isEqualTo(UPDATED_RESMI_CONTENT_TYPE);
        assertThat(testServisTemel.getServisSekli()).isEqualTo(DEFAULT_SERVIS_SEKLI);
        assertThat(testServisTemel.getServisTipi()).isEqualTo(UPDATED_SERVIS_TIPI);
        assertThat(testServisTemel.getBaglantiSekli()).isEqualTo(DEFAULT_BAGLANTI_SEKLI);
        assertThat(testServisTemel.getServisDurum()).isEqualTo(UPDATED_SERVIS_DURUM);
        assertThat(testServisTemel.getAnlikSorgu()).isEqualTo(UPDATED_ANLIK_SORGU);
        assertThat(testServisTemel.getYiginVeri()).isEqualTo(DEFAULT_YIGIN_VERI);
        assertThat(testServisTemel.getGds()).isEqualTo(UPDATED_GDS);
    }

    @Test
    @Transactional
    void fullUpdateServisTemelWithPatch() throws Exception {
        // Initialize the database
        servisTemelRepository.saveAndFlush(servisTemel);

        int databaseSizeBeforeUpdate = servisTemelRepository.findAll().size();

        // Update the servisTemel using partial update
        ServisTemel partialUpdatedServisTemel = new ServisTemel();
        partialUpdatedServisTemel.setId(servisTemel.getId());

        partialUpdatedServisTemel
            .servisAdi(UPDATED_SERVIS_ADI)
            .servisUrl(UPDATED_SERVIS_URL)
            .servisVeriTip(UPDATED_SERVIS_VERI_TIP)
            .aciklamasi(UPDATED_ACIKLAMASI)
            .dosyasi(UPDATED_DOSYASI)
            .dosyasiContentType(UPDATED_DOSYASI_CONTENT_TYPE)
            .resmi(UPDATED_RESMI)
            .resmiContentType(UPDATED_RESMI_CONTENT_TYPE)
            .servisSekli(UPDATED_SERVIS_SEKLI)
            .servisTipi(UPDATED_SERVIS_TIPI)
            .baglantiSekli(UPDATED_BAGLANTI_SEKLI)
            .servisDurum(UPDATED_SERVIS_DURUM)
            .anlikSorgu(UPDATED_ANLIK_SORGU)
            .yiginVeri(UPDATED_YIGIN_VERI)
            .gds(UPDATED_GDS);

        restServisTemelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedServisTemel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedServisTemel))
            )
            .andExpect(status().isOk());

        // Validate the ServisTemel in the database
        List<ServisTemel> servisTemelList = servisTemelRepository.findAll();
        assertThat(servisTemelList).hasSize(databaseSizeBeforeUpdate);
        ServisTemel testServisTemel = servisTemelList.get(servisTemelList.size() - 1);
        assertThat(testServisTemel.getServisAdi()).isEqualTo(UPDATED_SERVIS_ADI);
        assertThat(testServisTemel.getServisUrl()).isEqualTo(UPDATED_SERVIS_URL);
        assertThat(testServisTemel.getServisVeriTip()).isEqualTo(UPDATED_SERVIS_VERI_TIP);
        assertThat(testServisTemel.getAciklamasi()).isEqualTo(UPDATED_ACIKLAMASI);
        assertThat(testServisTemel.getDosyasi()).isEqualTo(UPDATED_DOSYASI);
        assertThat(testServisTemel.getDosyasiContentType()).isEqualTo(UPDATED_DOSYASI_CONTENT_TYPE);
        assertThat(testServisTemel.getResmi()).isEqualTo(UPDATED_RESMI);
        assertThat(testServisTemel.getResmiContentType()).isEqualTo(UPDATED_RESMI_CONTENT_TYPE);
        assertThat(testServisTemel.getServisSekli()).isEqualTo(UPDATED_SERVIS_SEKLI);
        assertThat(testServisTemel.getServisTipi()).isEqualTo(UPDATED_SERVIS_TIPI);
        assertThat(testServisTemel.getBaglantiSekli()).isEqualTo(UPDATED_BAGLANTI_SEKLI);
        assertThat(testServisTemel.getServisDurum()).isEqualTo(UPDATED_SERVIS_DURUM);
        assertThat(testServisTemel.getAnlikSorgu()).isEqualTo(UPDATED_ANLIK_SORGU);
        assertThat(testServisTemel.getYiginVeri()).isEqualTo(UPDATED_YIGIN_VERI);
        assertThat(testServisTemel.getGds()).isEqualTo(UPDATED_GDS);
    }

    @Test
    @Transactional
    void patchNonExistingServisTemel() throws Exception {
        int databaseSizeBeforeUpdate = servisTemelRepository.findAll().size();
        servisTemel.setId(count.incrementAndGet());

        // Create the ServisTemel
        ServisTemelDTO servisTemelDTO = servisTemelMapper.toDto(servisTemel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServisTemelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, servisTemelDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(servisTemelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServisTemel in the database
        List<ServisTemel> servisTemelList = servisTemelRepository.findAll();
        assertThat(servisTemelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchServisTemel() throws Exception {
        int databaseSizeBeforeUpdate = servisTemelRepository.findAll().size();
        servisTemel.setId(count.incrementAndGet());

        // Create the ServisTemel
        ServisTemelDTO servisTemelDTO = servisTemelMapper.toDto(servisTemel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServisTemelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(servisTemelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServisTemel in the database
        List<ServisTemel> servisTemelList = servisTemelRepository.findAll();
        assertThat(servisTemelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamServisTemel() throws Exception {
        int databaseSizeBeforeUpdate = servisTemelRepository.findAll().size();
        servisTemel.setId(count.incrementAndGet());

        // Create the ServisTemel
        ServisTemelDTO servisTemelDTO = servisTemelMapper.toDto(servisTemel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServisTemelMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(servisTemelDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ServisTemel in the database
        List<ServisTemel> servisTemelList = servisTemelRepository.findAll();
        assertThat(servisTemelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteServisTemel() throws Exception {
        // Initialize the database
        servisTemelRepository.saveAndFlush(servisTemel);

        int databaseSizeBeforeDelete = servisTemelRepository.findAll().size();

        // Delete the servisTemel
        restServisTemelMockMvc
            .perform(delete(ENTITY_API_URL_ID, servisTemel.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ServisTemel> servisTemelList = servisTemelRepository.findAll();
        assertThat(servisTemelList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
