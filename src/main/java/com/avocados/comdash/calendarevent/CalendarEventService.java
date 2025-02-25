package com.avocados.comdash.calendarevent;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CalendarEventService {
    private final CalendarEventRepository calendarEventRepository;

    public CalendarEvent createCalendarEvent(CalendarEventRequestDTO calendarEventRequest) {
        CalendarEvent calendarEvent = new CalendarEvent();
        return this.calendarEventRepository.save(calendarEvent);
    }
}
