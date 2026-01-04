package com.example.tricycle_app.repository;

import android.content.Context;
import com.example.tricycle_app.model.Driver;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DriverRepository {

    private static final String FILE_NAME = "drivers.txt";
    private static final List<Driver> driverList = new ArrayList<>();

    public static void init(Context context) {
        File file = new File(context.getFilesDir(), FILE_NAME);
        if (!file.exists()) {
            copyFileFromAssets(context);
        }
        loadAll(context);
    }

    private static void copyFileFromAssets(Context context) {
        try {
            InputStream in = context.getAssets().open(FILE_NAME);
            FileOutputStream out = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) out.write(buffer, 0, read);
            in.close(); out.flush(); out.close();
        } catch (IOException e) { e.printStackTrace(); }
    }

    public static void loadAll(Context context) {
        driverList.clear();
        try (FileInputStream fis = context.openFileInput(FILE_NAME);
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader reader = new BufferedReader(isr)) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                // Adjusted check for new field (approx 13 fields now)
                if (parts.length >= 7) {
                    String id = parts[0].trim();
                    String name = parts[1].trim();
                    String phone = parts[2].trim();
                    String email = parts[3].trim();
                    String address = parts[4].trim();
                    String plate = parts[5].trim();
                    String route = parts[6].trim(); // NEW FIELD

                    // Optional fields handling
                    String status = (parts.length > 7) ? parts[7].trim() : "Pending";
                    boolean isSuspended = (parts.length > 8) && Boolean.parseBoolean(parts[8].trim());
                    String sDate = (parts.length > 9) ? parts[9].trim() : "";
                    String eDate = (parts.length > 10) ? parts[10].trim() : "";
                    String user = (parts.length > 11) ? parts[11].trim() : "";
                    String pass = (parts.length > 12) ? parts[12].trim() : "";

                    driverList.add(new Driver(
                            id, name, phone, email, address, plate, route,
                            status, isSuspended, sDate, eDate, user, pass
                    ));
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    public static void saveAll(Context context) {
        try (FileOutputStream fos = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE)) {
            for (Driver d : driverList) {
                fos.write((d.toCsvString() + "\n").getBytes());
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    // --- ACCESS METHODS ---
    public static List<Driver> getAllDrivers() {
        return driverList;
    }

    public static Driver getDriverById(String id) {
        for (Driver d : driverList) {
            if (d.getId().equals(id)) return d;
        }
        return null;
    }

    public static List<Driver> getDriversByStatus(String status) {
        List<Driver> filtered = new ArrayList<>();
        for (Driver d : driverList) {
            if (status.equalsIgnoreCase("Pending")) {
                if (d.getStatus().equalsIgnoreCase("Pending") || d.getStatus().equalsIgnoreCase("Rejected")) {
                    filtered.add(d);
                }
            } else if (d.getStatus().equalsIgnoreCase(status)) {
                filtered.add(d);
            }
        }
        return filtered;
    }

    public static List<Driver> searchDrivers(String query, String status) {
        List<Driver> filtered = new ArrayList<>();
        for (Driver d : driverList) {
            boolean matchesStatus;
            if (status.equalsIgnoreCase("Pending")) {
                matchesStatus = d.getStatus().equalsIgnoreCase("Pending") || d.getStatus().equalsIgnoreCase("Rejected");
            } else {
                matchesStatus = d.getStatus().equalsIgnoreCase(status);
            }

            if (matchesStatus &&
                    (d.getName().toLowerCase().contains(query.toLowerCase()) ||
                            d.getPlateNumber().toLowerCase().contains(query.toLowerCase()))) {
                filtered.add(d);
            }
        }
        return filtered;
    }

    // --- ACTION METHODS ---
    public static void addDriver(Context context, Driver driver) {
        driverList.add(driver);
        saveAll(context);
    }

    // Updated to include ROUTE
    public static void updateDriver(Context context, String id, String name, String phone, String email, String address, String plate, String route) {
        Driver d = getDriverById(id);
        if (d != null) {
            d.setName(name); d.setPhone(phone); d.setEmail(email);
            d.setAddress(address); d.setPlateNumber(plate);
            d.setRoute(route);
            saveAll(context);
        }
    }

    public static void approveDriver(Context context, String driverId) {
        Driver d = getDriverById(driverId);
        if (d != null) { d.setStatus("Verified"); saveAll(context); }
    }

    public static void rejectDriver(Context context, String driverId) {
        Driver d = getDriverById(driverId);
        if (d != null) { d.setStatus("Rejected"); saveAll(context); }
    }

    public static Driver login(String username, String password) {
        for (Driver d : driverList) {
            if (d.getUsername() != null && d.getUsername().equals(username) &&
                    d.getPassword() != null && d.getPassword().equals(password)) {
                return d;
            }
        }
        return null;
    }

    public static void suspendDriver(Context context, String driverId, String startDate, String endDate) {
        Driver d = getDriverById(driverId);
        if (d != null) {
            d.setSuspended(true);
            d.setSuspendStartDate(startDate);
            d.setSuspendEndDate(endDate);
            saveAll(context);
        }
    }

    public static void unsuspendDriver(Context context, String driverId) {
        Driver d = getDriverById(driverId);
        if (d != null) {
            d.setSuspended(false);
            d.setSuspendStartDate("");
            d.setSuspendEndDate("");
            saveAll(context);
        }
    }
}