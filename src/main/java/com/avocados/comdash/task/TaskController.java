package com.avocados.comdash.task;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// TaskController is called by frontend --> it asks TaskService
@RestController
@RequestMapping("/api/v1/task")
public class TaskController {

    @GetMapping("/{id}")
    public TaskDTO getTask(@PathVariable Long id){
        // Call service layer to fetch task
        return new TaskDTO();
    }
// juz nwm czy service czy controller czy kto mowi get
}
