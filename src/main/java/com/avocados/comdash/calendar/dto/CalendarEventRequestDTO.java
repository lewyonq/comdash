package com.avocados.comdash.calendar.dto;

import com.avocados.comdash.model.enums.EventType;
import com.avocados.comdash.model.enums.PriorityLevel;
import com.avocados.comdash.model.enums.RecurrenceRule;
import com.avocados.comdash.model.embedded.ReminderSettings;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
public class CalendarEventRequestDTO {
    private String title;
    private String description;
    private String place;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private EventType eventType;
//    private Set<User> attendees = new HashSet<>();
//    private List<Task> relatedTasks;
//    private List<Order> relatedOrders;
    private boolean recurring;
    private RecurrenceRule recurrenceRule;
    private LocalDateTime recurrenceEndDate;
    private Set<String> tags = new HashSet<>();
    private PriorityLevel priority;
    private ReminderSettings reminder;
}
