package com.example.tricycle_app.model;

import java.io.Serializable;

public class PaymentMethod implements Serializable {
    private String id;
    private String provider; // e.g., "GCash", "PayMaya", "Cash", "Wallet"
    private String phoneNumber; // or Account Number
    private boolean isDefault;

    public PaymentMethod(String id, String provider, String phoneNumber, boolean isDefault) {
        this.id = id;
        this.provider = provider;
        this.phoneNumber = phoneNumber;
        this.isDefault = isDefault;
    }

    public String getId() { return id; }
    public String getProvider() { return provider; }
    public String getPhoneNumber() { return phoneNumber; }
    public boolean isDefault() { return isDefault; }

    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    @Override
    public String toString() {
        return id + "," + provider + "," + phoneNumber + "," + isDefault;
    }

    public static PaymentMethod fromString(String line) {
        String[] parts = line.split(",");
        if (parts.length >= 4) {
            return new PaymentMethod(parts[0], parts[1], parts[2], Boolean.parseBoolean(parts[3]));
        }
        return null;
    }
}