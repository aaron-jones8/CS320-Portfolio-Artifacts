package com.aaronjones.cs320.appointment;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Disk-backed AppointmentRepository. The appointment date is stored as epoch milliseconds.
 *
 * Known limitation (worth calling out honestly rather than hiding it): Appointment's constructor
 * rejects any date that isn't in the future *at construction time*, by design — that's what makes an
 * Appointment immutable and always "upcoming" when created. That same check runs again when this
 * repository reconstructs an Appointment from disk, which means an appointment will fail to reload
 * once its date has passed. That's fine for this artifact's purpose (demonstrating that the repository
 * pattern makes persistence possible at all), but a production version would need a separate
 * reconstruction path that skips re-validating already-trusted, previously-valid stored data.
 */
public class FileAppointmentRepository implements AppointmentRepository {
    private static final String DELIMITER = "\\|";

    private final Path storageFile;

    public FileAppointmentRepository(String filePath) {
        this.storageFile = Path.of(filePath);
    }

    @Override
    public void save(Appointment appointment) {
        Map<String, Appointment> all = loadAll();
        all.put(appointment.getAppointmentId(), appointment);
        writeAll(all);
    }

    @Override
    public Optional<Appointment> findById(String appointmentId) {
        return Optional.ofNullable(loadAll().get(appointmentId));
    }

    @Override
    public boolean existsById(String appointmentId) {
        return loadAll().containsKey(appointmentId);
    }

    @Override
    public void deleteById(String appointmentId) {
        Map<String, Appointment> all = loadAll();
        all.remove(appointmentId);
        writeAll(all);
    }

    @Override
    public List<Appointment> findAll() {
        return new ArrayList<>(loadAll().values());
    }

    private Map<String, Appointment> loadAll() {
        Map<String, Appointment> result = new LinkedHashMap<>();
        if (!Files.exists(storageFile)) {
            return result;
        }
        try {
            for (String line : Files.readAllLines(storageFile)) {
                if (line.isBlank()) continue;
                String[] parts = line.split(DELIMITER, -1);
                Date date = new Date(Long.parseLong(parts[1]));
                Appointment appointment = new Appointment(parts[0], date, parts[2]);
                result.put(appointment.getAppointmentId(), appointment);
            }
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to read appointments from " + storageFile, e);
        }
        return result;
    }

    private void writeAll(Map<String, Appointment> all) {
        List<String> lines = new ArrayList<>();
        for (Appointment a : all.values()) {
            lines.add(String.join("|", a.getAppointmentId(), String.valueOf(a.getAppointmentDate().getTime()), a.getDescription()));
        }
        try {
            Files.write(storageFile, lines);
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to write appointments to " + storageFile, e);
        }
    }
}
