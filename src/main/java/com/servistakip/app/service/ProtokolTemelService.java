package com.servistakip.app.service;

import com.servistakip.app.domain.ProtokolTemel;
import com.servistakip.app.repository.ProtokolTemelRepository;
import com.servistakip.app.service.dto.ProtokolTemelDTO;
import com.servistakip.app.service.mapper.ProtokolTemelMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ProtokolTemel}.
 */
@Service
@Transactional
public class ProtokolTemelService {

    private final Logger log = LoggerFactory.getLogger(ProtokolTemelService.class);

    private final ProtokolTemelRepository protokolTemelRepository;

    private final ProtokolTemelMapper protokolTemelMapper;

    public ProtokolTemelService(ProtokolTemelRepository protokolTemelRepository, ProtokolTemelMapper protokolTemelMapper) {
        this.protokolTemelRepository = protokolTemelRepository;
        this.protokolTemelMapper = protokolTemelMapper;
    }

    /**
     * Save a protokolTemel.
     *
     * @param protokolTemelDTO the entity to save.
     * @return the persisted entity.
     */
    public ProtokolTemelDTO save(ProtokolTemelDTO protokolTemelDTO) {
        log.debug("Request to save ProtokolTemel : {}", protokolTemelDTO);
        ProtokolTemel protokolTemel = protokolTemelMapper.toEntity(protokolTemelDTO);
        protokolTemel = protokolTemelRepository.save(protokolTemel);
        return protokolTemelMapper.toDto(protokolTemel);
    }

    /**
     * Partially update a protokolTemel.
     *
     * @param protokolTemelDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ProtokolTemelDTO> partialUpdate(ProtokolTemelDTO protokolTemelDTO) {
        log.debug("Request to partially update ProtokolTemel : {}", protokolTemelDTO);

        return protokolTemelRepository
            .findById(protokolTemelDTO.getId())
            .map(existingProtokolTemel -> {
                protokolTemelMapper.partialUpdate(existingProtokolTemel, protokolTemelDTO);

                return existingProtokolTemel;
            })
            .map(protokolTemelRepository::save)
            .map(protokolTemelMapper::toDto);
    }

    /**
     * Get all the protokolTemels.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProtokolTemelDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProtokolTemels");
        return protokolTemelRepository.findAll(pageable).map(protokolTemelMapper::toDto);
    }

    /**
     * Get one protokolTemel by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProtokolTemelDTO> findOne(Long id) {
        log.debug("Request to get ProtokolTemel : {}", id);
        return protokolTemelRepository.findById(id).map(protokolTemelMapper::toDto);
    }

    /**
     * Delete the protokolTemel by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProtokolTemel : {}", id);
        protokolTemelRepository.deleteById(id);
    }
}
