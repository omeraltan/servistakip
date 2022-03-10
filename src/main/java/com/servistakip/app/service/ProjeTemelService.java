package com.servistakip.app.service;

import com.servistakip.app.service.dto.ProjeTemelDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.servistakip.app.domain.ProjeTemel}.
 */
public interface ProjeTemelService {
    /**
     * Save a projeTemel.
     *
     * @param projeTemelDTO the entity to save.
     * @return the persisted entity.
     */
    ProjeTemelDTO save(ProjeTemelDTO projeTemelDTO);

    /**
     * Partially updates a projeTemel.
     *
     * @param projeTemelDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ProjeTemelDTO> partialUpdate(ProjeTemelDTO projeTemelDTO);

    /**
     * Get all the projeTemels.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProjeTemelDTO> findAll(Pageable pageable);

    /**
     * Get the "id" projeTemel.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProjeTemelDTO> findOne(Long id);

    /**
     * Delete the "id" projeTemel.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
