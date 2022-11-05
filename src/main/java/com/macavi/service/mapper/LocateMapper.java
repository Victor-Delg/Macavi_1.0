package com.macavi.service.mapper;

import com.macavi.domain.Locate;
import com.macavi.service.dto.LocateDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Locate} and its DTO {@link LocateDTO}.
 */
@Mapper(componentModel = "spring")
public interface LocateMapper extends EntityMapper<LocateDTO, Locate> {}
