package com.avocados.comdash.calendar.dto;

import com.avocados.comdash.model.embedded.ReminderSettings;
import com.avocados.comdash.model.enums.EventType;
import com.avocados.comdash.model.enums.PriorityLevel;
import com.avocados.comdash.user.dto.UserResponseDto;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CalendarEventResponseDTO {
    private Long id;
    private String title;
    private String description;
    private String place;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private EventType eventType;
    private List<String> tags;
    private PriorityLevel priority;
    private ReminderSettings reminder;
    private Long organizedById;
    private List<UserResponseDto> attendees;
}
