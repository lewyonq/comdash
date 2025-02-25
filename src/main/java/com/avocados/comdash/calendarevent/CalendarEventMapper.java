package com.avocados.comdash.calendarevent;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel="spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CalendarEventMapper {

    CalendarEvent toEntity(CalendarEventRequestDTO dto);

    CalendarEventResponseDTO toResponseDTO(CalendarEvent event);
}
