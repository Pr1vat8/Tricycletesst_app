package com.example.tricycle_app.model;

public class FareLocation {
    private String name;
    private String description; // e.g., "123 Main St"
    private String baseFare;    // e.g., "15.00"

    public FareLocation(String name, String description, String baseFare) {
        this.name = name;
        this.description = description;
        this.baseFare = baseFare;
    }

    public String toCsvString() {
        return name + "," + description + "," + baseFare;
    }

    // Getters and Setters
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getBaseFare() { return baseFare; }

    public void setBaseFare(String baseFare) { this.baseFare = baseFare; }
}