package com.avocados.comdash.task;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;

// Its asked requests from TaskController > it may get data from TaskRepository
@Service
public class TaskService {

    @PutMapping //czy moze post?
    public Task createTask(){
        // skad wziac parametry?
        Task task = new Task();
        // czemu konstruktor nie chce parametrow skowo jest RequiredArgsConstructor?
        System.out.println("create task");
        // dodac do repository jakos
        return task;
    }

    public Task getTask(Long id){
        // zapytanie do task repository i znalezienie po id i zwrocenie
        return new Task();
    }
}
