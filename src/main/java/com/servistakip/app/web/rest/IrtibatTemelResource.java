package com.servistakip.app.web.rest;

import com.servistakip.app.repository.IrtibatTemelRepository;
import com.servistakip.app.service.IrtibatTemelService;
import com.servistakip.app.service.dto.IrtibatTemelDTO;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.servistakip.app.domain.IrtibatTemel}.
 */
@RestController
@RequestMapping("/api")
public class IrtibatTemelResource {

    private final Logger log = LoggerFactory.getLogger(IrtibatTemelResource.class);

    private static final String ENTITY_NAME = "irtibatTemel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IrtibatTemelService irtibatTemelService;

    private final IrtibatTemelRepository irtibatTemelRepository;

    public IrtibatTemelResource(IrtibatTemelService irtibatTemelService, IrtibatTemelRepository irtibatTemelRepository) {
        this.irtibatTemelService = irtibatTemelService;
        this.irtibatTemelRepository = irtibatTemelRepository;
    }

    /**
     * {@code POST  /irtibat-temels} : Create a new irtibatTemel.
     *
     * @param irtibatTemelDTO the irtibatTemelDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new irtibatTemelDTO, or with status {@code 400 (Bad Request)} if the irtibatTemel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/irtibat-temels")
    public ResponseEntity<IrtibatTemelDTO> createIrtibatTemel(@Valid @RequestBody IrtibatTemelDTO irtibatTemelDTO)
        throws URISyntaxException {
        log.debug("REST request to save IrtibatTemel : {}", irtibatTemelDTO);
        if (irtibatTemelDTO.getId() != null) {
            throw new BadRequestAlertException("A new irtibatTemel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IrtibatTemelDTO result = irtibatTemelService.save(irtibatTemelDTO);
        return ResponseEntity
            .created(new URI("/api/irtibat-temels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /irtibat-temels/:id} : Updates an existing irtibatTemel.
     *
     * @param id the id of the irtibatTemelDTO to save.
     * @param irtibatTemelDTO the irtibatTemelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated irtibatTemelDTO,
     * or with status {@code 400 (Bad Request)} if the irtibatTemelDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the irtibatTemelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/irtibat-temels/{id}")
    public ResponseEntity<IrtibatTemelDTO> updateIrtibatTemel(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody IrtibatTemelDTO irtibatTemelDTO
    ) throws URISyntaxException {
        log.debug("REST request to update IrtibatTemel : {}, {}", id, irtibatTemelDTO);
        if (irtibatTemelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, irtibatTemelDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!irtibatTemelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        IrtibatTemelDTO result = irtibatTemelService.save(irtibatTemelDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, irtibatTemelDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /irtibat-temels/:id} : Partial updates given fields of an existing irtibatTemel, field will ignore if it is null
     *
     * @param id the id of the irtibatTemelDTO to save.
     * @param irtibatTemelDTO the irtibatTemelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated irtibatTemelDTO,
     * or with status {@code 400 (Bad Request)} if the irtibatTemelDTO is not valid,
     * or with status {@code 404 (Not Found)} if the irtibatTemelDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the irtibatTemelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/irtibat-temels/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<IrtibatTemelDTO> partialUpdateIrtibatTemel(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody IrtibatTemelDTO irtibatTemelDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update IrtibatTemel partially : {}, {}", id, irtibatTemelDTO);
        if (irtibatTemelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, irtibatTemelDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!irtibatTemelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<IrtibatTemelDTO> result = irtibatTemelService.partialUpdate(irtibatTemelDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, irtibatTemelDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /irtibat-temels} : get all the irtibatTemels.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of irtibatTemels in body.
     */
    @GetMapping("/irtibat-temels")
    public ResponseEntity<List<IrtibatTemelDTO>> getAllIrtibatTemels(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of IrtibatTemels");
        Page<IrtibatTemelDTO> page = irtibatTemelService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /irtibat-temels/:id} : get the "id" irtibatTemel.
     *
     * @param id the id of the irtibatTemelDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the irtibatTemelDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/irtibat-temels/{id}")
    public ResponseEntity<IrtibatTemelDTO> getIrtibatTemel(@PathVariable Long id) {
        log.debug("REST request to get IrtibatTemel : {}", id);
        Optional<IrtibatTemelDTO> irtibatTemelDTO = irtibatTemelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(irtibatTemelDTO);
    }

    /**
     * {@code DELETE  /irtibat-temels/:id} : delete the "id" irtibatTemel.
     *
     * @param id the id of the irtibatTemelDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/irtibat-temels/{id}")
    public ResponseEntity<Void> deleteIrtibatTemel(@PathVariable Long id) {
        log.debug("REST request to delete IrtibatTemel : {}", id);
        irtibatTemelService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
