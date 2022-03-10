package com.servistakip.app.web.rest;

import com.servistakip.app.repository.ProtokolTemelRepository;
import com.servistakip.app.service.ProtokolTemelService;
import com.servistakip.app.service.dto.ProtokolTemelDTO;
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
 * REST controller for managing {@link com.servistakip.app.domain.ProtokolTemel}.
 */
@RestController
@RequestMapping("/api")
public class ProtokolTemelResource {

    private final Logger log = LoggerFactory.getLogger(ProtokolTemelResource.class);

    private static final String ENTITY_NAME = "protokolTemel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProtokolTemelService protokolTemelService;

    private final ProtokolTemelRepository protokolTemelRepository;

    public ProtokolTemelResource(ProtokolTemelService protokolTemelService, ProtokolTemelRepository protokolTemelRepository) {
        this.protokolTemelService = protokolTemelService;
        this.protokolTemelRepository = protokolTemelRepository;
    }

    /**
     * {@code POST  /protokol-temels} : Create a new protokolTemel.
     *
     * @param protokolTemelDTO the protokolTemelDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new protokolTemelDTO, or with status {@code 400 (Bad Request)} if the protokolTemel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/protokol-temels")
    public ResponseEntity<ProtokolTemelDTO> createProtokolTemel(@Valid @RequestBody ProtokolTemelDTO protokolTemelDTO)
        throws URISyntaxException {
        log.debug("REST request to save ProtokolTemel : {}", protokolTemelDTO);
        if (protokolTemelDTO.getId() != null) {
            throw new BadRequestAlertException("A new protokolTemel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProtokolTemelDTO result = protokolTemelService.save(protokolTemelDTO);
        return ResponseEntity
            .created(new URI("/api/protokol-temels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /protokol-temels/:id} : Updates an existing protokolTemel.
     *
     * @param id the id of the protokolTemelDTO to save.
     * @param protokolTemelDTO the protokolTemelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated protokolTemelDTO,
     * or with status {@code 400 (Bad Request)} if the protokolTemelDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the protokolTemelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/protokol-temels/{id}")
    public ResponseEntity<ProtokolTemelDTO> updateProtokolTemel(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ProtokolTemelDTO protokolTemelDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ProtokolTemel : {}, {}", id, protokolTemelDTO);
        if (protokolTemelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, protokolTemelDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!protokolTemelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProtokolTemelDTO result = protokolTemelService.save(protokolTemelDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, protokolTemelDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /protokol-temels/:id} : Partial updates given fields of an existing protokolTemel, field will ignore if it is null
     *
     * @param id the id of the protokolTemelDTO to save.
     * @param protokolTemelDTO the protokolTemelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated protokolTemelDTO,
     * or with status {@code 400 (Bad Request)} if the protokolTemelDTO is not valid,
     * or with status {@code 404 (Not Found)} if the protokolTemelDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the protokolTemelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/protokol-temels/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProtokolTemelDTO> partialUpdateProtokolTemel(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ProtokolTemelDTO protokolTemelDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProtokolTemel partially : {}, {}", id, protokolTemelDTO);
        if (protokolTemelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, protokolTemelDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!protokolTemelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProtokolTemelDTO> result = protokolTemelService.partialUpdate(protokolTemelDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, protokolTemelDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /protokol-temels} : get all the protokolTemels.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of protokolTemels in body.
     */
    @GetMapping("/protokol-temels")
    public ResponseEntity<List<ProtokolTemelDTO>> getAllProtokolTemels(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of ProtokolTemels");
        Page<ProtokolTemelDTO> page = protokolTemelService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /protokol-temels/:id} : get the "id" protokolTemel.
     *
     * @param id the id of the protokolTemelDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the protokolTemelDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/protokol-temels/{id}")
    public ResponseEntity<ProtokolTemelDTO> getProtokolTemel(@PathVariable Long id) {
        log.debug("REST request to get ProtokolTemel : {}", id);
        Optional<ProtokolTemelDTO> protokolTemelDTO = protokolTemelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(protokolTemelDTO);
    }

    /**
     * {@code DELETE  /protokol-temels/:id} : delete the "id" protokolTemel.
     *
     * @param id the id of the protokolTemelDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/protokol-temels/{id}")
    public ResponseEntity<Void> deleteProtokolTemel(@PathVariable Long id) {
        log.debug("REST request to delete ProtokolTemel : {}", id);
        protokolTemelService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
