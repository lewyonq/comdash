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
    private Long creator_id; // TODO: need to connect it to user

    @Column
    private Long assignee_id; // TODO: need to connect it to user

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

// people have tasks
// ceo full access,
//Sales Manager creates a task for an Installer → stored in Tasks with status=pending.
//
//        Installer reviews, accepts, or rejects (with comment).
//
//After 3 rejects, task auto-escalates to CEO with a requires_ceo_review tag.