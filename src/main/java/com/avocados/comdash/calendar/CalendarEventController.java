package com.avocados.comdash.calendar;

import com.avocados.comdash.calendar.dto.CalendarEventRequestDTO;
import com.avocados.comdash.calendar.dto.CalendarEventResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/calendar-event")
public class CalendarEventController {
    private final CalendarEventService calendarEventService;

    @PostMapping
    public ResponseEntity<CalendarEventResponseDTO> createCalendarEvent(
            @RequestBody CalendarEventRequestDTO calendarEventRequest
    ) {
        return ResponseEntity.ok(calendarEventService.createCalendarEvent(calendarEventRequest));
    }
}
