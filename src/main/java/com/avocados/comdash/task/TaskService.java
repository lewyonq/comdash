package com.avocados.comdash.task;
import org.springframework.stereotype.Service;

// Its asked requests from TaskController > it may get data from TaskRepository
@Service
public class TaskService {


    public Task createTask(){
        Task task = new Task();
        return task;
    }
}
