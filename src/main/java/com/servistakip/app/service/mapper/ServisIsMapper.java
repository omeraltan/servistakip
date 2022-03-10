package com.servistakip.app.service.mapper;

import com.servistakip.app.domain.ServisIs;
import com.servistakip.app.service.dto.ServisIsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ServisIs} and its DTO {@link ServisIsDTO}.
 */
@Mapper(componentModel = "spring", uses = { PersonelMapper.class, ServisTemelMapper.class })
public interface ServisIsMapper extends EntityMapper<ServisIsDTO, ServisIs> {
    @Mapping(target = "sorumlu", source = "sorumlu", qualifiedByName = "id")
    @Mapping(target = "yapan", source = "yapan", qualifiedByName = "id")
    @Mapping(target = "servis", source = "servis", qualifiedByName = "id")
    ServisIsDTO toDto(ServisIs s);
}
