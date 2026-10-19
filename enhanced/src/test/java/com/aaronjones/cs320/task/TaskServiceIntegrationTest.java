package com.aaronjones.cs320.task;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

/** Integration test for TaskService + FileTaskRepository — same rationale as ContactServiceIntegrationTest. */
public class TaskServiceIntegrationTest {

    private Path tempFile;

    @BeforeEach
    public void setup() throws IOException {
        tempFile = Files.createTempFile("tasks", ".txt");
    }

    @AfterEach
    public void cleanup() throws IOException {
        Files.deleteIfExists(tempFile);
    }

    @Test
    public void savedTaskSurvivesReloadFromFreshRepositoryInstance() {
        TaskService service = new TaskService(new FileTaskRepository(tempFile.toString()));
        service.addTask(new Task("001", "Code Review", "Review project code for quality"));

        TaskService reloaded = new TaskService(new FileTaskRepository(tempFile.toString()));
        Task task = reloaded.getTask("001");

        assertNotNull(task, "Task should have survived being reloaded from disk");
        assertEquals("Code Review", task.getName());
    }

    @Test
    public void updatesArePersistedAcrossReload() {
        TaskService service = new TaskService(new FileTaskRepository(tempFile.toString()));
        service.addTask(new Task("002", "Write Docs", "Initial description"));
        service.updateTaskDescription("002", "Updated description");

        TaskService reloaded = new TaskService(new FileTaskRepository(tempFile.toString()));
        assertEquals("Updated description", reloaded.getTask("002").getDescription());
    }
}
