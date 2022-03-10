package com.servistakip.app.service;

import com.servistakip.app.domain.IrtibatTemel;
import com.servistakip.app.repository.IrtibatTemelRepository;
import com.servistakip.app.service.dto.IrtibatTemelDTO;
import com.servistakip.app.service.mapper.IrtibatTemelMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link IrtibatTemel}.
 */
@Service
@Transactional
public class IrtibatTemelService {

    private final Logger log = LoggerFactory.getLogger(IrtibatTemelService.class);

    private final IrtibatTemelRepository irtibatTemelRepository;

    private final IrtibatTemelMapper irtibatTemelMapper;

    public IrtibatTemelService(IrtibatTemelRepository irtibatTemelRepository, IrtibatTemelMapper irtibatTemelMapper) {
        this.irtibatTemelRepository = irtibatTemelRepository;
        this.irtibatTemelMapper = irtibatTemelMapper;
    }

    /**
     * Save a irtibatTemel.
     *
     * @param irtibatTemelDTO the entity to save.
     * @return the persisted entity.
     */
    public IrtibatTemelDTO save(IrtibatTemelDTO irtibatTemelDTO) {
        log.debug("Request to save IrtibatTemel : {}", irtibatTemelDTO);
        IrtibatTemel irtibatTemel = irtibatTemelMapper.toEntity(irtibatTemelDTO);
        irtibatTemel = irtibatTemelRepository.save(irtibatTemel);
        return irtibatTemelMapper.toDto(irtibatTemel);
    }

    /**
     * Partially update a irtibatTemel.
     *
     * @param irtibatTemelDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<IrtibatTemelDTO> partialUpdate(IrtibatTemelDTO irtibatTemelDTO) {
        log.debug("Request to partially update IrtibatTemel : {}", irtibatTemelDTO);

        return irtibatTemelRepository
            .findById(irtibatTemelDTO.getId())
            .map(existingIrtibatTemel -> {
                irtibatTemelMapper.partialUpdate(existingIrtibatTemel, irtibatTemelDTO);

                return existingIrtibatTemel;
            })
            .map(irtibatTemelRepository::save)
            .map(irtibatTemelMapper::toDto);
    }

    /**
     * Get all the irtibatTemels.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<IrtibatTemelDTO> findAll(Pageable pageable) {
        log.debug("Request to get all IrtibatTemels");
        return irtibatTemelRepository.findAll(pageable).map(irtibatTemelMapper::toDto);
    }

    /**
     * Get one irtibatTemel by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<IrtibatTemelDTO> findOne(Long id) {
        log.debug("Request to get IrtibatTemel : {}", id);
        return irtibatTemelRepository.findById(id).map(irtibatTemelMapper::toDto);
    }

    /**
     * Delete the irtibatTemel by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete IrtibatTemel : {}", id);
        irtibatTemelRepository.deleteById(id);
    }
}
