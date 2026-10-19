package com.aaronjones.cs320.appointment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/** Preserves AppointmentService's original in-memory-only behavior. Default repository when none is injected. */
public class InMemoryAppointmentRepository implements AppointmentRepository {
    private final Map<String, Appointment> appointments = new HashMap<>();

    @Override
    public void save(Appointment appointment) {
        appointments.put(appointment.getAppointmentId(), appointment);
    }

    @Override
    public Optional<Appointment> findById(String appointmentId) {
        return Optional.ofNullable(appointments.get(appointmentId));
    }

    @Override
    public boolean existsById(String appointmentId) {
        return appointments.containsKey(appointmentId);
    }

    @Override
    public void deleteById(String appointmentId) {
        appointments.remove(appointmentId);
    }

    @Override
    public List<Appointment> findAll() {
        return new ArrayList<>(appointments.values());
    }
}
