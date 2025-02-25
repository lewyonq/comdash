package com.avocados.comdash.calendarevent;

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
    private boolean isRecurring = false;
    private RecurrenceRule recurrenceRule;
    private LocalDateTime recurrenceEndDate;
    private Set<String> tags = new HashSet<>();
    private PriorityLevel priority;
    private ReminderSettings reminder;
}
