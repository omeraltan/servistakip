package com.servistakip.app.service.mapper;

import com.servistakip.app.domain.MetodTemel;
import com.servistakip.app.service.dto.MetodTemelDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MetodTemel} and its DTO {@link MetodTemelDTO}.
 */
@Mapper(componentModel = "spring", uses = { ServisTemelMapper.class })
public interface MetodTemelMapper extends EntityMapper<MetodTemelDTO, MetodTemel> {
    @Mapping(target = "servis", source = "servis", qualifiedByName = "id")
    MetodTemelDTO toDto(MetodTemel s);
}
