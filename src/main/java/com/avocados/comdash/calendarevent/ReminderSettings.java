package com.avocados.comdash.calendarevent;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.time.Duration;

@Embeddable
@Data
public class ReminderSettings {
    private Duration reminderBefore;
    private String reminderMethod;
}
