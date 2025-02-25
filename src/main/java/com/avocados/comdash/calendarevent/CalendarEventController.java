package com.avocados.comdash.calendarevent;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("api/v1/calendar-event")
@RequiredArgsConstructor
public class CalendarEventController {
    private final CalendarEventService calendarEventService;

    @GetMapping
    public ResponseEntity<CalendarEvent> createCalendarEvent(CalendarEventRequestDTO calendarEventRequest) {
        return ResponseEntity.ok(calendarEventService.createCalendarEvent(calendarEventRequest));
    }
}
