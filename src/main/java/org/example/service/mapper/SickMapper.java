package org.example.service.mapper;

import org.example.entity.Sick;
import org.example.service.dto.SickDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SickMapper {

    SickMapper INSTANCE = Mappers.getMapper(SickMapper.class);

    SickDTO sickToSickDTO(Sick sick);

    SickDTO sickDTOtoSick(SickDTO sickDTO);
}
