package com.servistakip.app.service.impl;

import com.servistakip.app.domain.ServisProjeTemel;
import com.servistakip.app.repository.ServisProjeTemelRepository;
import com.servistakip.app.service.ServisProjeTemelService;
import com.servistakip.app.service.dto.ServisProjeTemelDTO;
import com.servistakip.app.service.mapper.ServisProjeTemelMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ServisProjeTemel}.
 */
@Service
@Transactional
public class ServisProjeTemelServiceImpl implements ServisProjeTemelService {

    private final Logger log = LoggerFactory.getLogger(ServisProjeTemelServiceImpl.class);

    private final ServisProjeTemelRepository servisProjeTemelRepository;

    private final ServisProjeTemelMapper servisProjeTemelMapper;

    public ServisProjeTemelServiceImpl(
        ServisProjeTemelRepository servisProjeTemelRepository,
        ServisProjeTemelMapper servisProjeTemelMapper
    ) {
        this.servisProjeTemelRepository = servisProjeTemelRepository;
        this.servisProjeTemelMapper = servisProjeTemelMapper;
    }

    @Override
    public ServisProjeTemelDTO save(ServisProjeTemelDTO servisProjeTemelDTO) {
        log.debug("Request to save ServisProjeTemel : {}", servisProjeTemelDTO);
        ServisProjeTemel servisProjeTemel = servisProjeTemelMapper.toEntity(servisProjeTemelDTO);
        servisProjeTemel = servisProjeTemelRepository.save(servisProjeTemel);
        return servisProjeTemelMapper.toDto(servisProjeTemel);
    }

    @Override
    public Optional<ServisProjeTemelDTO> partialUpdate(ServisProjeTemelDTO servisProjeTemelDTO) {
        log.debug("Request to partially update ServisProjeTemel : {}", servisProjeTemelDTO);

        return servisProjeTemelRepository
            .findById(servisProjeTemelDTO.getId())
            .map(existingServisProjeTemel -> {
                servisProjeTemelMapper.partialUpdate(existingServisProjeTemel, servisProjeTemelDTO);

                return existingServisProjeTemel;
            })
            .map(servisProjeTemelRepository::save)
            .map(servisProjeTemelMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ServisProjeTemelDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ServisProjeTemels");
        return servisProjeTemelRepository.findAll(pageable).map(servisProjeTemelMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ServisProjeTemelDTO> findOne(Long id) {
        log.debug("Request to get ServisProjeTemel : {}", id);
        return servisProjeTemelRepository.findById(id).map(servisProjeTemelMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ServisProjeTemel : {}", id);
        servisProjeTemelRepository.deleteById(id);
    }
}
