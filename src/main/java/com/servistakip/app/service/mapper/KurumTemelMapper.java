package com.servistakip.app.service.mapper;

import com.servistakip.app.domain.KurumTemel;
import com.servistakip.app.service.dto.KurumTemelDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link KurumTemel} and its DTO {@link KurumTemelDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface KurumTemelMapper extends EntityMapper<KurumTemelDTO, KurumTemel> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    KurumTemelDTO toDtoId(KurumTemel kurumTemel);
}
