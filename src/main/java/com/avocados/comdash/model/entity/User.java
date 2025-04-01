package com.avocados.comdash.model.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity(name = "users") // to avoid postgres table name conflict
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false)
    private String lastname;

    private String phone;

    @OneToMany(mappedBy = "organizedBy", fetch = FetchType.EAGER)
    private List<CalendarEvent> organizedEvents;

    @ManyToMany(mappedBy = "attendees", fetch = FetchType.EAGER)
    private List<CalendarEvent> events;
}
