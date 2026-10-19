package com.aaronjones.cs320.appointment;

import java.util.Date;

// Class representing a scheduled appointment
public class Appointment {
    private final String appointmentId; // Unique and immutable
    private final Date appointmentDate; // Cannot be set in past
    private final String description;

    // Constructor with validation
    public Appointment(String appointmentId, Date appointmentDate, String description) {
        if (appointmentId == null || appointmentId.length() > 10)
            throw new IllegalArgumentException("Invalid Appointment ID");
        if (appointmentDate == null || appointmentDate.before(new Date()))
            throw new IllegalArgumentException("Invalid Date");
        if (description == null || description.length() > 50)
            throw new IllegalArgumentException("Invalid Description");

        this.appointmentId = appointmentId;
        this.appointmentDate = appointmentDate;
        this.description = description;
    }

    // Getters only (no setters as fields are immutable) — a deliberate design choice worth keeping
    // exactly as-is: every field, including the date, can only be set once.
    public String getAppointmentId() { return appointmentId; }
    public Date getAppointmentDate() { return appointmentDate; }
    public String getDescription() { return description; }
}
