package com.servistakip.app.service.mapper;

import com.servistakip.app.domain.KlasorTemel;
import com.servistakip.app.service.dto.KlasorTemelDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link KlasorTemel} and its DTO {@link KlasorTemelDTO}.
 */
@Mapper(componentModel = "spring", uses = { ServisTemelMapper.class })
public interface KlasorTemelMapper extends EntityMapper<KlasorTemelDTO, KlasorTemel> {
    @Mapping(target = "servis", source = "servis", qualifiedByName = "id")
    KlasorTemelDTO toDto(KlasorTemel s);
}
