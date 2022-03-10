package com.servistakip.app.web.rest;

import com.servistakip.app.repository.MetodTemelRepository;
import com.servistakip.app.service.MetodTemelService;
import com.servistakip.app.service.dto.MetodTemelDTO;
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
 * REST controller for managing {@link com.servistakip.app.domain.MetodTemel}.
 */
@RestController
@RequestMapping("/api")
public class MetodTemelResource {

    private final Logger log = LoggerFactory.getLogger(MetodTemelResource.class);

    private static final String ENTITY_NAME = "metodTemel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MetodTemelService metodTemelService;

    private final MetodTemelRepository metodTemelRepository;

    public MetodTemelResource(MetodTemelService metodTemelService, MetodTemelRepository metodTemelRepository) {
        this.metodTemelService = metodTemelService;
        this.metodTemelRepository = metodTemelRepository;
    }

    /**
     * {@code POST  /metod-temels} : Create a new metodTemel.
     *
     * @param metodTemelDTO the metodTemelDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new metodTemelDTO, or with status {@code 400 (Bad Request)} if the metodTemel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/metod-temels")
    public ResponseEntity<MetodTemelDTO> createMetodTemel(@Valid @RequestBody MetodTemelDTO metodTemelDTO) throws URISyntaxException {
        log.debug("REST request to save MetodTemel : {}", metodTemelDTO);
        if (metodTemelDTO.getId() != null) {
            throw new BadRequestAlertException("A new metodTemel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MetodTemelDTO result = metodTemelService.save(metodTemelDTO);
        return ResponseEntity
            .created(new URI("/api/metod-temels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /metod-temels/:id} : Updates an existing metodTemel.
     *
     * @param id the id of the metodTemelDTO to save.
     * @param metodTemelDTO the metodTemelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated metodTemelDTO,
     * or with status {@code 400 (Bad Request)} if the metodTemelDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the metodTemelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/metod-temels/{id}")
    public ResponseEntity<MetodTemelDTO> updateMetodTemel(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody MetodTemelDTO metodTemelDTO
    ) throws URISyntaxException {
        log.debug("REST request to update MetodTemel : {}, {}", id, metodTemelDTO);
        if (metodTemelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, metodTemelDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!metodTemelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MetodTemelDTO result = metodTemelService.save(metodTemelDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, metodTemelDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /metod-temels/:id} : Partial updates given fields of an existing metodTemel, field will ignore if it is null
     *
     * @param id the id of the metodTemelDTO to save.
     * @param metodTemelDTO the metodTemelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated metodTemelDTO,
     * or with status {@code 400 (Bad Request)} if the metodTemelDTO is not valid,
     * or with status {@code 404 (Not Found)} if the metodTemelDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the metodTemelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/metod-temels/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MetodTemelDTO> partialUpdateMetodTemel(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody MetodTemelDTO metodTemelDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update MetodTemel partially : {}, {}", id, metodTemelDTO);
        if (metodTemelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, metodTemelDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!metodTemelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MetodTemelDTO> result = metodTemelService.partialUpdate(metodTemelDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, metodTemelDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /metod-temels} : get all the metodTemels.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of metodTemels in body.
     */
    @GetMapping("/metod-temels")
    public ResponseEntity<List<MetodTemelDTO>> getAllMetodTemels(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of MetodTemels");
        Page<MetodTemelDTO> page = metodTemelService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /metod-temels/:id} : get the "id" metodTemel.
     *
     * @param id the id of the metodTemelDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the metodTemelDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/metod-temels/{id}")
    public ResponseEntity<MetodTemelDTO> getMetodTemel(@PathVariable Long id) {
        log.debug("REST request to get MetodTemel : {}", id);
        Optional<MetodTemelDTO> metodTemelDTO = metodTemelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(metodTemelDTO);
    }

    /**
     * {@code DELETE  /metod-temels/:id} : delete the "id" metodTemel.
     *
     * @param id the id of the metodTemelDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/metod-temels/{id}")
    public ResponseEntity<Void> deleteMetodTemel(@PathVariable Long id) {
        log.debug("REST request to delete MetodTemel : {}", id);
        metodTemelService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
