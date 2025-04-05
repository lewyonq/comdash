package com.avocados.comdash.calendar.dto;

import com.avocados.comdash.model.enums.EventType;
import com.avocados.comdash.model.enums.PriorityLevel;
import com.avocados.comdash.model.enums.RecurrenceRule;
import com.avocados.comdash.model.embedded.ReminderSettings;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class CalendarEventRequestDTO {
    private String title;
    private String description;
    private String place;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private EventType eventType;
    private List<Long> attendeesId = new ArrayList<>();
//    private List<Task> relatedTasks;
//    private List<Order> relatedOrders;
    private List<String> tags = new ArrayList<>();
    private PriorityLevel priority;
    private ReminderSettings reminder;
}
