package com.aaronjones.cs320.appointment;

/**
 * CS 499 enhancement: same repository-injection pattern as ContactService and TaskService. Unlike
 * those two, there are no update methods here — Appointment has no setters, by original design, so
 * there was never an inconsistent-null-check issue to fix in this class.
 */
public class AppointmentService {
    private final AppointmentRepository repository;

    public AppointmentService() {
        this(new InMemoryAppointmentRepository());
    }

    public AppointmentService(AppointmentRepository repository) {
        this.repository = repository;
    }

    // If ID is unique, adds a new appointment
    public void addAppointment(Appointment appointment) {
        if (appointment == null || repository.existsById(appointment.getAppointmentId())) {
            throw new IllegalArgumentException("Appointment is null or ID already exists");
        }
        repository.save(appointment);
    }

    // Delete an appointment by ID
    public void deleteAppointment(String appointmentId) {
        if (!repository.existsById(appointmentId)) {
            throw new IllegalArgumentException("Appointment ID not found");
        }
        repository.deleteById(appointmentId);
    }

    // Get appointment by ID (used for testing or retrieval)
    public Appointment getAppointment(String appointmentId) {
        return repository.findById(appointmentId).orElse(null);
    }
}
