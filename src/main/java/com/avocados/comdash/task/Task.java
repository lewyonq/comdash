package com.avocados.comdash.task;
import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Entity
@Data
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String description; //comments

    @Column
    private Long creator_id;

    @Column
    private Long assignee_id;

    @Column
    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @Column
    private boolean is_private;

    @Column
    private int reject_counter;

    @Column
    private Date created_at;
}