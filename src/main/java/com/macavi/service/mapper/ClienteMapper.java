package com.macavi.service.mapper;

import com.macavi.domain.Cliente;
import com.macavi.domain.Locate;
import com.macavi.domain.TipoDni;
import com.macavi.service.dto.ClienteDTO;
import com.macavi.service.dto.LocateDTO;
import com.macavi.service.dto.TipoDniDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Cliente} and its DTO {@link ClienteDTO}.
 */
@Mapper(componentModel = "spring")
public interface ClienteMapper extends EntityMapper<ClienteDTO, Cliente> {
    @Mapping(target = "locate", source = "locate", qualifiedByName = "locateId")
    @Mapping(target = "tipoDni", source = "tipoDni", qualifiedByName = "tipoDniId")
    ClienteDTO toDto(Cliente s);

    @Named("locateId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    LocateDTO toDtoLocateId(Locate locate);

    @Named("tipoDniId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TipoDniDTO toDtoTipoDniId(TipoDni tipoDni);
}
