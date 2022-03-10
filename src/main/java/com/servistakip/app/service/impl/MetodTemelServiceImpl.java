package com.servistakip.app.service.impl;

import com.servistakip.app.domain.MetodTemel;
import com.servistakip.app.repository.MetodTemelRepository;
import com.servistakip.app.service.MetodTemelService;
import com.servistakip.app.service.dto.MetodTemelDTO;
import com.servistakip.app.service.mapper.MetodTemelMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MetodTemel}.
 */
@Service
@Transactional
public class MetodTemelServiceImpl implements MetodTemelService {

    private final Logger log = LoggerFactory.getLogger(MetodTemelServiceImpl.class);

    private final MetodTemelRepository metodTemelRepository;

    private final MetodTemelMapper metodTemelMapper;

    public MetodTemelServiceImpl(MetodTemelRepository metodTemelRepository, MetodTemelMapper metodTemelMapper) {
        this.metodTemelRepository = metodTemelRepository;
        this.metodTemelMapper = metodTemelMapper;
    }

    @Override
    public MetodTemelDTO save(MetodTemelDTO metodTemelDTO) {
        log.debug("Request to save MetodTemel : {}", metodTemelDTO);
        MetodTemel metodTemel = metodTemelMapper.toEntity(metodTemelDTO);
        metodTemel = metodTemelRepository.save(metodTemel);
        return metodTemelMapper.toDto(metodTemel);
    }

    @Override
    public Optional<MetodTemelDTO> partialUpdate(MetodTemelDTO metodTemelDTO) {
        log.debug("Request to partially update MetodTemel : {}", metodTemelDTO);

        return metodTemelRepository
            .findById(metodTemelDTO.getId())
            .map(existingMetodTemel -> {
                metodTemelMapper.partialUpdate(existingMetodTemel, metodTemelDTO);

                return existingMetodTemel;
            })
            .map(metodTemelRepository::save)
            .map(metodTemelMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MetodTemelDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MetodTemels");
        return metodTemelRepository.findAll(pageable).map(metodTemelMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MetodTemelDTO> findOne(Long id) {
        log.debug("Request to get MetodTemel : {}", id);
        return metodTemelRepository.findById(id).map(metodTemelMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete MetodTemel : {}", id);
        metodTemelRepository.deleteById(id);
    }
}
