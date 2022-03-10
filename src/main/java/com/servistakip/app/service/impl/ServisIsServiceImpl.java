package com.servistakip.app.service.impl;

import com.servistakip.app.domain.ServisIs;
import com.servistakip.app.repository.ServisIsRepository;
import com.servistakip.app.service.ServisIsService;
import com.servistakip.app.service.dto.ServisIsDTO;
import com.servistakip.app.service.mapper.ServisIsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ServisIs}.
 */
@Service
@Transactional
public class ServisIsServiceImpl implements ServisIsService {

    private final Logger log = LoggerFactory.getLogger(ServisIsServiceImpl.class);

    private final ServisIsRepository servisIsRepository;

    private final ServisIsMapper servisIsMapper;

    public ServisIsServiceImpl(ServisIsRepository servisIsRepository, ServisIsMapper servisIsMapper) {
        this.servisIsRepository = servisIsRepository;
        this.servisIsMapper = servisIsMapper;
    }

    @Override
    public ServisIsDTO save(ServisIsDTO servisIsDTO) {
        log.debug("Request to save ServisIs : {}", servisIsDTO);
        ServisIs servisIs = servisIsMapper.toEntity(servisIsDTO);
        servisIs = servisIsRepository.save(servisIs);
        return servisIsMapper.toDto(servisIs);
    }

    @Override
    public Optional<ServisIsDTO> partialUpdate(ServisIsDTO servisIsDTO) {
        log.debug("Request to partially update ServisIs : {}", servisIsDTO);

        return servisIsRepository
            .findById(servisIsDTO.getId())
            .map(existingServisIs -> {
                servisIsMapper.partialUpdate(existingServisIs, servisIsDTO);

                return existingServisIs;
            })
            .map(servisIsRepository::save)
            .map(servisIsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ServisIsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Servises");
        return servisIsRepository.findAll(pageable).map(servisIsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ServisIsDTO> findOne(Long id) {
        log.debug("Request to get ServisIs : {}", id);
        return servisIsRepository.findById(id).map(servisIsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ServisIs : {}", id);
        servisIsRepository.deleteById(id);
    }
}
