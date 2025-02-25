package com.avocados.comdash.calendarevent;

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
