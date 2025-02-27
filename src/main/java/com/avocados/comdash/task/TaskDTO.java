package com.avocados.comdash.task;
import lombok.Data;
import java.util.Date;

// it's Task Data Transfer Object - it provides a layer of encapsulation - we don't share all data from Task class
@Data
public class TaskDTO {

    private String title;
    private String description; //comments
    private Long creator_id;
    private Long assignee_id;
    private TaskStatus status;
    private boolean is_private;
    private Date created_at;
}
