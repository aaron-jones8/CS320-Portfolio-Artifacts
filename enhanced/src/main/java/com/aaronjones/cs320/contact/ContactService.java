package com.aaronjones.cs320.contact;

/**
 * CS 499 enhancement: ContactService used to own a private HashMap directly, which made it impossible
 * to test against anything other than that one hardcoded storage implementation. It now depends on the
 * ContactRepository interface instead, injected through the constructor. The no-argument constructor
 * preserves the exact original behavior (in-memory only) so existing callers don't break.
 */
public class ContactService {
    private final ContactRepository repository;

    public ContactService() {
        this(new InMemoryContactRepository());
    }

    public ContactService(ContactRepository repository) {
        this.repository = repository;
    }

    // Adds new contact if ID is unique
    public void addContact(Contact contact) {
        if (contact == null || repository.existsById(contact.getContactId())) {
            throw new IllegalArgumentException("Contact ID already exists or is null");
        }
        repository.save(contact);
    }

    // Deletes contact by ID
    public void deleteContact(String contactId) {
        if (!repository.existsById(contactId)) {
            throw new IllegalArgumentException("Contact ID not found");
        }
        repository.deleteById(contactId);
    }

    // Methods for updating details of contact by ID.
    // CS 499 fix: these previously called contacts.get(contactId).setX(...) with no check that the ID
    // existed, so an unknown ID threw a raw NullPointerException instead of a clear error — inconsistent
    // with addContact/deleteContact, which both validate the ID first. They now do the same check, and
    // explicitly re-save through the repository so a file-backed repository actually persists the change.
    public void updateFirstName(String contactId, String firstName) {
        Contact contact = getExistingContact(contactId);
        contact.setFirstName(firstName);
        repository.save(contact);
    }

    public void updateLastName(String contactId, String lastName) {
        Contact contact = getExistingContact(contactId);
        contact.setLastName(lastName);
        repository.save(contact);
    }

    public void updatePhone(String contactId, String phone) {
        Contact contact = getExistingContact(contactId);
        contact.setPhone(phone);
        repository.save(contact);
    }

    public void updateAddress(String contactId, String address) {
        Contact contact = getExistingContact(contactId);
        contact.setAddress(address);
        repository.save(contact);
    }

    // Retrieves contact object by ID
    public Contact getContact(String contactId) {
        return repository.findById(contactId).orElse(null);
    }

    private Contact getExistingContact(String contactId) {
        return repository.findById(contactId)
                .orElseThrow(() -> new IllegalArgumentException("Contact ID not found"));
    }
}
