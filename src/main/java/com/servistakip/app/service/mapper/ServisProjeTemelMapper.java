package com.servistakip.app.service.mapper;

import com.servistakip.app.domain.ServisProjeTemel;
import com.servistakip.app.service.dto.ServisProjeTemelDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ServisProjeTemel} and its DTO {@link ServisProjeTemelDTO}.
 */
@Mapper(componentModel = "spring", uses = { ServisTemelMapper.class, ProjeTemelMapper.class })
public interface ServisProjeTemelMapper extends EntityMapper<ServisProjeTemelDTO, ServisProjeTemel> {
    @Mapping(target = "servis", source = "servis", qualifiedByName = "id")
    @Mapping(target = "proje", source = "proje", qualifiedByName = "id")
    ServisProjeTemelDTO toDto(ServisProjeTemel s);
}
