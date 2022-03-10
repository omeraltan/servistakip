package com.servistakip.app.service;

import com.servistakip.app.service.dto.SorunTemelDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.servistakip.app.domain.SorunTemel}.
 */
public interface SorunTemelService {
    /**
     * Save a sorunTemel.
     *
     * @param sorunTemelDTO the entity to save.
     * @return the persisted entity.
     */
    SorunTemelDTO save(SorunTemelDTO sorunTemelDTO);

    /**
     * Partially updates a sorunTemel.
     *
     * @param sorunTemelDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SorunTemelDTO> partialUpdate(SorunTemelDTO sorunTemelDTO);

    /**
     * Get all the sorunTemels.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SorunTemelDTO> findAll(Pageable pageable);

    /**
     * Get the "id" sorunTemel.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SorunTemelDTO> findOne(Long id);

    /**
     * Delete the "id" sorunTemel.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
