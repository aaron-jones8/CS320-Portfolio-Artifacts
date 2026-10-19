package com.aaronjones.cs320.contact;

public class Contact {
    // Contact attributes
    private final String contactId;
    private String firstName;
    private String lastName;
    private String phone;
    private String address;

    // Constructor to create a Contact ID with validation checks
    public Contact(String contactId, String firstName, String lastName, String phone, String address) {
        if (contactId == null || contactId.length() > 10) throw new IllegalArgumentException("Invalid Contact ID");
        if (firstName == null || firstName.length() > 10) throw new IllegalArgumentException("Invalid First Name");
        if (lastName == null || lastName.length() > 10) throw new IllegalArgumentException("Invalid Last Name");
        // Fixed during CS 499 code review: the previous check was "!phone.matches(\"//d{10}\")" — a missing
        // closing parenthesis (didn't compile) plus "//d{10}" isn't valid regex for "10 digits" (that's \\d{10}).
        // An earlier version of this file used phone.length() != 10, so this was a real regression.
        if (phone == null || !phone.matches("\\d{10}")) throw new IllegalArgumentException("Invalid Phone Number");
        if (address == null || address.length() > 30) throw new IllegalArgumentException("Invalid Address");

        // Assign fields after validation
        this.contactId = contactId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.address = address;
    }

    // Getters
    public String getContactId() { return contactId; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }

    // Setters with validation checks - allows updating all details except ID
    public void setFirstName(String firstName) {
        if (firstName == null || firstName.length() > 10) throw new IllegalArgumentException("Invalid First Name");
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        if (lastName == null || lastName.length() > 10) throw new IllegalArgumentException("Invalid Last Name");
        this.lastName = lastName;
    }

    public void setPhone(String phone) {
        if (phone == null || !phone.matches("\\d{10}")) throw new IllegalArgumentException("Invalid Phone Number");
        this.phone = phone;
    }

    public void setAddress(String address) {
        if (address == null || address.length() > 30) throw new IllegalArgumentException("Invalid Address");
        this.address = address;
    }
}
