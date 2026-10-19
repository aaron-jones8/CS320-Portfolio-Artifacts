package com.aaronjones.cs320.appointment;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class AppointmentTest {

    // Creates future date helper
    private Date getFutureDate() {
        return new Date(System.currentTimeMillis() + 100000); // ~1 minute into future
    }

    // Test valid appointment creation
    @Test
    public void testValidAppointmentCreation() {
        Appointment appt = new Appointment("123", getFutureDate(), "Annual Checkup");
        assertEquals("123", appt.getAppointmentId());
        assertEquals("Annual Checkup", appt.getDescription());
    }

    // Test that null ID throws error
    @Test
    public void testNullAppointmentId() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Appointment(null, getFutureDate(), "Test");
        });
    }

    // Test that past date is rejected
    @Test
    public void testPastDateThrowsException() {
        Date past = new Date(System.currentTimeMillis() - 100000); // ~1 minute into past
        assertThrows(IllegalArgumentException.class, () -> {
            new Appointment("001", past, "Expired Appointment");
        });
    }

    // Test long description validation
    @Test
    public void testTooLongDescription() {
        String longDesc = "This description is definitely longer than fifty characters.";
        assertThrows(IllegalArgumentException.class, () -> {
            new Appointment("002", getFutureDate(), longDesc);
        });
    }
}
