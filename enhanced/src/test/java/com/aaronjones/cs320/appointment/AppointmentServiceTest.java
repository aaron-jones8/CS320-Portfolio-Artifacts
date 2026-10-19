package com.aaronjones.cs320.appointment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class AppointmentServiceTest {

    private AppointmentService service;

    // Helper method to provide future date
    private Date getFutureDate() {
        return new Date(System.currentTimeMillis() + 60000); // 1 minute in the future
    }

    @BeforeEach
    public void setUp() {
        service = new AppointmentService();
        service.addAppointment(new Appointment("100", getFutureDate(), "Initial Appointment"));
    }

    // Test that duplicate ID throws exception
    @Test
    public void testAddDuplicateAppointment() {
        assertThrows(IllegalArgumentException.class, () -> {
            service.addAppointment(new Appointment("100", getFutureDate(), "Duplicate ID"));
        });
    }

    // Test deleting an appointment
    @Test
    public void testDeleteAppointment() {
        service.deleteAppointment("100");
        assertNull(service.getAppointment("100"));
    }

    // Test deleting non-existing ID
    @Test
    public void testDeleteInvalidIdThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            service.deleteAppointment("999");
        });
    }

    // Test successful retrieval
    @Test
    public void testGetAppointment() {
        Appointment appt = service.getAppointment("100");
        assertNotNull(appt);
        assertEquals("Initial Appointment", appt.getDescription());
    }
}
