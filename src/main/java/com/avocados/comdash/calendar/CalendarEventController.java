package com.avocados.comdash.calendar;

import com.avocados.comdash.calendar.dto.CalendarEventRequestDTO;
import com.avocados.comdash.calendar.dto.CalendarEventResponseDTO;
import com.avocados.comdash.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/calendar-event")
public class CalendarEventController {
    private final CalendarEventService calendarEventService;

    @PostMapping
    public ResponseEntity<CalendarEventResponseDTO> createCalendarEvent(
            @RequestBody CalendarEventRequestDTO calendarEventRequest
    ) {
        CalendarEventResponseDTO responseDTO = calendarEventService.createCalendarEvent(calendarEventRequest);
        return ResponseEntity
                .created(URI.create("api/v1/calendar-event" + responseDTO.getId()))
                .body(responseDTO);
    }

    @GetMapping
    public ResponseEntity<List<CalendarEventResponseDTO>> getCalendarEvents() {
        return ResponseEntity.ok(calendarEventService.getAllCalendarEvents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CalendarEventResponseDTO> getCalendarEvent(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(calendarEventService.getCalendarEvent(id));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CalendarEventResponseDTO> updateCalendarEvent(
            @RequestBody CalendarEventRequestDTO calendarEventRequest, @PathVariable Long id
    ) {
        try {
            return ResponseEntity.ok(calendarEventService.updateCalendarEvent(calendarEventRequest, id));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCalendarEvent(@PathVariable Long id) {
        try {
            calendarEventService.deleteCalendarEvent(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
