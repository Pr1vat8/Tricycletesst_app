package com.example.tricycle_app.model;

public class User {
    private String name;
    private String phone;
    private String email;
    private String address;
    private String gender;
    private String birthDate;
    private String age;
    private String memberSince;

    // New Fields for Suspension
    private boolean isSuspended;
    private String suspendStartDate;
    private String suspendEndDate;

    // Full Constructor (11 parameters)
    public User(String name, String phone, String email, String address, String gender, String birthDate, String age, String memberSince, boolean isSuspended, String suspendStartDate, String suspendEndDate) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.gender = gender;
        this.birthDate = birthDate;
        this.age = age;
        this.memberSince = memberSince;
        this.isSuspended = isSuspended;
        this.suspendStartDate = suspendStartDate;
        this.suspendEndDate = suspendEndDate;
    }

    // Legacy Constructor (8 parameters) - keeps old code working
    public User(String name, String phone, String email, String address, String gender, String birthDate, String age, String memberSince) {
        this(name, phone, email, address, gender, birthDate, age, memberSince, false, "", "");
    }

    // CSV Format: Name,Phone,Email,Address,Gender,BirthDate,Age,MemberSince,IsSuspended,Start,End
    public String toCsvString() {
        return name + "," + phone + "," + email + "," + address + "," + gender + "," + birthDate + "," + age + "," + memberSince + "," + isSuspended + "," + (suspendStartDate == null ? "" : suspendStartDate) + "," + (suspendEndDate == null ? "" : suspendEndDate);
    }

    // Getters
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
    public String getAddress() { return address; }
    public String getGender() { return gender; }
    public String getBirthDate() { return birthDate; }
    public String getAge() { return age; }
    public String getMemberSince() { return memberSince; }

    public boolean isSuspended() { return isSuspended; }
    public String getSuspendStartDate() { return suspendStartDate; }
    public String getSuspendEndDate() { return suspendEndDate; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setEmail(String email) { this.email = email; }
    public void setAddress(String address) { this.address = address; }
    public void setGender(String gender) { this.gender = gender; }
    public void setBirthDate(String birthDate) { this.birthDate = birthDate; }
    public void setAge(String age) { this.age = age; }

    public void setSuspended(boolean s) { this.isSuspended = s; }
    public void setSuspendStartDate(String s) { this.suspendStartDate = s; }
    public void setSuspendEndDate(String s) { this.suspendEndDate = s; }
}