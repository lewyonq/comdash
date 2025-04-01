package com.avocados.comdash.calendar;

import com.avocados.comdash.calendar.dto.CalendarEventRequestDTO;
import com.avocados.comdash.calendar.dto.CalendarEventResponseDTO;
import com.avocados.comdash.model.entity.CalendarEvent;
import com.avocados.comdash.model.entity.User;
import com.avocados.comdash.user.UserMapper;
import com.avocados.comdash.user.dto.UserResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.Set;

@Mapper(componentModel="spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, uses = UserMapper.class)
public interface CalendarEventMapper {

    CalendarEvent toEntity(CalendarEventRequestDTO dto);

    @Mapping(source = "organizedBy.id", target = "organizedById")
    @Mapping(source = "attendees", target = "attendees")
    CalendarEventResponseDTO toResponseDTO(CalendarEvent event);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDTO(CalendarEventRequestDTO dto, @MappingTarget CalendarEvent entity);

    Set<UserResponseDto> mapUsers(Set<User> users);
}
