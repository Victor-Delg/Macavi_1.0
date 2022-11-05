package com.macavi.service.mapper;

import com.macavi.domain.Cliente;
import com.macavi.domain.Factura;
import com.macavi.service.dto.ClienteDTO;
import com.macavi.service.dto.FacturaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Factura} and its DTO {@link FacturaDTO}.
 */
@Mapper(componentModel = "spring")
public interface FacturaMapper extends EntityMapper<FacturaDTO, Factura> {
    @Mapping(target = "cliente", source = "cliente", qualifiedByName = "clienteId")
    FacturaDTO toDto(Factura s);

    @Named("clienteId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ClienteDTO toDtoClienteId(Cliente cliente);
}
