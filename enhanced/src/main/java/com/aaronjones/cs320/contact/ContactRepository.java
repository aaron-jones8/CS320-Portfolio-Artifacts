package com.aaronjones.cs320.contact;

import java.util.List;
import java.util.Optional;

/**
 * Storage abstraction for Contacts. Added as the CS 499 enhancement: ContactService no longer owns
 * a HashMap directly, it depends on this interface instead — which is what makes it possible to swap
 * in a different storage implementation (or a test double) without changing ContactService at all.
 */
public interface ContactRepository {
    void save(Contact contact);

    Optional<Contact> findById(String contactId);

    boolean existsById(String contactId);

    void deleteById(String contactId);

    List<Contact> findAll();
}
