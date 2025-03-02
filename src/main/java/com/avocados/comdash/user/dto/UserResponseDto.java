package com.avocados.comdash.user.dto;

import java.util.List;

import com.avocados.comdash.model.entity.CalendarEvent;

import lombok.Data;

@Data
public class UserResponseDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private List<CalendarEvent> events;
}
