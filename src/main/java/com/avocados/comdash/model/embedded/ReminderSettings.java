package com.avocados.comdash.model.embedded;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.time.Duration;

@Embeddable
@Data
public class ReminderSettings {
    private Duration reminderBefore;
    private String reminderMethod;
}
