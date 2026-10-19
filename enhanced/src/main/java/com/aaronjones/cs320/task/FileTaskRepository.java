package com.aaronjones.cs320.task;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Disk-backed TaskRepository — same pipe-delimited, whole-file-rewrite approach as
 * FileContactRepository, appropriate for this artifact's scale. See FileContactRepository for the
 * fuller explanation of the trade-off.
 */
public class FileTaskRepository implements TaskRepository {
    private static final String DELIMITER = "\\|";

    private final Path storageFile;

    public FileTaskRepository(String filePath) {
        this.storageFile = Path.of(filePath);
    }

    @Override
    public void save(Task task) {
        Map<String, Task> all = loadAll();
        all.put(task.getTaskId(), task);
        writeAll(all);
    }

    @Override
    public Optional<Task> findById(String taskId) {
        return Optional.ofNullable(loadAll().get(taskId));
    }

    @Override
    public boolean existsById(String taskId) {
        return loadAll().containsKey(taskId);
    }

    @Override
    public void deleteById(String taskId) {
        Map<String, Task> all = loadAll();
        all.remove(taskId);
        writeAll(all);
    }

    @Override
    public List<Task> findAll() {
        return new ArrayList<>(loadAll().values());
    }

    private Map<String, Task> loadAll() {
        Map<String, Task> result = new LinkedHashMap<>();
        if (!Files.exists(storageFile)) {
            return result;
        }
        try {
            for (String line : Files.readAllLines(storageFile)) {
                if (line.isBlank()) continue;
                String[] parts = line.split(DELIMITER, -1);
                Task task = new Task(parts[0], parts[1], parts[2]);
                result.put(task.getTaskId(), task);
            }
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to read tasks from " + storageFile, e);
        }
        return result;
    }

    private void writeAll(Map<String, Task> all) {
        List<String> lines = new ArrayList<>();
        for (Task t : all.values()) {
            lines.add(String.join("|", t.getTaskId(), t.getName(), t.getDescription()));
        }
        try {
            Files.write(storageFile, lines);
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to write tasks to " + storageFile, e);
        }
    }
}
