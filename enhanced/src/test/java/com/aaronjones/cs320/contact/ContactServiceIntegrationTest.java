package com.aaronjones.cs320.contact;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

/**
 * CS 499 enhancement: this is the integration test that was impossible before the repository
 * abstraction existed. It exercises ContactService together with a real FileContactRepository —
 * not a mock and not the in-memory default — and confirms data actually survives being saved and
 * reloaded from a brand-new ContactService instance, closing the gap identified in the CS 320 project
 * reflection ("no integration testing... no external data source").
 */
public class ContactServiceIntegrationTest {

    private Path tempFile;

    @BeforeEach
    public void setup() throws IOException {
        tempFile = Files.createTempFile("contacts", ".txt");
    }

    @AfterEach
    public void cleanup() throws IOException {
        Files.deleteIfExists(tempFile);
    }

    @Test
    public void savedContactSurvivesReloadFromFreshRepositoryInstance() {
        ContactService service = new ContactService(new FileContactRepository(tempFile.toString()));
        service.addContact(new Contact("001", "John", "Doe", "1234567890", "123 Main St"));

        // A brand-new service + brand-new repository instance, pointed at the same file —
        // simulates the program restarting.
        ContactService reloaded = new ContactService(new FileContactRepository(tempFile.toString()));

        Contact contact = reloaded.getContact("001");
        assertNotNull(contact, "Contact should have survived being reloaded from disk");
        assertEquals("John", contact.getFirstName());
        assertEquals("123 Main St", contact.getAddress());
    }

    @Test
    public void updatesArePersistedAcrossReload() {
        ContactService service = new ContactService(new FileContactRepository(tempFile.toString()));
        service.addContact(new Contact("002", "Jane", "Smith", "5551234567", "1 Oak Ave"));
        service.updatePhone("002", "5559876543");

        ContactService reloaded = new ContactService(new FileContactRepository(tempFile.toString()));
        assertEquals("5559876543", reloaded.getContact("002").getPhone());
    }

    @Test
    public void deletesArePersistedAcrossReload() {
        ContactService service = new ContactService(new FileContactRepository(tempFile.toString()));
        service.addContact(new Contact("003", "Sam", "Lee", "5550001111", "2 Pine Rd"));
        service.deleteContact("003");

        ContactService reloaded = new ContactService(new FileContactRepository(tempFile.toString()));
        assertNull(reloaded.getContact("003"));
    }
}
