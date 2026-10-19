package com.aaronjones.cs320.task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {

    // Tests creating a valid task
    @Test
    public void testValidTaskCreation() {
        Task task = new Task("001", "Code Review", "Review project code for quality");
        assertEquals("001", task.getTaskId());
        assertEquals("Code Review", task.getName());
        assertEquals("Review project code for quality", task.getDescription());
    }

    // Tests name field length restriction
    @Test
    public void testNameTooLong() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Task("002", "ThisNameIsWayTooLongToBeValid", "Short Desc");
        });
    }

    // Tests description update
    @Test
    public void testUpdateDescription() {
        Task task = new Task("003", "Write Tests", "Initial Description");
        task.setDescription("Updated description");
        assertEquals("Updated description", task.getDescription());
    }
}
