package com.servistakip.app.web.rest;

import com.servistakip.app.repository.ServisIsRepository;
import com.servistakip.app.service.ServisIsService;
import com.servistakip.app.service.dto.ServisIsDTO;
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
 * REST controller for managing {@link com.servistakip.app.domain.ServisIs}.
 */
@RestController
@RequestMapping("/api")
public class ServisIsResource {

    private final Logger log = LoggerFactory.getLogger(ServisIsResource.class);

    private static final String ENTITY_NAME = "servisIs";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServisIsService servisIsService;

    private final ServisIsRepository servisIsRepository;

    public ServisIsResource(ServisIsService servisIsService, ServisIsRepository servisIsRepository) {
        this.servisIsService = servisIsService;
        this.servisIsRepository = servisIsRepository;
    }

    /**
     * {@code POST  /servises} : Create a new servisIs.
     *
     * @param servisIsDTO the servisIsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new servisIsDTO, or with status {@code 400 (Bad Request)} if the servisIs has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/servises")
    public ResponseEntity<ServisIsDTO> createServisIs(@Valid @RequestBody ServisIsDTO servisIsDTO) throws URISyntaxException {
        log.debug("REST request to save ServisIs : {}", servisIsDTO);
        if (servisIsDTO.getId() != null) {
            throw new BadRequestAlertException("A new servisIs cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ServisIsDTO result = servisIsService.save(servisIsDTO);
        return ResponseEntity
            .created(new URI("/api/servises/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /servises/:id} : Updates an existing servisIs.
     *
     * @param id the id of the servisIsDTO to save.
     * @param servisIsDTO the servisIsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated servisIsDTO,
     * or with status {@code 400 (Bad Request)} if the servisIsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the servisIsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/servises/{id}")
    public ResponseEntity<ServisIsDTO> updateServisIs(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ServisIsDTO servisIsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ServisIs : {}, {}", id, servisIsDTO);
        if (servisIsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, servisIsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!servisIsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ServisIsDTO result = servisIsService.save(servisIsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, servisIsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /servises/:id} : Partial updates given fields of an existing servisIs, field will ignore if it is null
     *
     * @param id the id of the servisIsDTO to save.
     * @param servisIsDTO the servisIsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated servisIsDTO,
     * or with status {@code 400 (Bad Request)} if the servisIsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the servisIsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the servisIsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/servises/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ServisIsDTO> partialUpdateServisIs(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ServisIsDTO servisIsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ServisIs partially : {}, {}", id, servisIsDTO);
        if (servisIsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, servisIsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!servisIsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ServisIsDTO> result = servisIsService.partialUpdate(servisIsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, servisIsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /servises} : get all the servises.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of servises in body.
     */
    @GetMapping("/servises")
    public ResponseEntity<List<ServisIsDTO>> getAllServises(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Servises");
        Page<ServisIsDTO> page = servisIsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /servises/:id} : get the "id" servisIs.
     *
     * @param id the id of the servisIsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the servisIsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/servises/{id}")
    public ResponseEntity<ServisIsDTO> getServisIs(@PathVariable Long id) {
        log.debug("REST request to get ServisIs : {}", id);
        Optional<ServisIsDTO> servisIsDTO = servisIsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(servisIsDTO);
    }

    /**
     * {@code DELETE  /servises/:id} : delete the "id" servisIs.
     *
     * @param id the id of the servisIsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/servises/{id}")
    public ResponseEntity<Void> deleteServisIs(@PathVariable Long id) {
        log.debug("REST request to delete ServisIs : {}", id);
        servisIsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
