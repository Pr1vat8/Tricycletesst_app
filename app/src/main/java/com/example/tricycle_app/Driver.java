package com.example.tricycle_app;

public class Driver {
    private String id;
    private String name;
    private String phone;
    private String email;
    private String address;
    private String plateNumber;
    private String status; // "Verified" or "Pending"
    private boolean isSuspended;

    public Driver(String id, String name, String phone, String email, String address, String plateNumber, String status, boolean isSuspended) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.plateNumber = plateNumber;
        this.status = status;
        this.isSuspended = isSuspended;
    }

    public String toCsvString() {
        return id + "," + name + "," + phone + "," + email + "," + address + "," + plateNumber + "," + status + "," + isSuspended;
    }

    // Getters and Setters
    public String getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getPlateNumber() { return plateNumber; }
    public void setPlateNumber(String plateNumber) { this.plateNumber = plateNumber; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public boolean isSuspended() { return isSuspended; }
    public void setSuspended(boolean suspended) { isSuspended = suspended; }
}