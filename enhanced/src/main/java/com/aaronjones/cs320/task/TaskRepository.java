package com.aaronjones.cs320.task;

import java.util.List;
import java.util.Optional;

/** Storage abstraction for Tasks — same pattern as ContactRepository. See that interface for rationale. */
public interface TaskRepository {
    void save(Task task);

    Optional<Task> findById(String taskId);

    boolean existsById(String taskId);

    void deleteById(String taskId);

    List<Task> findAll();
}
