package com.servistakip.app.web.rest;

import com.servistakip.app.repository.SorunTemelRepository;
import com.servistakip.app.service.SorunTemelService;
import com.servistakip.app.service.dto.SorunTemelDTO;
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
 * REST controller for managing {@link com.servistakip.app.domain.SorunTemel}.
 */
@RestController
@RequestMapping("/api")
public class SorunTemelResource {

    private final Logger log = LoggerFactory.getLogger(SorunTemelResource.class);

    private static final String ENTITY_NAME = "sorunTemel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SorunTemelService sorunTemelService;

    private final SorunTemelRepository sorunTemelRepository;

    public SorunTemelResource(SorunTemelService sorunTemelService, SorunTemelRepository sorunTemelRepository) {
        this.sorunTemelService = sorunTemelService;
        this.sorunTemelRepository = sorunTemelRepository;
    }

    /**
     * {@code POST  /sorun-temels} : Create a new sorunTemel.
     *
     * @param sorunTemelDTO the sorunTemelDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sorunTemelDTO, or with status {@code 400 (Bad Request)} if the sorunTemel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sorun-temels")
    public ResponseEntity<SorunTemelDTO> createSorunTemel(@Valid @RequestBody SorunTemelDTO sorunTemelDTO) throws URISyntaxException {
        log.debug("REST request to save SorunTemel : {}", sorunTemelDTO);
        if (sorunTemelDTO.getId() != null) {
            throw new BadRequestAlertException("A new sorunTemel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SorunTemelDTO result = sorunTemelService.save(sorunTemelDTO);
        return ResponseEntity
            .created(new URI("/api/sorun-temels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sorun-temels/:id} : Updates an existing sorunTemel.
     *
     * @param id the id of the sorunTemelDTO to save.
     * @param sorunTemelDTO the sorunTemelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sorunTemelDTO,
     * or with status {@code 400 (Bad Request)} if the sorunTemelDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sorunTemelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sorun-temels/{id}")
    public ResponseEntity<SorunTemelDTO> updateSorunTemel(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SorunTemelDTO sorunTemelDTO
    ) throws URISyntaxException {
        log.debug("REST request to update SorunTemel : {}, {}", id, sorunTemelDTO);
        if (sorunTemelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sorunTemelDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sorunTemelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SorunTemelDTO result = sorunTemelService.save(sorunTemelDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sorunTemelDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /sorun-temels/:id} : Partial updates given fields of an existing sorunTemel, field will ignore if it is null
     *
     * @param id the id of the sorunTemelDTO to save.
     * @param sorunTemelDTO the sorunTemelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sorunTemelDTO,
     * or with status {@code 400 (Bad Request)} if the sorunTemelDTO is not valid,
     * or with status {@code 404 (Not Found)} if the sorunTemelDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the sorunTemelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/sorun-temels/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SorunTemelDTO> partialUpdateSorunTemel(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SorunTemelDTO sorunTemelDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update SorunTemel partially : {}, {}", id, sorunTemelDTO);
        if (sorunTemelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sorunTemelDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sorunTemelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SorunTemelDTO> result = sorunTemelService.partialUpdate(sorunTemelDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sorunTemelDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /sorun-temels} : get all the sorunTemels.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sorunTemels in body.
     */
    @GetMapping("/sorun-temels")
    public ResponseEntity<List<SorunTemelDTO>> getAllSorunTemels(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of SorunTemels");
        Page<SorunTemelDTO> page = sorunTemelService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sorun-temels/:id} : get the "id" sorunTemel.
     *
     * @param id the id of the sorunTemelDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sorunTemelDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sorun-temels/{id}")
    public ResponseEntity<SorunTemelDTO> getSorunTemel(@PathVariable Long id) {
        log.debug("REST request to get SorunTemel : {}", id);
        Optional<SorunTemelDTO> sorunTemelDTO = sorunTemelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sorunTemelDTO);
    }

    /**
     * {@code DELETE  /sorun-temels/:id} : delete the "id" sorunTemel.
     *
     * @param id the id of the sorunTemelDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sorun-temels/{id}")
    public ResponseEntity<Void> deleteSorunTemel(@PathVariable Long id) {
        log.debug("REST request to delete SorunTemel : {}", id);
        sorunTemelService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
