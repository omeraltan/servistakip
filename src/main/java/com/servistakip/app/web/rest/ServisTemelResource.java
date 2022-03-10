package com.servistakip.app.web.rest;

import com.servistakip.app.repository.ServisTemelRepository;
import com.servistakip.app.service.ServisTemelService;
import com.servistakip.app.service.dto.ServisTemelDTO;
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
 * REST controller for managing {@link com.servistakip.app.domain.ServisTemel}.
 */
@RestController
@RequestMapping("/api")
public class ServisTemelResource {

    private final Logger log = LoggerFactory.getLogger(ServisTemelResource.class);

    private static final String ENTITY_NAME = "servisTemel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServisTemelService servisTemelService;

    private final ServisTemelRepository servisTemelRepository;

    public ServisTemelResource(ServisTemelService servisTemelService, ServisTemelRepository servisTemelRepository) {
        this.servisTemelService = servisTemelService;
        this.servisTemelRepository = servisTemelRepository;
    }

    /**
     * {@code POST  /servis-temels} : Create a new servisTemel.
     *
     * @param servisTemelDTO the servisTemelDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new servisTemelDTO, or with status {@code 400 (Bad Request)} if the servisTemel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/servis-temels")
    public ResponseEntity<ServisTemelDTO> createServisTemel(@Valid @RequestBody ServisTemelDTO servisTemelDTO) throws URISyntaxException {
        log.debug("REST request to save ServisTemel : {}", servisTemelDTO);
        if (servisTemelDTO.getId() != null) {
            throw new BadRequestAlertException("A new servisTemel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ServisTemelDTO result = servisTemelService.save(servisTemelDTO);
        return ResponseEntity
            .created(new URI("/api/servis-temels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /servis-temels/:id} : Updates an existing servisTemel.
     *
     * @param id the id of the servisTemelDTO to save.
     * @param servisTemelDTO the servisTemelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated servisTemelDTO,
     * or with status {@code 400 (Bad Request)} if the servisTemelDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the servisTemelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/servis-temels/{id}")
    public ResponseEntity<ServisTemelDTO> updateServisTemel(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ServisTemelDTO servisTemelDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ServisTemel : {}, {}", id, servisTemelDTO);
        if (servisTemelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, servisTemelDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!servisTemelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ServisTemelDTO result = servisTemelService.save(servisTemelDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, servisTemelDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /servis-temels/:id} : Partial updates given fields of an existing servisTemel, field will ignore if it is null
     *
     * @param id the id of the servisTemelDTO to save.
     * @param servisTemelDTO the servisTemelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated servisTemelDTO,
     * or with status {@code 400 (Bad Request)} if the servisTemelDTO is not valid,
     * or with status {@code 404 (Not Found)} if the servisTemelDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the servisTemelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/servis-temels/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ServisTemelDTO> partialUpdateServisTemel(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ServisTemelDTO servisTemelDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ServisTemel partially : {}, {}", id, servisTemelDTO);
        if (servisTemelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, servisTemelDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!servisTemelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ServisTemelDTO> result = servisTemelService.partialUpdate(servisTemelDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, servisTemelDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /servis-temels} : get all the servisTemels.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of servisTemels in body.
     */
    @GetMapping("/servis-temels")
    public ResponseEntity<List<ServisTemelDTO>> getAllServisTemels(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of ServisTemels");
        Page<ServisTemelDTO> page = servisTemelService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /servis-temels/:id} : get the "id" servisTemel.
     *
     * @param id the id of the servisTemelDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the servisTemelDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/servis-temels/{id}")
    public ResponseEntity<ServisTemelDTO> getServisTemel(@PathVariable Long id) {
        log.debug("REST request to get ServisTemel : {}", id);
        Optional<ServisTemelDTO> servisTemelDTO = servisTemelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(servisTemelDTO);
    }

    /**
     * {@code DELETE  /servis-temels/:id} : delete the "id" servisTemel.
     *
     * @param id the id of the servisTemelDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/servis-temels/{id}")
    public ResponseEntity<Void> deleteServisTemel(@PathVariable Long id) {
        log.debug("REST request to delete ServisTemel : {}", id);
        servisTemelService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
