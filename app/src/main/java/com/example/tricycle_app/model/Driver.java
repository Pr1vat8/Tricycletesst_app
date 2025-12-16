package com.example.tricycle_app.model;

public class Driver {
    private String id;
    private String name;
    private String phone;
    private String email;
    private String address;
    private String plateNumber;
    private String status; // "Verified" or "Pending"
    private boolean isSuspended;

    // Suspension Duration
    private String suspendStartDate;
    private String suspendEndDate;

    // Login Credentials
    private String username;
    private String password;

    public Driver(String id, String name, String phone, String email, String address, String plateNumber,
                  String status, boolean isSuspended, String suspendStartDate, String suspendEndDate,
                  String username, String password) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.plateNumber = plateNumber;
        this.status = status;
        this.isSuspended = isSuspended;
        this.suspendStartDate = suspendStartDate;
        this.suspendEndDate = suspendEndDate;
        this.username = username;
        this.password = password;
    }

    // CSV Format: ID,Name,Phone,Email,Address,Plate,Status,IsSuspended,Start,End,User,Pass
    public String toCsvString() {
        return id + "," + name + "," + phone + "," + email + "," + address + "," + plateNumber + "," +
                status + "," + isSuspended + "," + suspendStartDate + "," + suspendEndDate + "," +
                username + "," + password;
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
    public String getSuspendStartDate() { return suspendStartDate; }
    public void setSuspendStartDate(String s) { this.suspendStartDate = s; }
    public String getSuspendEndDate() { return suspendEndDate; }
    public void setSuspendEndDate(String s) { this.suspendEndDate = s; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}