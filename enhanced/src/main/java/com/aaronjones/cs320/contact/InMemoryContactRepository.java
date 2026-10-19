package com.aaronjones.cs320.contact;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Preserves the original behavior of ContactService exactly: contacts are held in a HashMap for the
 * lifetime of the program and are lost when it exits. This is the default repository used when a
 * ContactService is constructed without one, so existing callers (and the original unit tests) don't
 * need to change.
 */
public class InMemoryContactRepository implements ContactRepository {
    private final Map<String, Contact> contacts = new HashMap<>();

    @Override
    public void save(Contact contact) {
        contacts.put(contact.getContactId(), contact);
    }

    @Override
    public Optional<Contact> findById(String contactId) {
        return Optional.ofNullable(contacts.get(contactId));
    }

    @Override
    public boolean existsById(String contactId) {
        return contacts.containsKey(contactId);
    }

    @Override
    public void deleteById(String contactId) {
        contacts.remove(contactId);
    }

    @Override
    public List<Contact> findAll() {
        return new ArrayList<>(contacts.values());
    }
}
