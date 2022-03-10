package com.servistakip.app.service;

import com.servistakip.app.service.dto.ServisIsDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.servistakip.app.domain.ServisIs}.
 */
public interface ServisIsService {
    /**
     * Save a servisIs.
     *
     * @param servisIsDTO the entity to save.
     * @return the persisted entity.
     */
    ServisIsDTO save(ServisIsDTO servisIsDTO);

    /**
     * Partially updates a servisIs.
     *
     * @param servisIsDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ServisIsDTO> partialUpdate(ServisIsDTO servisIsDTO);

    /**
     * Get all the servises.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ServisIsDTO> findAll(Pageable pageable);

    /**
     * Get the "id" servisIs.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ServisIsDTO> findOne(Long id);

    /**
     * Delete the "id" servisIs.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
