package com.avocados.comdash.task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.stream.Collectors;

// Its asked requests from TaskController > it may get data from TaskRepository
@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public TaskDTO createTask(TaskDTO taskDTO) {
        //builder goes here
        Task task = new Task();
        // convert DTO to entity and save (save is automatic function from repository
        return convertToDTO(taskRepository.save(task));
    }

    public ArrayList<TaskDTO> getAllTasks() {
        ArrayList<Task> tasks = (ArrayList<Task>) taskRepository.findAll();
        return (ArrayList<TaskDTO>) tasks.stream().map(this::convertToDTO).collect(Collectors.toList());
    }


    public TaskDTO getTaskById(Long id) {
        // exception handling, if not found
        return convertToDTO(taskRepository.findById(id).orElseThrow());
    }

    public void updateTask(Long id, TaskDTO taskDTO) {
        Task task = taskRepository.findById(id).orElseThrow();
        // update fields
        taskRepository.save(task);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    private TaskDTO convertToDTO(Task task) {
        // mapping entity to DTO
        return new TaskDTO();
    }

    private Task convertToEntity(TaskDTO taskDTO) {
        // mapping DTO to entity
        return new Task();
    }
}
