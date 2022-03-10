package com.servistakip.app.service.impl;

import com.servistakip.app.domain.TabloTemel;
import com.servistakip.app.repository.TabloTemelRepository;
import com.servistakip.app.service.TabloTemelService;
import com.servistakip.app.service.dto.TabloTemelDTO;
import com.servistakip.app.service.mapper.TabloTemelMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TabloTemel}.
 */
@Service
@Transactional
public class TabloTemelServiceImpl implements TabloTemelService {

    private final Logger log = LoggerFactory.getLogger(TabloTemelServiceImpl.class);

    private final TabloTemelRepository tabloTemelRepository;

    private final TabloTemelMapper tabloTemelMapper;

    public TabloTemelServiceImpl(TabloTemelRepository tabloTemelRepository, TabloTemelMapper tabloTemelMapper) {
        this.tabloTemelRepository = tabloTemelRepository;
        this.tabloTemelMapper = tabloTemelMapper;
    }

    @Override
    public TabloTemelDTO save(TabloTemelDTO tabloTemelDTO) {
        log.debug("Request to save TabloTemel : {}", tabloTemelDTO);
        TabloTemel tabloTemel = tabloTemelMapper.toEntity(tabloTemelDTO);
        tabloTemel = tabloTemelRepository.save(tabloTemel);
        return tabloTemelMapper.toDto(tabloTemel);
    }

    @Override
    public Optional<TabloTemelDTO> partialUpdate(TabloTemelDTO tabloTemelDTO) {
        log.debug("Request to partially update TabloTemel : {}", tabloTemelDTO);

        return tabloTemelRepository
            .findById(tabloTemelDTO.getId())
            .map(existingTabloTemel -> {
                tabloTemelMapper.partialUpdate(existingTabloTemel, tabloTemelDTO);

                return existingTabloTemel;
            })
            .map(tabloTemelRepository::save)
            .map(tabloTemelMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TabloTemelDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TabloTemels");
        return tabloTemelRepository.findAll(pageable).map(tabloTemelMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TabloTemelDTO> findOne(Long id) {
        log.debug("Request to get TabloTemel : {}", id);
        return tabloTemelRepository.findById(id).map(tabloTemelMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TabloTemel : {}", id);
        tabloTemelRepository.deleteById(id);
    }
}
