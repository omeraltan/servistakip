package com.servistakip.app.web.rest;

import com.servistakip.app.repository.ProjeTemelRepository;
import com.servistakip.app.service.ProjeTemelService;
import com.servistakip.app.service.dto.ProjeTemelDTO;
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
 * REST controller for managing {@link com.servistakip.app.domain.ProjeTemel}.
 */
@RestController
@RequestMapping("/api")
public class ProjeTemelResource {

    private final Logger log = LoggerFactory.getLogger(ProjeTemelResource.class);

    private static final String ENTITY_NAME = "projeTemel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProjeTemelService projeTemelService;

    private final ProjeTemelRepository projeTemelRepository;

    public ProjeTemelResource(ProjeTemelService projeTemelService, ProjeTemelRepository projeTemelRepository) {
        this.projeTemelService = projeTemelService;
        this.projeTemelRepository = projeTemelRepository;
    }

    /**
     * {@code POST  /proje-temels} : Create a new projeTemel.
     *
     * @param projeTemelDTO the projeTemelDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new projeTemelDTO, or with status {@code 400 (Bad Request)} if the projeTemel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/proje-temels")
    public ResponseEntity<ProjeTemelDTO> createProjeTemel(@Valid @RequestBody ProjeTemelDTO projeTemelDTO) throws URISyntaxException {
        log.debug("REST request to save ProjeTemel : {}", projeTemelDTO);
        if (projeTemelDTO.getId() != null) {
            throw new BadRequestAlertException("A new projeTemel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProjeTemelDTO result = projeTemelService.save(projeTemelDTO);
        return ResponseEntity
            .created(new URI("/api/proje-temels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /proje-temels/:id} : Updates an existing projeTemel.
     *
     * @param id the id of the projeTemelDTO to save.
     * @param projeTemelDTO the projeTemelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projeTemelDTO,
     * or with status {@code 400 (Bad Request)} if the projeTemelDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the projeTemelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/proje-temels/{id}")
    public ResponseEntity<ProjeTemelDTO> updateProjeTemel(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ProjeTemelDTO projeTemelDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ProjeTemel : {}, {}", id, projeTemelDTO);
        if (projeTemelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, projeTemelDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!projeTemelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProjeTemelDTO result = projeTemelService.save(projeTemelDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, projeTemelDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /proje-temels/:id} : Partial updates given fields of an existing projeTemel, field will ignore if it is null
     *
     * @param id the id of the projeTemelDTO to save.
     * @param projeTemelDTO the projeTemelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projeTemelDTO,
     * or with status {@code 400 (Bad Request)} if the projeTemelDTO is not valid,
     * or with status {@code 404 (Not Found)} if the projeTemelDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the projeTemelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/proje-temels/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProjeTemelDTO> partialUpdateProjeTemel(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ProjeTemelDTO projeTemelDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProjeTemel partially : {}, {}", id, projeTemelDTO);
        if (projeTemelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, projeTemelDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!projeTemelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProjeTemelDTO> result = projeTemelService.partialUpdate(projeTemelDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, projeTemelDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /proje-temels} : get all the projeTemels.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of projeTemels in body.
     */
    @GetMapping("/proje-temels")
    public ResponseEntity<List<ProjeTemelDTO>> getAllProjeTemels(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of ProjeTemels");
        Page<ProjeTemelDTO> page = projeTemelService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /proje-temels/:id} : get the "id" projeTemel.
     *
     * @param id the id of the projeTemelDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the projeTemelDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/proje-temels/{id}")
    public ResponseEntity<ProjeTemelDTO> getProjeTemel(@PathVariable Long id) {
        log.debug("REST request to get ProjeTemel : {}", id);
        Optional<ProjeTemelDTO> projeTemelDTO = projeTemelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(projeTemelDTO);
    }

    /**
     * {@code DELETE  /proje-temels/:id} : delete the "id" projeTemel.
     *
     * @param id the id of the projeTemelDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/proje-temels/{id}")
    public ResponseEntity<Void> deleteProjeTemel(@PathVariable Long id) {
        log.debug("REST request to delete ProjeTemel : {}", id);
        projeTemelService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
