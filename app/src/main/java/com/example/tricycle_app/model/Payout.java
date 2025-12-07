package com.example.tricycle_app.model;

public class Payout {
    private String driverName;
    private String amount;
    private String status; // "Pending" or "Paid"

    public Payout(String driverName, String amount, String status) {
        this.driverName = driverName;
        this.amount = amount;
        this.status = status;
    }

    public String toCsvString() {
        return driverName + "," + amount + "," + status;
    }

    public String getDriverName() { return driverName; }
    public String getAmount() { return amount; }
    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }
}