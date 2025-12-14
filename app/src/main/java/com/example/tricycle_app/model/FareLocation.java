package com.example.tricycle_app.model;

import java.io.Serializable;

public class FareLocation implements Serializable {
    private String name;
    private String description;
    private double baseFare; // Changed to double for math/calculations

    public FareLocation(String name, String description, double baseFare) {
        this.name = name;
        this.description = description;
        this.baseFare = baseFare;
    }

    // Update CSV generation to handle the double type
    public String toCsvString() {
        return name + "," + description + "," + baseFare;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getBaseFare() {
        return baseFare;
    }

    // UI Helper: Returns the price as a String with 2 decimals (e.g., "15.00")
    public String getFormattedFare() {
        return String.format("%.2f", baseFare);
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setBaseFare(double baseFare) {
        this.baseFare = baseFare;
    }
}