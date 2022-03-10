package com.servistakip.app.service.mapper;

import com.servistakip.app.domain.IrtibatTemel;
import com.servistakip.app.service.dto.IrtibatTemelDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link IrtibatTemel} and its DTO {@link IrtibatTemelDTO}.
 */
@Mapper(componentModel = "spring", uses = { KurumTemelMapper.class })
public interface IrtibatTemelMapper extends EntityMapper<IrtibatTemelDTO, IrtibatTemel> {
    @Mapping(target = "kurum", source = "kurum", qualifiedByName = "id")
    IrtibatTemelDTO toDto(IrtibatTemel s);
}
