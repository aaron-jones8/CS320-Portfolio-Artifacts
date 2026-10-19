package com.aaronjones.cs320.contact;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * A second ContactRepository implementation that actually persists data to disk, so it survives the
 * program restarting — the concrete gap identified in the CS 320 project reflection ("each service was
 * built in isolation, and there was no user interface or external data source"). This is intentionally
 * simple: one pipe-delimited line per contact, the whole file rewritten on every change. That's the
 * right trade-off for this artifact's scale (a handful of contacts in a student project); a production
 * version would use a real embedded database instead of rewriting the whole file on every write.
 *
 * Field values are assumed not to contain the "|" delimiter, which holds given the existing validation
 * (contact ID, name fields, and address are all short, plain strings).
 */
public class FileContactRepository implements ContactRepository {
    private static final String DELIMITER = "\\|";

    private final Path storageFile;

    public FileContactRepository(String filePath) {
        this.storageFile = Path.of(filePath);
    }

    @Override
    public void save(Contact contact) {
        Map<String, Contact> all = loadAll();
        all.put(contact.getContactId(), contact);
        writeAll(all);
    }

    @Override
    public Optional<Contact> findById(String contactId) {
        return Optional.ofNullable(loadAll().get(contactId));
    }

    @Override
    public boolean existsById(String contactId) {
        return loadAll().containsKey(contactId);
    }

    @Override
    public void deleteById(String contactId) {
        Map<String, Contact> all = loadAll();
        all.remove(contactId);
        writeAll(all);
    }

    @Override
    public List<Contact> findAll() {
        return new ArrayList<>(loadAll().values());
    }

    private Map<String, Contact> loadAll() {
        Map<String, Contact> result = new LinkedHashMap<>();
        if (!Files.exists(storageFile)) {
            return result;
        }
        try {
            for (String line : Files.readAllLines(storageFile)) {
                if (line.isBlank()) continue;
                String[] parts = line.split(DELIMITER, -1);
                Contact contact = new Contact(parts[0], parts[1], parts[2], parts[3], parts[4]);
                result.put(contact.getContactId(), contact);
            }
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to read contacts from " + storageFile, e);
        }
        return result;
    }

    private void writeAll(Map<String, Contact> all) {
        List<String> lines = new ArrayList<>();
        for (Contact c : all.values()) {
            lines.add(String.join("|", c.getContactId(), c.getFirstName(), c.getLastName(), c.getPhone(), c.getAddress()));
        }
        try {
            Files.write(storageFile, lines);
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to write contacts to " + storageFile, e);
        }
    }
}
