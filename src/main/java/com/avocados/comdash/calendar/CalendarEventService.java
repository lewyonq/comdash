package com.avocados.comdash.calendar;

import com.avocados.comdash.calendar.dto.CalendarEventRequestDTO;
import com.avocados.comdash.calendar.dto.CalendarEventResponseDTO;
import com.avocados.comdash.exception.ResourceNotFoundException;
import com.avocados.comdash.model.entity.CalendarEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CalendarEventService {
    private final CalendarEventRepository calendarEventRepository;
    private final CalendarEventMapper calendarEventMapper;

    public CalendarEventResponseDTO createCalendarEvent(CalendarEventRequestDTO request) {
        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null.");
        }

        CalendarEvent calendarEvent = calendarEventMapper.toEntity(request);
        CalendarEvent savedEvent = calendarEventRepository.save(calendarEvent);

        return calendarEventMapper.toResponseDTO(savedEvent);
    }

    public CalendarEventResponseDTO updateCalendarEvent(CalendarEventRequestDTO request, Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null.");
        }
        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null.");
        }

        CalendarEvent event = calendarEventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + id));

        calendarEventMapper.updateEntityFromDTO(request, event);
        CalendarEvent savedEvent = calendarEventRepository.save(event);

        return calendarEventMapper.toResponseDTO(savedEvent);
    }

    public void deleteCalendarEvent(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null.");
        }
        if (!calendarEventRepository.existsById(id)) {
            throw new ResourceNotFoundException("Event not found with id: " + id);
        }

        calendarEventRepository.deleteById(id);
    }

    public CalendarEventResponseDTO getCalendarEvent(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null.");
        }

        CalendarEvent event = calendarEventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));

        return calendarEventMapper.toResponseDTO(event);
    }

    public List<CalendarEventResponseDTO> getAllCalendarEvents() {
        List<CalendarEvent> events = calendarEventRepository.findAll();

        return events.stream()
                .map(calendarEventMapper::toResponseDTO)
                .toList();
    }
}
