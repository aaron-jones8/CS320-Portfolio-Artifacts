package com.aaronjones.cs320.appointment;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration test for AppointmentService + FileAppointmentRepository. Uses a date well into the
 * future (not just a minute) specifically because of the reload limitation documented on
 * FileAppointmentRepository — the reconstructed Appointment re-validates that its date is still in
 * the future at reload time, so a date only a few seconds out would be flaky here.
 */
public class AppointmentServiceIntegrationTest {

    private Path tempFile;

    @BeforeEach
    public void setup() throws IOException {
        tempFile = Files.createTempFile("appointments", ".txt");
    }

    @AfterEach
    public void cleanup() throws IOException {
        Files.deleteIfExists(tempFile);
    }

    private Date getFarFutureDate() {
        return new Date(System.currentTimeMillis() + 7L * 24 * 60 * 60 * 1000); // 7 days out
    }

    @Test
    public void savedAppointmentSurvivesReloadFromFreshRepositoryInstance() {
        AppointmentService service = new AppointmentService(new FileAppointmentRepository(tempFile.toString()));
        service.addAppointment(new Appointment("100", getFarFutureDate(), "Annual Checkup"));

        AppointmentService reloaded = new AppointmentService(new FileAppointmentRepository(tempFile.toString()));
        Appointment appt = reloaded.getAppointment("100");

        assertNotNull(appt, "Appointment should have survived being reloaded from disk");
        assertEquals("Annual Checkup", appt.getDescription());
    }

    @Test
    public void deletesArePersistedAcrossReload() {
        AppointmentService service = new AppointmentService(new FileAppointmentRepository(tempFile.toString()));
        service.addAppointment(new Appointment("200", getFarFutureDate(), "To Be Cancelled"));
        service.deleteAppointment("200");

        AppointmentService reloaded = new AppointmentService(new FileAppointmentRepository(tempFile.toString()));
        assertNull(reloaded.getAppointment("200"));
    }
}
