package com.servistakip.app.service.mapper;

import com.servistakip.app.domain.SorunTemel;
import com.servistakip.app.service.dto.SorunTemelDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SorunTemel} and its DTO {@link SorunTemelDTO}.
 */
@Mapper(componentModel = "spring", uses = { ServisTemelMapper.class, PersonelMapper.class })
public interface SorunTemelMapper extends EntityMapper<SorunTemelDTO, SorunTemel> {
    @Mapping(target = "servis", source = "servis", qualifiedByName = "id")
    @Mapping(target = "bulan", source = "bulan", qualifiedByName = "id")
    @Mapping(target = "cozen", source = "cozen", qualifiedByName = "id")
    SorunTemelDTO toDto(SorunTemel s);
}
