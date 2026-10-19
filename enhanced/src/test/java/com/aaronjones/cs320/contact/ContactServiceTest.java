package com.aaronjones.cs320.contact;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ContactServiceTest {

    private ContactService service;

    // Sets up test environment with default contact on each test
    @BeforeEach
    public void setup() {
        service = new ContactService();
        service.addContact(new Contact("123", "John", "Doe", "1234567890", "123 Main St"));
    }

    // Tests that adding contact with existing ID throws an exception
    @Test
    public void testAddDuplicateContactThrowsError() {
        assertThrows(IllegalArgumentException.class, () -> {
            service.addContact(new Contact("123", "Jane", "Smith", "0987654321", "456 Elm St"));
        });
    }

    // Tests that contact deletes successfully
    @Test
    public void testDeleteContact() {
        service.deleteContact("123");
        assertNull(service.getContact("123"));
    }

    // Tests that phone number updates successfully
    @Test
    public void testUpdatePhone() {
        service.updatePhone("123", "0987654321");
        assertEquals("0987654321", service.getContact("123").getPhone());
    }

    // Tests that address updates successfully
    @Test
    public void testUpdateAddress() {
        service.updateAddress("123", "456 Elm St");
        assertEquals("456 Elm St", service.getContact("123").getAddress());
    }

    // CS 499 addition: updating an ID that doesn't exist should fail cleanly instead of throwing an
    // unchecked NullPointerException, which is what the original update methods did.
    @Test
    public void testUpdateUnknownIdThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            service.updatePhone("does-not-exist", "0987654321");
        });
    }
}
