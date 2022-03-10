package com.servistakip.app.service.impl;

import com.servistakip.app.domain.SorunTemel;
import com.servistakip.app.repository.SorunTemelRepository;
import com.servistakip.app.service.SorunTemelService;
import com.servistakip.app.service.dto.SorunTemelDTO;
import com.servistakip.app.service.mapper.SorunTemelMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SorunTemel}.
 */
@Service
@Transactional
public class SorunTemelServiceImpl implements SorunTemelService {

    private final Logger log = LoggerFactory.getLogger(SorunTemelServiceImpl.class);

    private final SorunTemelRepository sorunTemelRepository;

    private final SorunTemelMapper sorunTemelMapper;

    public SorunTemelServiceImpl(SorunTemelRepository sorunTemelRepository, SorunTemelMapper sorunTemelMapper) {
        this.sorunTemelRepository = sorunTemelRepository;
        this.sorunTemelMapper = sorunTemelMapper;
    }

    @Override
    public SorunTemelDTO save(SorunTemelDTO sorunTemelDTO) {
        log.debug("Request to save SorunTemel : {}", sorunTemelDTO);
        SorunTemel sorunTemel = sorunTemelMapper.toEntity(sorunTemelDTO);
        sorunTemel = sorunTemelRepository.save(sorunTemel);
        return sorunTemelMapper.toDto(sorunTemel);
    }

    @Override
    public Optional<SorunTemelDTO> partialUpdate(SorunTemelDTO sorunTemelDTO) {
        log.debug("Request to partially update SorunTemel : {}", sorunTemelDTO);

        return sorunTemelRepository
            .findById(sorunTemelDTO.getId())
            .map(existingSorunTemel -> {
                sorunTemelMapper.partialUpdate(existingSorunTemel, sorunTemelDTO);

                return existingSorunTemel;
            })
            .map(sorunTemelRepository::save)
            .map(sorunTemelMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SorunTemelDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SorunTemels");
        return sorunTemelRepository.findAll(pageable).map(sorunTemelMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SorunTemelDTO> findOne(Long id) {
        log.debug("Request to get SorunTemel : {}", id);
        return sorunTemelRepository.findById(id).map(sorunTemelMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SorunTemel : {}", id);
        sorunTemelRepository.deleteById(id);
    }
}
