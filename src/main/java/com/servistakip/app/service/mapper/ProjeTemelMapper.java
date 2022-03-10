package com.servistakip.app.service.mapper;

import com.servistakip.app.domain.ProjeTemel;
import com.servistakip.app.service.dto.ProjeTemelDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProjeTemel} and its DTO {@link ProjeTemelDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProjeTemelMapper extends EntityMapper<ProjeTemelDTO, ProjeTemel> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProjeTemelDTO toDtoId(ProjeTemel projeTemel);
}
