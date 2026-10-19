package com.aaronjones.cs320.contact;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ContactTest {

    // Tests validity of Contact object creation
    @Test
    public void testValidContactCreation() {
        Contact contact = new Contact("123", "John", "Doe", "1234567890", "123 Main St");
        assertEquals("John", contact.getFirstName());
        assertEquals("Doe", contact.getLastName());
        assertEquals("1234567890", contact.getPhone());
        assertEquals("123 Main St", contact.getAddress());
    }

    // Tests for invalid phone number.
    // CS 499 fix: the original version of this test passed "1234567890" — a fully valid 10-digit
    // number — while asserting that construction should throw. It was testing the opposite of what its
    // name claimed. It now uses an actually invalid number.
    @Test
    public void testInvalidPhone() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Contact("123", "John", "Doe", "12345", "123 Main St");
        });
    }

    // Tests updating the first name field
    @Test
    public void testFirstNameUpdate() {
        Contact contact = new Contact("123", "John", "Doe", "1234567890", "123 Main St");
        contact.setFirstName("Jane");
        assertEquals("Jane", contact.getFirstName());
    }
}
