package com.avocados.comdash.user.dto;

import java.util.List;

import com.avocados.comdash.model.entity.CalendarEvent;

import lombok.Data;

@Data
public class UserResponseDto {
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
}
