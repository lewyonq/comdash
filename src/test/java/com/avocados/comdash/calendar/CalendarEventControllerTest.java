package com.avocados.comdash.calendar;

import com.avocados.comdash.calendar.dto.CalendarEventRequestDTO;
import com.avocados.comdash.calendar.dto.CalendarEventResponseDTO;
import com.avocados.comdash.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CalendarEventControllerTest {

    @Mock
    private CalendarEventService calendarEventService;

    @InjectMocks
    private CalendarEventController calendarEventController;

    private final Long testId = 1L;
    private final CalendarEventRequestDTO requestDTO = new CalendarEventRequestDTO();
    private final CalendarEventResponseDTO responseDTO = new CalendarEventResponseDTO();

    @BeforeEach
    void setUp() {
        responseDTO.setId(testId);
        responseDTO.setTitle("Test Event");
    }

    @Test
    void createCalendarEvent_ValidRequest_ReturnsCreatedResponse() {
        when(calendarEventService.createCalendarEvent(any(CalendarEventRequestDTO.class)))
                .thenReturn(responseDTO);

        ResponseEntity<CalendarEventResponseDTO> response =
                calendarEventController.createCalendarEvent(requestDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(responseDTO, response.getBody());
        assertEquals("api/v1/calendar-event" + testId,
                response.getHeaders().getLocation().toString());
        verify(calendarEventService).createCalendarEvent(requestDTO);
    }

    @Test
    void getCalendarEvents_ReturnsListOfEvents() {
        when(calendarEventService.getAllCalendarEvents())
                .thenReturn(List.of(responseDTO));

        ResponseEntity<List<CalendarEventResponseDTO>> response =
                calendarEventController.getCalendarEvents();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(responseDTO, response.getBody().get(0));
    }

    @Test
    void getCalendarEvents_EmptyList_ReturnsOk() {
        when(calendarEventService.getAllCalendarEvents())
                .thenReturn(List.of());

        ResponseEntity<List<CalendarEventResponseDTO>> response =
                calendarEventController.getCalendarEvents();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    void getCalendarEvent_ExistingId_ReturnsOk() {
        when(calendarEventService.getCalendarEvent(testId))
                .thenReturn(responseDTO);

        ResponseEntity<CalendarEventResponseDTO> response =
                calendarEventController.getCalendarEvent(testId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDTO, response.getBody());
    }

    @Test
    void getCalendarEvent_NonExistingId_ReturnsNotFound() {
        when(calendarEventService.getCalendarEvent(testId))
                .thenThrow(ResourceNotFoundException.class);

        ResponseEntity<CalendarEventResponseDTO> response =
                calendarEventController.getCalendarEvent(testId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void updateCalendarEvent_ValidRequest_ReturnsOk() {
        when(calendarEventService.updateCalendarEvent(eq(requestDTO), eq(testId)))
                .thenReturn(responseDTO);

        ResponseEntity<CalendarEventResponseDTO> response =
                calendarEventController.updateCalendarEvent(requestDTO, testId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDTO, response.getBody());
        verify(calendarEventService).updateCalendarEvent(requestDTO, testId);
    }

    @Test
    void updateCalendarEvent_NonExistingId_ReturnsNotFound() {
        when(calendarEventService.updateCalendarEvent(eq(requestDTO), eq(testId)))
                .thenThrow(ResourceNotFoundException.class);

        ResponseEntity<CalendarEventResponseDTO> response =
                calendarEventController.updateCalendarEvent(requestDTO, testId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void deleteCalendarEvent_ExistingId_ReturnsNoContent() {
        ResponseEntity<Void> response =
                calendarEventController.deleteCalendarEvent(testId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(calendarEventService).deleteCalendarEvent(testId);
    }

    @Test
    void deleteCalendarEvent_NonExistingId_ReturnsNotFound() {
        doThrow(ResourceNotFoundException.class)
                .when(calendarEventService).deleteCalendarEvent(testId);

        ResponseEntity<Void> response =
                calendarEventController.deleteCalendarEvent(testId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}