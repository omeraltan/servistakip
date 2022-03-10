package com.servistakip.app.service;

import com.servistakip.app.service.dto.ServisProjeTemelDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.servistakip.app.domain.ServisProjeTemel}.
 */
public interface ServisProjeTemelService {
    /**
     * Save a servisProjeTemel.
     *
     * @param servisProjeTemelDTO the entity to save.
     * @return the persisted entity.
     */
    ServisProjeTemelDTO save(ServisProjeTemelDTO servisProjeTemelDTO);

    /**
     * Partially updates a servisProjeTemel.
     *
     * @param servisProjeTemelDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ServisProjeTemelDTO> partialUpdate(ServisProjeTemelDTO servisProjeTemelDTO);

    /**
     * Get all the servisProjeTemels.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ServisProjeTemelDTO> findAll(Pageable pageable);

    /**
     * Get the "id" servisProjeTemel.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ServisProjeTemelDTO> findOne(Long id);

    /**
     * Delete the "id" servisProjeTemel.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
