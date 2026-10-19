package com.aaronjones.cs320.task;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TaskServiceTest {

    private TaskService service;

    // Initializes TaskService and adds one task before each test
    @BeforeEach
    public void setup() {
        service = new TaskService();
        service.addTask(new Task("001", "Initial Task", "Initial Description"));
    }

    // Tests for duplicate Task ID and throws exception
    @Test
    public void testAddDuplicateTask() {
        assertThrows(IllegalArgumentException.class, () -> {
            service.addTask(new Task("001", "New Task", "Another Task"));
        });
    }

    // Tests task deletion
    @Test
    public void testDeleteTask() {
        service.deleteTask("001");
        assertNull(service.getTask("001"));
    }

    // Tests updating task name
    @Test
    public void testUpdateTaskName() {
        service.updateTaskName("001", "Updated Name");
        assertEquals("Updated Name", service.getTask("001").getName());
    }

    // Test updating task description
    @Test
    public void testUpdateTaskDescription() {
        service.updateTaskDescription("001", "Updated Description");
        assertEquals("Updated Description", service.getTask("001").getDescription());
    }

    // CS 499 addition: updating an unknown ID should fail cleanly, not with a NullPointerException.
    @Test
    public void testUpdateUnknownIdThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            service.updateTaskName("does-not-exist", "New Name");
        });
    }
}
