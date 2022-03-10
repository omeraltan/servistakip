package com.servistakip.app.service;

import com.servistakip.app.domain.ServisTemel;
import com.servistakip.app.repository.ServisTemelRepository;
import com.servistakip.app.service.dto.ServisTemelDTO;
import com.servistakip.app.service.mapper.ServisTemelMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ServisTemel}.
 */
@Service
@Transactional
public class ServisTemelService {

    private final Logger log = LoggerFactory.getLogger(ServisTemelService.class);

    private final ServisTemelRepository servisTemelRepository;

    private final ServisTemelMapper servisTemelMapper;

    public ServisTemelService(ServisTemelRepository servisTemelRepository, ServisTemelMapper servisTemelMapper) {
        this.servisTemelRepository = servisTemelRepository;
        this.servisTemelMapper = servisTemelMapper;
    }

    /**
     * Save a servisTemel.
     *
     * @param servisTemelDTO the entity to save.
     * @return the persisted entity.
     */
    public ServisTemelDTO save(ServisTemelDTO servisTemelDTO) {
        log.debug("Request to save ServisTemel : {}", servisTemelDTO);
        ServisTemel servisTemel = servisTemelMapper.toEntity(servisTemelDTO);
        servisTemel = servisTemelRepository.save(servisTemel);
        return servisTemelMapper.toDto(servisTemel);
    }

    /**
     * Partially update a servisTemel.
     *
     * @param servisTemelDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ServisTemelDTO> partialUpdate(ServisTemelDTO servisTemelDTO) {
        log.debug("Request to partially update ServisTemel : {}", servisTemelDTO);

        return servisTemelRepository
            .findById(servisTemelDTO.getId())
            .map(existingServisTemel -> {
                servisTemelMapper.partialUpdate(existingServisTemel, servisTemelDTO);

                return existingServisTemel;
            })
            .map(servisTemelRepository::save)
            .map(servisTemelMapper::toDto);
    }

    /**
     * Get all the servisTemels.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ServisTemelDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ServisTemels");
        return servisTemelRepository.findAll(pageable).map(servisTemelMapper::toDto);
    }

    /**
     * Get one servisTemel by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ServisTemelDTO> findOne(Long id) {
        log.debug("Request to get ServisTemel : {}", id);
        return servisTemelRepository.findById(id).map(servisTemelMapper::toDto);
    }

    /**
     * Delete the servisTemel by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ServisTemel : {}", id);
        servisTemelRepository.deleteById(id);
    }
}
