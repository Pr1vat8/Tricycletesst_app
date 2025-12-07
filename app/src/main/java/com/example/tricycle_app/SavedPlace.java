package com.example.tricycle_app;

public class SavedPlace {
    private String name;
    private String address;

    public SavedPlace(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public String getName() { return name; }
    public String getAddress() { return address; }
}