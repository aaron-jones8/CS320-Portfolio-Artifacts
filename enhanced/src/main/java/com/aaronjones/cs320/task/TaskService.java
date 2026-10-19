package com.aaronjones.cs320.task;

/** CS 499 enhancement: same repository-injection pattern as ContactService. See that class for rationale. */
public class TaskService {
    private final TaskRepository repository;

    public TaskService() {
        this(new InMemoryTaskRepository());
    }

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    // Adds new task if the ID is unique
    public void addTask(Task task) {
        if (task == null || repository.existsById(task.getTaskId())) {
            throw new IllegalArgumentException("Task is null or ID already exists");
        }
        repository.save(task);
    }

    // Delete a task by its ID
    public void deleteTask(String taskId) {
        if (!repository.existsById(taskId)) {
            throw new IllegalArgumentException("Task ID not found");
        }
        repository.deleteById(taskId);
    }

    // Update name of a task by its ID.
    // CS 499 fix: now checks the ID exists before updating (see ContactService for the same fix and why).
    public void updateTaskName(String taskId, String newName) {
        Task task = getExistingTask(taskId);
        task.setName(newName);
        repository.save(task);
    }

    // Update description of a task by its ID
    public void updateTaskDescription(String taskId, String newDescription) {
        Task task = getExistingTask(taskId);
        task.setDescription(newDescription);
        repository.save(task);
    }

    // Retrieve task for validation and/or testing
    public Task getTask(String taskId) {
        return repository.findById(taskId).orElse(null);
    }

    private Task getExistingTask(String taskId) {
        return repository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task ID not found"));
    }
}
