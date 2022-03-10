package com.servistakip.app.service;

import com.servistakip.app.domain.KurumTemel;
import com.servistakip.app.repository.KurumTemelRepository;
import com.servistakip.app.service.dto.KurumTemelDTO;
import com.servistakip.app.service.mapper.KurumTemelMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link KurumTemel}.
 */
@Service
@Transactional
public class KurumTemelService {

    private final Logger log = LoggerFactory.getLogger(KurumTemelService.class);

    private final KurumTemelRepository kurumTemelRepository;

    private final KurumTemelMapper kurumTemelMapper;

    public KurumTemelService(KurumTemelRepository kurumTemelRepository, KurumTemelMapper kurumTemelMapper) {
        this.kurumTemelRepository = kurumTemelRepository;
        this.kurumTemelMapper = kurumTemelMapper;
    }

    /**
     * Save a kurumTemel.
     *
     * @param kurumTemelDTO the entity to save.
     * @return the persisted entity.
     */
    public KurumTemelDTO save(KurumTemelDTO kurumTemelDTO) {
        log.debug("Request to save KurumTemel : {}", kurumTemelDTO);
        KurumTemel kurumTemel = kurumTemelMapper.toEntity(kurumTemelDTO);
        kurumTemel = kurumTemelRepository.save(kurumTemel);
        return kurumTemelMapper.toDto(kurumTemel);
    }

    /**
     * Partially update a kurumTemel.
     *
     * @param kurumTemelDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<KurumTemelDTO> partialUpdate(KurumTemelDTO kurumTemelDTO) {
        log.debug("Request to partially update KurumTemel : {}", kurumTemelDTO);

        return kurumTemelRepository
            .findById(kurumTemelDTO.getId())
            .map(existingKurumTemel -> {
                kurumTemelMapper.partialUpdate(existingKurumTemel, kurumTemelDTO);

                return existingKurumTemel;
            })
            .map(kurumTemelRepository::save)
            .map(kurumTemelMapper::toDto);
    }

    /**
     * Get all the kurumTemels.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<KurumTemelDTO> findAll(Pageable pageable) {
        log.debug("Request to get all KurumTemels");
        return kurumTemelRepository.findAll(pageable).map(kurumTemelMapper::toDto);
    }

    /**
     * Get one kurumTemel by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<KurumTemelDTO> findOne(Long id) {
        log.debug("Request to get KurumTemel : {}", id);
        return kurumTemelRepository.findById(id).map(kurumTemelMapper::toDto);
    }

    /**
     * Delete the kurumTemel by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete KurumTemel : {}", id);
        kurumTemelRepository.deleteById(id);
    }
}
