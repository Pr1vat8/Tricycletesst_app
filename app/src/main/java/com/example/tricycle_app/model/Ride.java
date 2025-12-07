package com.example.tricycle_app.model;

public class Ride {
    private String rideId;
    private String passenger;
    private String driver;
    private String fromLocation; // New
    private String toLocation;   // New
    private String date;
    private String time;
    private String status;
    private String baseFare;
    private String distanceFare;
    private String totalFare;

    public Ride(String rideId, String passenger, String driver, String fromLocation, String toLocation, String date, String time, String status, String baseFare, String distanceFare, String totalFare) {
        this.rideId = rideId;
        this.passenger = passenger;
        this.driver = driver;
        this.fromLocation = fromLocation;
        this.toLocation = toLocation;
        this.date = date;
        this.time = time;
        this.status = status;
        this.baseFare = baseFare;
        this.distanceFare = distanceFare;
        this.totalFare = totalFare;
    }

    public String toCsvString() {
        return rideId + "," + passenger + "," + driver + "," + fromLocation + "," + toLocation + "," + date + "," + time + "," + status + "," + baseFare + "," + distanceFare + "," + totalFare;
    }

    // Getters
    public String getRideId() { return rideId; }
    public String getPassenger() { return passenger; }
    public String getDriver() { return driver; }
    public String getFromLocation() { return fromLocation; }
    public String getToLocation() { return toLocation; }
    public String getDate() { return date; }
    public String getTime() { return time; }
    public String getStatus() { return status; }
    public String getBaseFare() { return baseFare; }
    public String getDistanceFare() { return distanceFare; }
    public String getTotalFare() { return totalFare; }
}