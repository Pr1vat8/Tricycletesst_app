package com.example.tricycle_app.model;

public class Passenger {
    private String id;
    private String name;
    private String phone;
    private String email;
    private String address;
    // New Fields
    private String gender;
    private String birthDate;
    private String age;

    private boolean isSuspended;
    private String dateJoined;
    private String suspendStartDate;
    private String suspendEndDate;
    private String username;
    private String password;

    public Passenger(String id, String name, String phone, String email, String address,
                     String gender, String birthDate, String age,
                     boolean isSuspended, String dateJoined, String suspendStartDate, String suspendEndDate,
                     String username, String password) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.gender = gender;
        this.birthDate = birthDate;
        this.age = age;
        this.isSuspended = isSuspended;
        this.dateJoined = dateJoined;
        this.suspendStartDate = suspendStartDate;
        this.suspendEndDate = suspendEndDate;
        this.username = username;
        this.password = password;
    }

    // Updated CSV Format
    public String toCsvString() {
        return id + "," + name + "," + phone + "," + email + "," + address + "," +
                gender + "," + birthDate + "," + age + "," +
                isSuspended + "," + dateJoined + "," +
                (suspendStartDate == null ? "" : suspendStartDate) + "," +
                (suspendEndDate == null ? "" : suspendEndDate) + "," +
                username + "," + password;
    }

    // Getters & Setters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
    public String getAddress() { return address; }
    public String getGender() { return gender; }
    public String getBirthDate() { return birthDate; }
    public String getAge() { return age; }
    public boolean isSuspended() { return isSuspended; }
    public String getDateJoined() { return dateJoined; }
    public String getSuspendStartDate() { return suspendStartDate; }
    public String getSuspendEndDate() { return suspendEndDate; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }

    public void setName(String name) { this.name = name; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setEmail(String email) { this.email = email; }
    public void setAddress(String address) { this.address = address; }
    public void setSuspended(boolean suspended) { isSuspended = suspended; }
    public void setSuspendStartDate(String s) { this.suspendStartDate = s; }
    public void setSuspendEndDate(String s) { this.suspendEndDate = s; }
}