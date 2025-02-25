package com.avocados.comdash.model.entity;

import com.avocados.comdash.model.embedded.ReminderSettings;
import com.avocados.comdash.model.enums.EventStatus;
import com.avocados.comdash.model.enums.EventType;
import com.avocados.comdash.model.enums.PriorityLevel;
import com.avocados.comdash.model.enums.RecurrenceRule;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class CalendarEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column
    private String description;

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    @Column(nullable = false)
    private String place;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private LocalDateTime endTime;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EventType eventType;

    @Enumerated(EnumType.STRING)
    private EventStatus status;

    @Column
    private String colorCode;

    //todo: after user will be added
//    @ManyToOne
//    private User organizer;
//
//    @ManyToMany
//    private Set<User> attendees = new HashSet<>();

    //todo: after tasks will be added
//    @OneToMany
//    private List<Task> relatedTasks = new ArrayList<>();

    //todo: after orders will be added
//    @OneToMany
//    private List<Order> relatedOrders = new ArrayList<>();

    @Column
    private boolean isRecurring = false;

    @Enumerated(EnumType.STRING)
    private RecurrenceRule recurrenceRule;

    @Column
    private LocalDateTime recurrenceEndDate;

    @ElementCollection
    private Set<String> tags = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private PriorityLevel priority;

    @Embedded
    private ReminderSettings reminder;
}
