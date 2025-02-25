package com.avocados.comdash.calendar;

import com.avocados.comdash.calendar.dto.CalendarEventRequestDTO;
import com.avocados.comdash.calendar.dto.CalendarEventResponseDTO;
import com.avocados.comdash.model.entity.CalendarEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CalendarEventService {
    private final CalendarEventRepository calendarEventRepository;
    private final CalendarEventMapper calendarEventMapper;

    public CalendarEventResponseDTO createCalendarEvent(CalendarEventRequestDTO request) {
        CalendarEvent calendarEvent = calendarEventMapper.toEntity(request);
        //handle atendees
//        Set<User> attendees = userRepository.findAllById(request.getAttendeeIds());
//        calendarEvent.setAttendees(attendees);
        CalendarEvent savedEvent = calendarEventRepository.save(calendarEvent);
        return calendarEventMapper.toResponseDTO(savedEvent);
    }
}
