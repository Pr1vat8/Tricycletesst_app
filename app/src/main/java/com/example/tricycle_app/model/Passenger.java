package com.example.tricycle_app.model;

public class Passenger {
    private String id;
    private String name;
    private String phone;
    private String email;
    private String address;
    private boolean isSuspended;
    private String dateJoined; // New Field

    public Passenger(String id, String name, String phone, String email, String address, boolean isSuspended, String dateJoined) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.isSuspended = isSuspended;
        this.dateJoined = dateJoined;
    }

    // Update CSV format
    public String toCsvString() {
        return id + "," + name + "," + phone + "," + email + "," + address + "," + isSuspended + "," + dateJoined;
    }

    // Getters & Setters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
    public String getAddress() { return address; }
    public boolean isSuspended() { return isSuspended; }
    public String getDateJoined() { return dateJoined; }

    public void setName(String name) { this.name = name; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setEmail(String email) { this.email = email; }
    public void setAddress(String address) { this.address = address; }
    public void setSuspended(boolean suspended) { isSuspended = suspended; }
    public void setDateJoined(String dateJoined) { this.dateJoined = dateJoined; }
}