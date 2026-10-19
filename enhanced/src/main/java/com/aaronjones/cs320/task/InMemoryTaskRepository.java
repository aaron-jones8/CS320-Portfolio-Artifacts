package com.aaronjones.cs320.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/** Preserves TaskService's original in-memory-only behavior. Default repository when none is injected. */
public class InMemoryTaskRepository implements TaskRepository {
    private final Map<String, Task> tasks = new HashMap<>();

    @Override
    public void save(Task task) {
        tasks.put(task.getTaskId(), task);
    }

    @Override
    public Optional<Task> findById(String taskId) {
        return Optional.ofNullable(tasks.get(taskId));
    }

    @Override
    public boolean existsById(String taskId) {
        return tasks.containsKey(taskId);
    }

    @Override
    public void deleteById(String taskId) {
        tasks.remove(taskId);
    }

    @Override
    public List<Task> findAll() {
        return new ArrayList<>(tasks.values());
    }
}
