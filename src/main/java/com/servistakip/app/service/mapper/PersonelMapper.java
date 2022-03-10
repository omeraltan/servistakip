package com.servistakip.app.service.mapper;

import com.servistakip.app.domain.Personel;
import com.servistakip.app.service.dto.PersonelDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Personel} and its DTO {@link PersonelDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PersonelMapper extends EntityMapper<PersonelDTO, Personel> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PersonelDTO toDtoId(Personel personel);
}
