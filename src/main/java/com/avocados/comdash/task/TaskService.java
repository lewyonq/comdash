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
        //builder goes here?
        Task task = convertToEntity(taskDTO);
        System.out.println("Creating task with title: " + task.getTitle());
        System.out.println("Status: " + task.getStatus());
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
        TaskDTO dto = new TaskDTO();
        dto.setId(task.getId()); 
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setCreator_id(task.getCreator_id());
        dto.setAssignee_id(task.getAssignee_id());
        dto.setStatus(task.getStatus());
        dto.setPrivate_flag(task.isPrivate_flag());
        dto.setCreated_at(task.getCreated_at());
        return dto;
    }

    private Task convertToEntity(TaskDTO taskDTO) {
        Task task = new Task();
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setCreator_id(taskDTO.getCreator_id());
        task.setAssignee_id(taskDTO.getAssignee_id());
        task.setStatus(taskDTO.getStatus());
        task.setPrivate_flag(taskDTO.isPrivate_flag());
        task.setCreated_at(taskDTO.getCreated_at());
        task.setReject_counter(0); // Set default value internally
        return task;
    }

    public TaskDTO updateTaskStatus(Long id, TaskStatus newStatus, String comment) {
        Task task = taskRepository.findById(id).orElseThrow();
        
        if (newStatus == TaskStatus.rejected) {
            task.setReject_counter(task.getReject_counter() + 1);
            
            if (task.getReject_counter() >= 3) {
                task.setStatus(TaskStatus.CEO_escalated);
            } else {
                task.setStatus(TaskStatus.rejected);
            }
        } else {
            task.setStatus(newStatus);
        }
        if (comment != null && !comment.isEmpty()) {
            System.out.println("Updating task status with comment "  + comment);
        }
        return convertToDTO(taskRepository.save(task));
    }
}
