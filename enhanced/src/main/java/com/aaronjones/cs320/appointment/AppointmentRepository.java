package com.aaronjones.cs320.appointment;

import java.util.List;
import java.util.Optional;

/** Storage abstraction for Appointments — same pattern as ContactRepository. See that interface for rationale. */
public interface AppointmentRepository {
    void save(Appointment appointment);

    Optional<Appointment> findById(String appointmentId);

    boolean existsById(String appointmentId);

    void deleteById(String appointmentId);

    List<Appointment> findAll();
}
