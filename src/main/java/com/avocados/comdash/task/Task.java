package com.avocados.comdash.task;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import java.util.Date;

@Entity
@Data
@RequiredArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column
    private String description; //comments

    @Column(nullable = false)
    private Long creator_id; // TODO: need to connect it to user

    @Column(nullable = false)
    private Long assignee_id; // TODO: need to connect it to user

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @Column(nullable = false)
    private boolean is_private;

    @Column(nullable = false)
    private int reject_counter;

    @Column(nullable = false)
    private Date created_at;
}

// people have tasks
// ceo full access,
//Sales Manager creates a task for an Installer → stored in Tasks with status=pending.
//
//        Installer reviews, accepts, or rejects (with comment).
//
//After 3 rejects, task auto-escalates to CEO with a requires_ceo_review tag.