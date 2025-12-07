package com.example.tricycle_app.model;

public class User {
    private String name;
    private String phone;
    private String email;
    private String address;
    private String gender;
    private String birthDate;
    private String age;        // New Field
    private String memberSince;

    public User(String name, String phone, String email, String address, String gender, String birthDate, String age, String memberSince) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.gender = gender;
        this.birthDate = birthDate;
        this.age = age;
        this.memberSince = memberSince;
    }

    // CSV format: Name,Phone,Email,Address,Gender,BirthDate,Age,MemberSince
    public String toCsvString() {
        return name + "," + phone + "," + email + "," + address + "," + gender + "," + birthDate + "," + age + "," + memberSince;
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

    // Setters
    public void setName(String name) { this.name = name; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setEmail(String email) { this.email = email; }
    public void setAddress(String address) { this.address = address; }
    public void setGender(String gender) { this.gender = gender; }
    public void setBirthDate(String birthDate) { this.birthDate = birthDate; }
    public void setAge(String age) { this.age = age; }
}