package com.avocados.comdash.human;

import com.avocados.comdash.task.TaskDTO;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;

@Entity
@Data
public class Human {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    private String password;

    @Enumerated(EnumType.STRING)
    private HumanType humanType;

    //each user has a dashboard with tasks - to do list
    private ArrayList<TaskDTO> tasks = new ArrayList<>();;
}
