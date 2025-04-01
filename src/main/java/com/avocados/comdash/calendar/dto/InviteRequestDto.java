package com.avocados.comdash.calendar.dto;

import lombok.Data;

import java.util.List;

@Data
public class InviteRequestDto {
    private List<Long> userIds;
}
