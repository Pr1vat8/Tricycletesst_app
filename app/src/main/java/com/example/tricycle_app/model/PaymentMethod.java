package com.example.tricycle_app.model;

import java.io.Serializable;

public class PaymentMethod implements Serializable {
    private String id;
    private String provider; // "GCash", "Maya", "Card"
    private String phoneNumber; // Phone or Card Number
    private String cardType;    // "Visa", "Mastercard", or "N/A"
    private boolean isDefault;

    public PaymentMethod(String id, String provider, String phoneNumber, String cardType, boolean isDefault) {
        this.id = id;
        this.provider = provider;
        this.phoneNumber = phoneNumber;
        this.cardType = cardType;
        this.isDefault = isDefault;
    }

    public String getId() { return id; }
    public String getProvider() { return provider; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getCardType() { return cardType; }
    public boolean isDefault() { return isDefault; }

    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    @Override
    public String toString() {
        // Appended cardType
        return id + "," + provider + "," + phoneNumber + "," + cardType + "," + isDefault;
    }

    public static PaymentMethod fromString(String line) {
        String[] parts = line.split(",");
        if (parts.length >= 5) {
            return new PaymentMethod(parts[0], parts[1], parts[2], parts[3], Boolean.parseBoolean(parts[4]));
        }
        // Fallback for old data without cardType
        if (parts.length == 4) {
            return new PaymentMethod(parts[0], parts[1], parts[2], "N/A", Boolean.parseBoolean(parts[3]));
        }
        return null;
    }
}