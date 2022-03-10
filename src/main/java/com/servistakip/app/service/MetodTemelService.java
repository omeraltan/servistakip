package com.servistakip.app.service;

import com.servistakip.app.service.dto.MetodTemelDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.servistakip.app.domain.MetodTemel}.
 */
public interface MetodTemelService {
    /**
     * Save a metodTemel.
     *
     * @param metodTemelDTO the entity to save.
     * @return the persisted entity.
     */
    MetodTemelDTO save(MetodTemelDTO metodTemelDTO);

    /**
     * Partially updates a metodTemel.
     *
     * @param metodTemelDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MetodTemelDTO> partialUpdate(MetodTemelDTO metodTemelDTO);

    /**
     * Get all the metodTemels.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MetodTemelDTO> findAll(Pageable pageable);

    /**
     * Get the "id" metodTemel.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MetodTemelDTO> findOne(Long id);

    /**
     * Delete the "id" metodTemel.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
