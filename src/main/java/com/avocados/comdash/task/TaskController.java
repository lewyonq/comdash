package com.avocados.comdash.task;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// TaskController is called by frontend --> it asks TaskService
// all http calls posts get, mappings and so on, but it only calls methods from service - not doing anything really
@RestController
@RequestMapping("/api/v1/task")
public class TaskController {

    @GetMapping("/{id}")
    public TaskDTO getTask(@PathVariable Long id){
        // Call service layer to fetch task
        return new TaskDTO();
    }

}
