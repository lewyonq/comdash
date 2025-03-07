package com.avocados.comdash.calendar;

import com.avocados.comdash.calendar.dto.CalendarEventRequestDTO;
import com.avocados.comdash.calendar.dto.CalendarEventResponseDTO;
import com.avocados.comdash.calendar.dto.InviteRequestDto;
import com.avocados.comdash.config.CurrentUser;
import com.avocados.comdash.exception.ResourceNotFoundException;
import com.avocados.comdash.model.entity.CalendarEvent;
import com.avocados.comdash.model.entity.User;
import com.avocados.comdash.user.UserMapper;
import com.avocados.comdash.user.UserRepository;
import com.avocados.comdash.user.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CalendarEventService {
    private static final String EVENT_NOT_FOUND = "Event not found with id: %d";
    
    private final CalendarEventRepository calendarEventRepository;
    private final CalendarEventMapper calendarEventMapper;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final CurrentUser currentUser;

    public CalendarEventResponseDTO createCalendarEvent(@NonNull CalendarEventRequestDTO request) {
        CalendarEvent calendarEvent = calendarEventMapper.toEntity(request);
        calendarEvent.setOrganizedBy(currentUser.getCurrentUser());
        Set<User> attendees = new HashSet<>(userRepository.findAllById(request.getAttendeesId()));

        calendarEvent.setAttendees(attendees);
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

    public List<UserResponseDto> inviteUsers(@NonNull InviteRequestDto inviteRequestDto, Long eventId) {
        List<User> users = userRepository.findAllById(inviteRequestDto.getUserIds());
        CalendarEvent event = findEventById(eventId);

        if (users.isEmpty()) {
            throw new IllegalArgumentException("Cannot add users to this event: Users not found");
        }

        event.getAttendees().addAll(users);
        calendarEventRepository.save(event);

        return users.stream()
                .map(userMapper::toDto)
                .toList();
    }

    private CalendarEvent findEventById(@NonNull Long id) {
        return calendarEventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(EVENT_NOT_FOUND, id)));
    }
}
