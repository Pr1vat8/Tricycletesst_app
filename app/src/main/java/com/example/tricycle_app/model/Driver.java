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

    // New Fields
    private String licenseNumber;
    private String licenseExpirationDate; // Format: YYYY-MM-DD
    private String suspensionEndDate; // Format: YYYY-MM-DD

    public Driver(String id, String name, String phone, String email, String address, String plateNumber, String status, boolean isSuspended, String licenseNumber, String licenseExpirationDate, String suspensionEndDate) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.plateNumber = plateNumber;
        this.status = status;
        this.isSuspended = isSuspended;
        this.licenseNumber = licenseNumber;
        this.licenseExpirationDate = licenseExpirationDate;
        this.suspensionEndDate = suspensionEndDate;
    }

    // Constructor for backward compatibility (defaults new fields to empty/null)
    public Driver(String id, String name, String phone, String email, String address, String plateNumber, String status, boolean isSuspended) {
        this(id, name, phone, email, address, plateNumber, status, isSuspended, "", "", "");
    }

    public String toCsvString() {
        return id + "," + name + "," + phone + "," + email + "," + address + "," + plateNumber + "," + status + "," + isSuspended + "," + licenseNumber + "," + licenseExpirationDate + "," + suspensionEndDate;
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

    public String getLicenseNumber() { return licenseNumber; }
    public void setLicenseNumber(String licenseNumber) { this.licenseNumber = licenseNumber; }

    public String getLicenseExpirationDate() { return licenseExpirationDate; }
    public void setLicenseExpirationDate(String licenseExpirationDate) { this.licenseExpirationDate = licenseExpirationDate; }

    public String getSuspensionEndDate() { return suspensionEndDate; }
    public void setSuspensionEndDate(String suspensionEndDate) { this.suspensionEndDate = suspensionEndDate; }
}
