package com.servistakip.app.web.rest;

import com.servistakip.app.repository.TabloTemelRepository;
import com.servistakip.app.service.TabloTemelService;
import com.servistakip.app.service.dto.TabloTemelDTO;
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
 * REST controller for managing {@link com.servistakip.app.domain.TabloTemel}.
 */
@RestController
@RequestMapping("/api")
public class TabloTemelResource {

    private final Logger log = LoggerFactory.getLogger(TabloTemelResource.class);

    private static final String ENTITY_NAME = "tabloTemel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TabloTemelService tabloTemelService;

    private final TabloTemelRepository tabloTemelRepository;

    public TabloTemelResource(TabloTemelService tabloTemelService, TabloTemelRepository tabloTemelRepository) {
        this.tabloTemelService = tabloTemelService;
        this.tabloTemelRepository = tabloTemelRepository;
    }

    /**
     * {@code POST  /tablo-temels} : Create a new tabloTemel.
     *
     * @param tabloTemelDTO the tabloTemelDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tabloTemelDTO, or with status {@code 400 (Bad Request)} if the tabloTemel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tablo-temels")
    public ResponseEntity<TabloTemelDTO> createTabloTemel(@Valid @RequestBody TabloTemelDTO tabloTemelDTO) throws URISyntaxException {
        log.debug("REST request to save TabloTemel : {}", tabloTemelDTO);
        if (tabloTemelDTO.getId() != null) {
            throw new BadRequestAlertException("A new tabloTemel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TabloTemelDTO result = tabloTemelService.save(tabloTemelDTO);
        return ResponseEntity
            .created(new URI("/api/tablo-temels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tablo-temels/:id} : Updates an existing tabloTemel.
     *
     * @param id the id of the tabloTemelDTO to save.
     * @param tabloTemelDTO the tabloTemelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tabloTemelDTO,
     * or with status {@code 400 (Bad Request)} if the tabloTemelDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tabloTemelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tablo-temels/{id}")
    public ResponseEntity<TabloTemelDTO> updateTabloTemel(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TabloTemelDTO tabloTemelDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TabloTemel : {}, {}", id, tabloTemelDTO);
        if (tabloTemelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tabloTemelDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tabloTemelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TabloTemelDTO result = tabloTemelService.save(tabloTemelDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tabloTemelDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /tablo-temels/:id} : Partial updates given fields of an existing tabloTemel, field will ignore if it is null
     *
     * @param id the id of the tabloTemelDTO to save.
     * @param tabloTemelDTO the tabloTemelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tabloTemelDTO,
     * or with status {@code 400 (Bad Request)} if the tabloTemelDTO is not valid,
     * or with status {@code 404 (Not Found)} if the tabloTemelDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the tabloTemelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/tablo-temels/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TabloTemelDTO> partialUpdateTabloTemel(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TabloTemelDTO tabloTemelDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TabloTemel partially : {}, {}", id, tabloTemelDTO);
        if (tabloTemelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tabloTemelDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tabloTemelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TabloTemelDTO> result = tabloTemelService.partialUpdate(tabloTemelDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tabloTemelDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /tablo-temels} : get all the tabloTemels.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tabloTemels in body.
     */
    @GetMapping("/tablo-temels")
    public ResponseEntity<List<TabloTemelDTO>> getAllTabloTemels(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of TabloTemels");
        Page<TabloTemelDTO> page = tabloTemelService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tablo-temels/:id} : get the "id" tabloTemel.
     *
     * @param id the id of the tabloTemelDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tabloTemelDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tablo-temels/{id}")
    public ResponseEntity<TabloTemelDTO> getTabloTemel(@PathVariable Long id) {
        log.debug("REST request to get TabloTemel : {}", id);
        Optional<TabloTemelDTO> tabloTemelDTO = tabloTemelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tabloTemelDTO);
    }

    /**
     * {@code DELETE  /tablo-temels/:id} : delete the "id" tabloTemel.
     *
     * @param id the id of the tabloTemelDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tablo-temels/{id}")
    public ResponseEntity<Void> deleteTabloTemel(@PathVariable Long id) {
        log.debug("REST request to delete TabloTemel : {}", id);
        tabloTemelService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
