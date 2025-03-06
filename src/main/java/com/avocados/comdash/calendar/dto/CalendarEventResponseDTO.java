package com.avocados.comdash.calendar.dto;

import com.avocados.comdash.model.enums.EventType;
import com.avocados.comdash.model.enums.PriorityLevel;
import com.avocados.comdash.model.embedded.ReminderSettings;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class CalendarEventResponseDTO {
    private Long id;
    private String title;
    private String description;
    private String place;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private EventType eventType;
    private boolean recurring;
    private String recurrenceRule;
    private LocalDateTime recurrenceEndDate;
    private Set<String> tags;
    private PriorityLevel priority;
    private ReminderSettings reminder;
    private Long organizedById;
//    private Set<AttendeeDTO> attendees;
}
