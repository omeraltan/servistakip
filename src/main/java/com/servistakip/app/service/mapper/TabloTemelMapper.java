package com.servistakip.app.service.mapper;

import com.servistakip.app.domain.TabloTemel;
import com.servistakip.app.service.dto.TabloTemelDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TabloTemel} and its DTO {@link TabloTemelDTO}.
 */
@Mapper(componentModel = "spring", uses = { ServisTemelMapper.class })
public interface TabloTemelMapper extends EntityMapper<TabloTemelDTO, TabloTemel> {
    @Mapping(target = "servis", source = "servis", qualifiedByName = "id")
    TabloTemelDTO toDto(TabloTemel s);
}
