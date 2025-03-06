package com.avocados.comdash.calendar;

import com.avocados.comdash.calendar.dto.CalendarEventRequestDTO;
import com.avocados.comdash.calendar.dto.CalendarEventResponseDTO;
import com.avocados.comdash.model.entity.CalendarEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel="spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CalendarEventMapper {

    CalendarEvent toEntity(CalendarEventRequestDTO dto);

    @Mapping(source = "organizedBy.id", target = "organizedById")
    CalendarEventResponseDTO toResponseDTO(CalendarEvent event);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDTO(CalendarEventRequestDTO dto, @MappingTarget CalendarEvent entity);
}
