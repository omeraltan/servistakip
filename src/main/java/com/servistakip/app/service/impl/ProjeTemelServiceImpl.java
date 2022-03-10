package com.servistakip.app.service.impl;

import com.servistakip.app.domain.ProjeTemel;
import com.servistakip.app.repository.ProjeTemelRepository;
import com.servistakip.app.service.ProjeTemelService;
import com.servistakip.app.service.dto.ProjeTemelDTO;
import com.servistakip.app.service.mapper.ProjeTemelMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ProjeTemel}.
 */
@Service
@Transactional
public class ProjeTemelServiceImpl implements ProjeTemelService {

    private final Logger log = LoggerFactory.getLogger(ProjeTemelServiceImpl.class);

    private final ProjeTemelRepository projeTemelRepository;

    private final ProjeTemelMapper projeTemelMapper;

    public ProjeTemelServiceImpl(ProjeTemelRepository projeTemelRepository, ProjeTemelMapper projeTemelMapper) {
        this.projeTemelRepository = projeTemelRepository;
        this.projeTemelMapper = projeTemelMapper;
    }

    @Override
    public ProjeTemelDTO save(ProjeTemelDTO projeTemelDTO) {
        log.debug("Request to save ProjeTemel : {}", projeTemelDTO);
        ProjeTemel projeTemel = projeTemelMapper.toEntity(projeTemelDTO);
        projeTemel = projeTemelRepository.save(projeTemel);
        return projeTemelMapper.toDto(projeTemel);
    }

    @Override
    public Optional<ProjeTemelDTO> partialUpdate(ProjeTemelDTO projeTemelDTO) {
        log.debug("Request to partially update ProjeTemel : {}", projeTemelDTO);

        return projeTemelRepository
            .findById(projeTemelDTO.getId())
            .map(existingProjeTemel -> {
                projeTemelMapper.partialUpdate(existingProjeTemel, projeTemelDTO);

                return existingProjeTemel;
            })
            .map(projeTemelRepository::save)
            .map(projeTemelMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProjeTemelDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProjeTemels");
        return projeTemelRepository.findAll(pageable).map(projeTemelMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProjeTemelDTO> findOne(Long id) {
        log.debug("Request to get ProjeTemel : {}", id);
        return projeTemelRepository.findById(id).map(projeTemelMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProjeTemel : {}", id);
        projeTemelRepository.deleteById(id);
    }
}
