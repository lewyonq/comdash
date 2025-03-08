package com.avocados.comdash.calendar;

import com.avocados.comdash.calendar.dto.CalendarEventRequestDTO;
import com.avocados.comdash.calendar.dto.CalendarEventResponseDTO;
import com.avocados.comdash.config.CurrentUser;
import com.avocados.comdash.exception.ResourceNotFoundException;
import com.avocados.comdash.model.entity.CalendarEvent;
import com.avocados.comdash.model.entity.User;
import com.avocados.comdash.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CalendarEventServiceTest {
    private CalendarEventRequestDTO validRequest;
    private CalendarEvent calendarEvent;
    private CalendarEventResponseDTO responseDTO;

    @Mock
    private CalendarEventRepository calendarEventRepository;

    @Mock
    private CurrentUser currentUser;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CalendarEventMapper calendarEventMapper;

    @InjectMocks
    private CalendarEventService calendarEventService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        validRequest = new CalendarEventRequestDTO();
        validRequest.setTitle("Title 1");
        validRequest.setDescription("Desc 1");
        validRequest.setPlace("Some address");
        validRequest.setStartTime(LocalDateTime.of(2025,2,27,17,11));
        validRequest.setEndTime(LocalDateTime.of(2025,2,27,17,51));

        calendarEvent = new CalendarEvent();
        calendarEvent.setId(1L);
        calendarEvent.setTitle("Title 1");
        calendarEvent.setDescription("Desc 1");
        calendarEvent.setPlace("Some address");
        calendarEvent.setStartTime(LocalDateTime.of(2025,2,27,17,11));
        calendarEvent.setEndTime(LocalDateTime.of(2025,2,27,17,51));

        responseDTO = new CalendarEventResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setTitle("Title 1");
        responseDTO.setDescription("Desc 1");
        responseDTO.setPlace("Some address");
        responseDTO.setStartTime(LocalDateTime.of(2025,2,27,17,11));
        responseDTO.setEndTime(LocalDateTime.of(2025,2,27,17,51));
    }

    @Test
    void createCalendarEvent_ValidRequest_ReturnsResponseDTO() {
        when(calendarEventMapper.toEntity(validRequest)).thenReturn(calendarEvent);
        when(calendarEventRepository.save(calendarEvent)).thenReturn(calendarEvent);
        when(calendarEventMapper.toResponseDTO(calendarEvent)).thenReturn(responseDTO);
        when(currentUser.getCurrentUser()).thenReturn(new User());

        CalendarEventResponseDTO result = calendarEventService.createCalendarEvent(validRequest);

        assertNotNull(result);
        assertEquals(responseDTO, result);
        verify(calendarEventMapper).toEntity(validRequest);
        verify(calendarEventRepository).save(calendarEvent);
        verify(calendarEventMapper).toResponseDTO(calendarEvent);
    }

    @Test
    void updateCalendarEvent_ValidRequest_ReturnsUpdatedDTO() {
        Long id = 1L;
        when(calendarEventRepository.findById(id)).thenReturn(Optional.of(calendarEvent));
        when(calendarEventRepository.save(calendarEvent)).thenReturn(calendarEvent);
        when(calendarEventMapper.toResponseDTO(calendarEvent)).thenReturn(responseDTO);

        CalendarEventResponseDTO result = calendarEventService.updateCalendarEvent(validRequest, id);

        assertNotNull(result);
        assertEquals(responseDTO, result);
        verify(calendarEventMapper).updateEntityFromDTO(validRequest, calendarEvent);
        verify(calendarEventRepository).save(calendarEvent);
    }

    @Test
    void updateCalendarEvent_NonExistingId_ThrowsResourceNotFoundException() {
        Long id = 99L;
        when(calendarEventRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> calendarEventService.updateCalendarEvent(validRequest, id));
    }

    @Test
    void deleteCalendarEvent_ExistingId_DeletesSuccessfully() {
        Long id = 1L;
        when(calendarEventRepository.existsById(id)).thenReturn(true);

        calendarEventService.deleteCalendarEvent(id);

        verify(calendarEventRepository).deleteById(id);
    }

    @Test
    void deleteCalendarEvent_NonExistingId_ThrowsResourceNotFoundException() {
        Long id = 99L;
        when(calendarEventRepository.existsById(id)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class,
                () -> calendarEventService.deleteCalendarEvent(id));
    }

    @Test
    void getCalendarEvent_ExistingId_ReturnsResponseDTO() {
        Long id = 1L;
        when(calendarEventRepository.findById(id)).thenReturn(Optional.of(calendarEvent));
        when(calendarEventMapper.toResponseDTO(calendarEvent)).thenReturn(responseDTO);

        CalendarEventResponseDTO result = calendarEventService.getCalendarEvent(id);

        assertNotNull(result);
        assertEquals(responseDTO, result);
        verify(calendarEventRepository).findById(id);
    }

    @Test
    void getCalendarEvent_NonExistingId_ThrowsResourceNotFoundException() {
        Long id = 99L;
        when(calendarEventRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> calendarEventService.getCalendarEvent(id));
    }

    @Test
    void getAllCalendarEvents_ReturnsListOfDTOs() {
        List<CalendarEvent> events = Collections.singletonList(calendarEvent);
        when(calendarEventRepository.findAll()).thenReturn(events);
        when(calendarEventMapper.toResponseDTO(calendarEvent)).thenReturn(responseDTO);

        List<CalendarEventResponseDTO> result = calendarEventService.getAllCalendarEvents();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(responseDTO, result.get(0));
        verify(calendarEventRepository).findAll();
    }

    @Test
    void getAllCalendarEvents_EmptyDatabase_ReturnsEmptyList() {
        when(calendarEventRepository.findAll()).thenReturn(Collections.emptyList());

        List<CalendarEventResponseDTO> result = calendarEventService.getAllCalendarEvents();

        assertTrue(result.isEmpty());
    }
}