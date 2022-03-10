package com.servistakip.app.web.rest;

import com.servistakip.app.repository.KurumTemelRepository;
import com.servistakip.app.service.KurumTemelService;
import com.servistakip.app.service.dto.KurumTemelDTO;
import com.servistakip.app.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.servistakip.app.domain.KurumTemel}.
 */
@RestController
@RequestMapping("/api")
public class KurumTemelResource {

    private final Logger log = LoggerFactory.getLogger(KurumTemelResource.class);

    private static final String ENTITY_NAME = "kurumTemel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final KurumTemelService kurumTemelService;

    private final KurumTemelRepository kurumTemelRepository;

    public KurumTemelResource(KurumTemelService kurumTemelService, KurumTemelRepository kurumTemelRepository) {
        this.kurumTemelService = kurumTemelService;
        this.kurumTemelRepository = kurumTemelRepository;
    }

    /**
     * {@code POST  /kurum-temels} : Create a new kurumTemel.
     *
     * @param kurumTemelDTO the kurumTemelDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new kurumTemelDTO, or with status {@code 400 (Bad Request)} if the kurumTemel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/kurum-temels")
    public ResponseEntity<KurumTemelDTO> createKurumTemel(@Valid @RequestBody KurumTemelDTO kurumTemelDTO) throws URISyntaxException {
        log.debug("REST request to save KurumTemel : {}", kurumTemelDTO);
        if (kurumTemelDTO.getId() != null) {
            throw new BadRequestAlertException("A new kurumTemel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        KurumTemelDTO result = kurumTemelService.save(kurumTemelDTO);
        return ResponseEntity
            .created(new URI("/api/kurum-temels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /kurum-temels/:id} : Updates an existing kurumTemel.
     *
     * @param id the id of the kurumTemelDTO to save.
     * @param kurumTemelDTO the kurumTemelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated kurumTemelDTO,
     * or with status {@code 400 (Bad Request)} if the kurumTemelDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the kurumTemelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/kurum-temels/{id}")
    public ResponseEntity<KurumTemelDTO> updateKurumTemel(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody KurumTemelDTO kurumTemelDTO
    ) throws URISyntaxException {
        log.debug("REST request to update KurumTemel : {}, {}", id, kurumTemelDTO);
        if (kurumTemelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, kurumTemelDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!kurumTemelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        KurumTemelDTO result = kurumTemelService.save(kurumTemelDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, kurumTemelDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /kurum-temels/:id} : Partial updates given fields of an existing kurumTemel, field will ignore if it is null
     *
     * @param id the id of the kurumTemelDTO to save.
     * @param kurumTemelDTO the kurumTemelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated kurumTemelDTO,
     * or with status {@code 400 (Bad Request)} if the kurumTemelDTO is not valid,
     * or with status {@code 404 (Not Found)} if the kurumTemelDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the kurumTemelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/kurum-temels/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<KurumTemelDTO> partialUpdateKurumTemel(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody KurumTemelDTO kurumTemelDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update KurumTemel partially : {}, {}", id, kurumTemelDTO);
        if (kurumTemelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, kurumTemelDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!kurumTemelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<KurumTemelDTO> result = kurumTemelService.partialUpdate(kurumTemelDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, kurumTemelDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /kurum-temels} : get all the kurumTemels.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of kurumTemels in body.
     */
    @GetMapping("/kurum-temels")
    public ResponseEntity<List<KurumTemelDTO>> getAllKurumTemels(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of KurumTemels");
        Page<KurumTemelDTO> page = kurumTemelService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /kurum-temels/:id} : get the "id" kurumTemel.
     *
     * @param id the id of the kurumTemelDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the kurumTemelDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/kurum-temels/{id}")
    public ResponseEntity<KurumTemelDTO> getKurumTemel(@PathVariable Long id) {
        log.debug("REST request to get KurumTemel : {}", id);
        Optional<KurumTemelDTO> kurumTemelDTO = kurumTemelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(kurumTemelDTO);
    }

    /**
     * {@code DELETE  /kurum-temels/:id} : delete the "id" kurumTemel.
     *
     * @param id the id of the kurumTemelDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/kurum-temels/{id}")
    public ResponseEntity<Void> deleteKurumTemel(@PathVariable Long id) {
        log.debug("REST request to delete KurumTemel : {}", id);
        kurumTemelService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
