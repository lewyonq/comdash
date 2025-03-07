package com.avocados.comdash.task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;

// TaskController is called by frontend --> it asks TaskService
// all http calls posts get, mappings and so on, but it only calls methods from service - not doing anything really
@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    /* example post to verify
    {
        "title": "do the dishes",
        "description": "pls its urgent Frank",
        "creator_id": 123,
        "assignee_id": 1240,
        "status":"pending",
        "private_flag":false,
        "created_at":"2025-03-02T14:50:09.482+00:00"
    }
    */
    @PostMapping
    public ResponseEntity<TaskDTO> createTask(@RequestBody TaskDTO taskDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.createTask(taskDTO));
    }

    @GetMapping
    public ResponseEntity<ArrayList<TaskDTO>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getTaskById(
        @PathVariable Long id){
        // Call service layer to fetch task
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateTask(
        @PathVariable Long id, 
        @RequestBody TaskDTO taskDTO) {
        taskService.updateTask(id, taskDTO);
        return ResponseEntity.noContent().build();
    }


    /* example put to verify
    http://localhost:8080/api/v1/tasks/2/status?status=accepted

    http://localhost:8080/api/v1/tasks/2/status?status=REJECTED&comment=Need more details
    */ 
    @PutMapping("/{id}/status")
    public ResponseEntity<TaskDTO> updateTaskStatus(
            @PathVariable Long id,
            @RequestParam TaskStatus status,
            @RequestParam(required = false) String comment)
            /*
            I'm using it for logging purposes without storing it in the database.
                1. It helps with debugging/monitoring (you can see rejection reasons in the logs)
                2. Doesn't require database schema changes
                3. Still allows users to provide feedback when rejecting tasks
                4. Keeps the Task entity clean and focused
             */
             {
        return ResponseEntity.ok(taskService.updateTaskStatus(id, status, comment));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
