package com.avocados.comdash.task;
import org.springframework.web.bind.annotation.RestController;

// TaskController is called by frontend --> it asks TaskService
@RestController
public class TaskController {

    private final TaskService service;
    public TaskController(TaskService service){
        this.service = service;
    }
}
