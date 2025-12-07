package com.example.tricycle_app.utils;

public class UserTripManager {
    private static UserTripManager instance;

    private String fromLocation;
    private String toLocation;
    private String driverName;
    private String price;
    private String distance; // Optional
    private String duration; // Optional

    private UserTripManager() {}

    public static synchronized UserTripManager getInstance() {
        if (instance == null) instance = new UserTripManager();
        return instance;
    }

    public void setLocations(String from, String to) {
        this.fromLocation = from;
        this.toLocation = to;
    }

    public void setDriver(String name) {
        this.driverName = name;
    }

    public void setTripDetails(String price, String distance, String duration) {
        this.price = price;
        this.distance = distance;
        this.duration = duration;
    }

    public String getFromLocation() { return fromLocation; }
    public String getToLocation() { return toLocation; }
    public String getDriverName() { return driverName; }
    public String getPrice() { return price; }
    public String getDistance() { return distance; }
    public String getDuration() { return duration; }

    public void clear() {
        fromLocation = null;
        toLocation = null;
        driverName = null;
        price = null;
    }
}