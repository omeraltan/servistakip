package com.servistakip.app.service;

import com.servistakip.app.domain.Personel;
import com.servistakip.app.repository.PersonelRepository;
import com.servistakip.app.service.dto.PersonelDTO;
import com.servistakip.app.service.mapper.PersonelMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Personel}.
 */
@Service
@Transactional
public class PersonelService {

    private final Logger log = LoggerFactory.getLogger(PersonelService.class);

    private final PersonelRepository personelRepository;

    private final PersonelMapper personelMapper;

    public PersonelService(PersonelRepository personelRepository, PersonelMapper personelMapper) {
        this.personelRepository = personelRepository;
        this.personelMapper = personelMapper;
    }

    /**
     * Save a personel.
     *
     * @param personelDTO the entity to save.
     * @return the persisted entity.
     */
    public PersonelDTO save(PersonelDTO personelDTO) {
        log.debug("Request to save Personel : {}", personelDTO);
        Personel personel = personelMapper.toEntity(personelDTO);
        personel = personelRepository.save(personel);
        return personelMapper.toDto(personel);
    }

    /**
     * Partially update a personel.
     *
     * @param personelDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PersonelDTO> partialUpdate(PersonelDTO personelDTO) {
        log.debug("Request to partially update Personel : {}", personelDTO);

        return personelRepository
            .findById(personelDTO.getId())
            .map(existingPersonel -> {
                personelMapper.partialUpdate(existingPersonel, personelDTO);

                return existingPersonel;
            })
            .map(personelRepository::save)
            .map(personelMapper::toDto);
    }

    /**
     * Get all the personels.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PersonelDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Personels");
        return personelRepository.findAll(pageable).map(personelMapper::toDto);
    }

    /**
     * Get one personel by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PersonelDTO> findOne(Long id) {
        log.debug("Request to get Personel : {}", id);
        return personelRepository.findById(id).map(personelMapper::toDto);
    }

    /**
     * Delete the personel by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Personel : {}", id);
        personelRepository.deleteById(id);
    }
}
