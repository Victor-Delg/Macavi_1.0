package com.macavi.service.mapper;

import com.macavi.domain.TipoDni;
import com.macavi.service.dto.TipoDniDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TipoDni} and its DTO {@link TipoDniDTO}.
 */
@Mapper(componentModel = "spring")
public interface TipoDniMapper extends EntityMapper<TipoDniDTO, TipoDni> {}
