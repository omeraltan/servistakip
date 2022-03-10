package com.servistakip.app.web.rest;

import com.servistakip.app.repository.ServisProjeTemelRepository;
import com.servistakip.app.service.ServisProjeTemelService;
import com.servistakip.app.service.dto.ServisProjeTemelDTO;
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
 * REST controller for managing {@link com.servistakip.app.domain.ServisProjeTemel}.
 */
@RestController
@RequestMapping("/api")
public class ServisProjeTemelResource {

    private final Logger log = LoggerFactory.getLogger(ServisProjeTemelResource.class);

    private static final String ENTITY_NAME = "servisProjeTemel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServisProjeTemelService servisProjeTemelService;

    private final ServisProjeTemelRepository servisProjeTemelRepository;

    public ServisProjeTemelResource(
        ServisProjeTemelService servisProjeTemelService,
        ServisProjeTemelRepository servisProjeTemelRepository
    ) {
        this.servisProjeTemelService = servisProjeTemelService;
        this.servisProjeTemelRepository = servisProjeTemelRepository;
    }

    /**
     * {@code POST  /servis-proje-temels} : Create a new servisProjeTemel.
     *
     * @param servisProjeTemelDTO the servisProjeTemelDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new servisProjeTemelDTO, or with status {@code 400 (Bad Request)} if the servisProjeTemel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/servis-proje-temels")
    public ResponseEntity<ServisProjeTemelDTO> createServisProjeTemel(@Valid @RequestBody ServisProjeTemelDTO servisProjeTemelDTO)
        throws URISyntaxException {
        log.debug("REST request to save ServisProjeTemel : {}", servisProjeTemelDTO);
        if (servisProjeTemelDTO.getId() != null) {
            throw new BadRequestAlertException("A new servisProjeTemel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ServisProjeTemelDTO result = servisProjeTemelService.save(servisProjeTemelDTO);
        return ResponseEntity
            .created(new URI("/api/servis-proje-temels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /servis-proje-temels/:id} : Updates an existing servisProjeTemel.
     *
     * @param id the id of the servisProjeTemelDTO to save.
     * @param servisProjeTemelDTO the servisProjeTemelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated servisProjeTemelDTO,
     * or with status {@code 400 (Bad Request)} if the servisProjeTemelDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the servisProjeTemelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/servis-proje-temels/{id}")
    public ResponseEntity<ServisProjeTemelDTO> updateServisProjeTemel(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ServisProjeTemelDTO servisProjeTemelDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ServisProjeTemel : {}, {}", id, servisProjeTemelDTO);
        if (servisProjeTemelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, servisProjeTemelDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!servisProjeTemelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ServisProjeTemelDTO result = servisProjeTemelService.save(servisProjeTemelDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, servisProjeTemelDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /servis-proje-temels/:id} : Partial updates given fields of an existing servisProjeTemel, field will ignore if it is null
     *
     * @param id the id of the servisProjeTemelDTO to save.
     * @param servisProjeTemelDTO the servisProjeTemelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated servisProjeTemelDTO,
     * or with status {@code 400 (Bad Request)} if the servisProjeTemelDTO is not valid,
     * or with status {@code 404 (Not Found)} if the servisProjeTemelDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the servisProjeTemelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/servis-proje-temels/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ServisProjeTemelDTO> partialUpdateServisProjeTemel(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ServisProjeTemelDTO servisProjeTemelDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ServisProjeTemel partially : {}, {}", id, servisProjeTemelDTO);
        if (servisProjeTemelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, servisProjeTemelDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!servisProjeTemelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ServisProjeTemelDTO> result = servisProjeTemelService.partialUpdate(servisProjeTemelDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, servisProjeTemelDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /servis-proje-temels} : get all the servisProjeTemels.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of servisProjeTemels in body.
     */
    @GetMapping("/servis-proje-temels")
    public ResponseEntity<List<ServisProjeTemelDTO>> getAllServisProjeTemels(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of ServisProjeTemels");
        Page<ServisProjeTemelDTO> page = servisProjeTemelService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /servis-proje-temels/:id} : get the "id" servisProjeTemel.
     *
     * @param id the id of the servisProjeTemelDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the servisProjeTemelDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/servis-proje-temels/{id}")
    public ResponseEntity<ServisProjeTemelDTO> getServisProjeTemel(@PathVariable Long id) {
        log.debug("REST request to get ServisProjeTemel : {}", id);
        Optional<ServisProjeTemelDTO> servisProjeTemelDTO = servisProjeTemelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(servisProjeTemelDTO);
    }

    /**
     * {@code DELETE  /servis-proje-temels/:id} : delete the "id" servisProjeTemel.
     *
     * @param id the id of the servisProjeTemelDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/servis-proje-temels/{id}")
    public ResponseEntity<Void> deleteServisProjeTemel(@PathVariable Long id) {
        log.debug("REST request to delete ServisProjeTemel : {}", id);
        servisProjeTemelService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
