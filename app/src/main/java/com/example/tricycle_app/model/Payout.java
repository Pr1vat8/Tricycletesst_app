package com.example.tricycle_app.model;

public class Payout {
    private String driverName;
    private String amount;
    private String status; // "Pending" or "Paid"
    private String paymentMethod; // "GCash" or "PayMaya"

    public Payout(String driverName, String amount, String status, String paymentMethod) {
        this.driverName = driverName;
        this.amount = amount;
        this.status = status;
        this.paymentMethod = paymentMethod;
    }

    public String toCsvString() {
        return driverName + "," + amount + "," + status + "," + paymentMethod;
    }

    // Getters and Setters
    public String getDriverName() { return driverName; }
    public String getAmount() { return amount; }
    public String getStatus() { return status; }
    public String getPaymentMethod() { return paymentMethod; }

    public void setStatus(String status) { this.status = status; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
}