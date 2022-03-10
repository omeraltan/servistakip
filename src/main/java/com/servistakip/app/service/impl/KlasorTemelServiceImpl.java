package com.servistakip.app.service.impl;

import com.servistakip.app.domain.KlasorTemel;
import com.servistakip.app.repository.KlasorTemelRepository;
import com.servistakip.app.service.KlasorTemelService;
import com.servistakip.app.service.dto.KlasorTemelDTO;
import com.servistakip.app.service.mapper.KlasorTemelMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link KlasorTemel}.
 */
@Service
@Transactional
public class KlasorTemelServiceImpl implements KlasorTemelService {

    private final Logger log = LoggerFactory.getLogger(KlasorTemelServiceImpl.class);

    private final KlasorTemelRepository klasorTemelRepository;

    private final KlasorTemelMapper klasorTemelMapper;

    public KlasorTemelServiceImpl(KlasorTemelRepository klasorTemelRepository, KlasorTemelMapper klasorTemelMapper) {
        this.klasorTemelRepository = klasorTemelRepository;
        this.klasorTemelMapper = klasorTemelMapper;
    }

    @Override
    public KlasorTemelDTO save(KlasorTemelDTO klasorTemelDTO) {
        log.debug("Request to save KlasorTemel : {}", klasorTemelDTO);
        KlasorTemel klasorTemel = klasorTemelMapper.toEntity(klasorTemelDTO);
        klasorTemel = klasorTemelRepository.save(klasorTemel);
        return klasorTemelMapper.toDto(klasorTemel);
    }

    @Override
    public Optional<KlasorTemelDTO> partialUpdate(KlasorTemelDTO klasorTemelDTO) {
        log.debug("Request to partially update KlasorTemel : {}", klasorTemelDTO);

        return klasorTemelRepository
            .findById(klasorTemelDTO.getId())
            .map(existingKlasorTemel -> {
                klasorTemelMapper.partialUpdate(existingKlasorTemel, klasorTemelDTO);

                return existingKlasorTemel;
            })
            .map(klasorTemelRepository::save)
            .map(klasorTemelMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<KlasorTemelDTO> findAll(Pageable pageable) {
        log.debug("Request to get all KlasorTemels");
        return klasorTemelRepository.findAll(pageable).map(klasorTemelMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<KlasorTemelDTO> findOne(Long id) {
        log.debug("Request to get KlasorTemel : {}", id);
        return klasorTemelRepository.findById(id).map(klasorTemelMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete KlasorTemel : {}", id);
        klasorTemelRepository.deleteById(id);
    }
}
