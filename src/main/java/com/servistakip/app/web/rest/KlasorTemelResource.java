package com.servistakip.app.web.rest;

import com.servistakip.app.repository.KlasorTemelRepository;
import com.servistakip.app.service.KlasorTemelService;
import com.servistakip.app.service.dto.KlasorTemelDTO;
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
 * REST controller for managing {@link com.servistakip.app.domain.KlasorTemel}.
 */
@RestController
@RequestMapping("/api")
public class KlasorTemelResource {

    private final Logger log = LoggerFactory.getLogger(KlasorTemelResource.class);

    private static final String ENTITY_NAME = "klasorTemel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final KlasorTemelService klasorTemelService;

    private final KlasorTemelRepository klasorTemelRepository;

    public KlasorTemelResource(KlasorTemelService klasorTemelService, KlasorTemelRepository klasorTemelRepository) {
        this.klasorTemelService = klasorTemelService;
        this.klasorTemelRepository = klasorTemelRepository;
    }

    /**
     * {@code POST  /klasor-temels} : Create a new klasorTemel.
     *
     * @param klasorTemelDTO the klasorTemelDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new klasorTemelDTO, or with status {@code 400 (Bad Request)} if the klasorTemel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/klasor-temels")
    public ResponseEntity<KlasorTemelDTO> createKlasorTemel(@Valid @RequestBody KlasorTemelDTO klasorTemelDTO) throws URISyntaxException {
        log.debug("REST request to save KlasorTemel : {}", klasorTemelDTO);
        if (klasorTemelDTO.getId() != null) {
            throw new BadRequestAlertException("A new klasorTemel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        KlasorTemelDTO result = klasorTemelService.save(klasorTemelDTO);
        return ResponseEntity
            .created(new URI("/api/klasor-temels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /klasor-temels/:id} : Updates an existing klasorTemel.
     *
     * @param id the id of the klasorTemelDTO to save.
     * @param klasorTemelDTO the klasorTemelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated klasorTemelDTO,
     * or with status {@code 400 (Bad Request)} if the klasorTemelDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the klasorTemelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/klasor-temels/{id}")
    public ResponseEntity<KlasorTemelDTO> updateKlasorTemel(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody KlasorTemelDTO klasorTemelDTO
    ) throws URISyntaxException {
        log.debug("REST request to update KlasorTemel : {}, {}", id, klasorTemelDTO);
        if (klasorTemelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, klasorTemelDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!klasorTemelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        KlasorTemelDTO result = klasorTemelService.save(klasorTemelDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, klasorTemelDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /klasor-temels/:id} : Partial updates given fields of an existing klasorTemel, field will ignore if it is null
     *
     * @param id the id of the klasorTemelDTO to save.
     * @param klasorTemelDTO the klasorTemelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated klasorTemelDTO,
     * or with status {@code 400 (Bad Request)} if the klasorTemelDTO is not valid,
     * or with status {@code 404 (Not Found)} if the klasorTemelDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the klasorTemelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/klasor-temels/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<KlasorTemelDTO> partialUpdateKlasorTemel(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody KlasorTemelDTO klasorTemelDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update KlasorTemel partially : {}, {}", id, klasorTemelDTO);
        if (klasorTemelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, klasorTemelDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!klasorTemelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<KlasorTemelDTO> result = klasorTemelService.partialUpdate(klasorTemelDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, klasorTemelDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /klasor-temels} : get all the klasorTemels.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of klasorTemels in body.
     */
    @GetMapping("/klasor-temels")
    public ResponseEntity<List<KlasorTemelDTO>> getAllKlasorTemels(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of KlasorTemels");
        Page<KlasorTemelDTO> page = klasorTemelService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /klasor-temels/:id} : get the "id" klasorTemel.
     *
     * @param id the id of the klasorTemelDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the klasorTemelDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/klasor-temels/{id}")
    public ResponseEntity<KlasorTemelDTO> getKlasorTemel(@PathVariable Long id) {
        log.debug("REST request to get KlasorTemel : {}", id);
        Optional<KlasorTemelDTO> klasorTemelDTO = klasorTemelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(klasorTemelDTO);
    }

    /**
     * {@code DELETE  /klasor-temels/:id} : delete the "id" klasorTemel.
     *
     * @param id the id of the klasorTemelDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/klasor-temels/{id}")
    public ResponseEntity<Void> deleteKlasorTemel(@PathVariable Long id) {
        log.debug("REST request to delete KlasorTemel : {}", id);
        klasorTemelService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
