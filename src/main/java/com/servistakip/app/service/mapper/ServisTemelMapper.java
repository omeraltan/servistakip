package com.servistakip.app.service.mapper;

import com.servistakip.app.domain.ServisTemel;
import com.servistakip.app.service.dto.ServisTemelDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ServisTemel} and its DTO {@link ServisTemelDTO}.
 */
@Mapper(componentModel = "spring", uses = { ProtokolTemelMapper.class, KurumTemelMapper.class })
public interface ServisTemelMapper extends EntityMapper<ServisTemelDTO, ServisTemel> {
    @Mapping(target = "protokol", source = "protokol", qualifiedByName = "id")
    @Mapping(target = "bakanlik", source = "bakanlik", qualifiedByName = "id")
    ServisTemelDTO toDto(ServisTemel s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ServisTemelDTO toDtoId(ServisTemel servisTemel);
}
