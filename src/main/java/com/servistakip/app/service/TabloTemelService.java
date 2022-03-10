package com.servistakip.app.service;

import com.servistakip.app.service.dto.TabloTemelDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.servistakip.app.domain.TabloTemel}.
 */
public interface TabloTemelService {
    /**
     * Save a tabloTemel.
     *
     * @param tabloTemelDTO the entity to save.
     * @return the persisted entity.
     */
    TabloTemelDTO save(TabloTemelDTO tabloTemelDTO);

    /**
     * Partially updates a tabloTemel.
     *
     * @param tabloTemelDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TabloTemelDTO> partialUpdate(TabloTemelDTO tabloTemelDTO);

    /**
     * Get all the tabloTemels.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TabloTemelDTO> findAll(Pageable pageable);

    /**
     * Get the "id" tabloTemel.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TabloTemelDTO> findOne(Long id);

    /**
     * Delete the "id" tabloTemel.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
