package com.avocados.comdash.calendar;

import com.avocados.comdash.calendar.dto.CalendarEventRequestDTO;
import com.avocados.comdash.calendar.dto.CalendarEventResponseDTO;
import com.avocados.comdash.exception.ResourceNotFoundException;
import com.avocados.comdash.model.entity.CalendarEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import org.springframework.lang.NonNull;

@Service
@RequiredArgsConstructor
public class CalendarEventService {
    private static final String EVENT_NOT_FOUND = "Event not found with id: %d";
    
    private final CalendarEventRepository calendarEventRepository;
    private final CalendarEventMapper calendarEventMapper;

    public CalendarEventResponseDTO createCalendarEvent(@NonNull CalendarEventRequestDTO request) {
        CalendarEvent calendarEvent = calendarEventMapper.toEntity(request);
        CalendarEvent savedEvent = calendarEventRepository.save(calendarEvent);
        return calendarEventMapper.toResponseDTO(savedEvent);
    }

    public CalendarEventResponseDTO updateCalendarEvent(@NonNull CalendarEventRequestDTO request, @NonNull Long id) {
        CalendarEvent event = findEventById(id);
        calendarEventMapper.updateEntityFromDTO(request, event);
        CalendarEvent savedEvent = calendarEventRepository.save(event);
        return calendarEventMapper.toResponseDTO(savedEvent);
    }

    public void deleteCalendarEvent(@NonNull Long id) {
        if (!calendarEventRepository.existsById(id)) {
            throw new ResourceNotFoundException(String.format(EVENT_NOT_FOUND, id));
        }
        calendarEventRepository.deleteById(id);
    }

    public CalendarEventResponseDTO getCalendarEvent(@NonNull Long id) {
        return calendarEventMapper.toResponseDTO(findEventById(id));
    }

    public List<CalendarEventResponseDTO> getAllCalendarEvents() {
        List<CalendarEvent> events = calendarEventRepository.findAll();

        return events.stream()
                .map(calendarEventMapper::toResponseDTO)
                .toList();
    }

    private CalendarEvent findEventById(@NonNull Long id) {
        return calendarEventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(EVENT_NOT_FOUND, id)));
    }
}
