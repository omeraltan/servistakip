package com.servistakip.app.service;

import com.servistakip.app.service.dto.KlasorTemelDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.servistakip.app.domain.KlasorTemel}.
 */
public interface KlasorTemelService {
    /**
     * Save a klasorTemel.
     *
     * @param klasorTemelDTO the entity to save.
     * @return the persisted entity.
     */
    KlasorTemelDTO save(KlasorTemelDTO klasorTemelDTO);

    /**
     * Partially updates a klasorTemel.
     *
     * @param klasorTemelDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<KlasorTemelDTO> partialUpdate(KlasorTemelDTO klasorTemelDTO);

    /**
     * Get all the klasorTemels.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<KlasorTemelDTO> findAll(Pageable pageable);

    /**
     * Get the "id" klasorTemel.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<KlasorTemelDTO> findOne(Long id);

    /**
     * Delete the "id" klasorTemel.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
