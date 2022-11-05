package com.macavi.service.mapper;

import com.macavi.domain.Factura;
import com.macavi.domain.ProdFact;
import com.macavi.domain.Producto;
import com.macavi.service.dto.FacturaDTO;
import com.macavi.service.dto.ProdFactDTO;
import com.macavi.service.dto.ProductoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProdFact} and its DTO {@link ProdFactDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProdFactMapper extends EntityMapper<ProdFactDTO, ProdFact> {
    @Mapping(target = "factura", source = "factura", qualifiedByName = "facturaId")
    @Mapping(target = "producto", source = "producto", qualifiedByName = "productoId")
    ProdFactDTO toDto(ProdFact s);

    @Named("facturaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FacturaDTO toDtoFacturaId(Factura factura);

    @Named("productoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProductoDTO toDtoProductoId(Producto producto);
}
