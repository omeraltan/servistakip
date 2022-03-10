package com.servistakip.app.service.mapper;

import com.servistakip.app.domain.ProtokolTemel;
import com.servistakip.app.service.dto.ProtokolTemelDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProtokolTemel} and its DTO {@link ProtokolTemelDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProtokolTemelMapper extends EntityMapper<ProtokolTemelDTO, ProtokolTemel> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProtokolTemelDTO toDtoId(ProtokolTemel protokolTemel);
}
