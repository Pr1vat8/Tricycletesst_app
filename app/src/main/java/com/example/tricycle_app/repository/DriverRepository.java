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
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadAll(Context context) {
        driverList.clear();
        try (FileInputStream fis = context.openFileInput(FILE_NAME);
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader reader = new BufferedReader(isr)) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 8) {
                    driverList.add(new Driver(
                            parts[0].trim(), parts[1].trim(), parts[2].trim(),
                            parts[3].trim(), parts[4].trim(), parts[5].trim(),
                            parts[6].trim(), Boolean.parseBoolean(parts[7].trim())
                    ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveAll(Context context) {
        try (FileOutputStream fos = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE)) {
            for (Driver d : driverList) {
                String line = d.toCsvString() + "\n";
                fos.write(line.getBytes());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Driver getDriverById(String id) {
        for (Driver d : driverList) {
            if (d.getId().equals(id)) {
                return d;
            }
        }
        return null;
    }

    // --- ADDED MISSING METHOD ---
    public static List<Driver> getAllDrivers() {
        return driverList;
    }

    // --- MODIFIED METHOD ---
    public static List<Driver> getDriversByStatus(String status) {
        List<Driver> filtered = new ArrayList<>();
        for (Driver d : driverList) {
            if (status.equalsIgnoreCase("Pending")) {
                // If asking for Pending, show both PENDING and REJECTED
                if (d.getStatus().equalsIgnoreCase("Pending") || d.getStatus().equalsIgnoreCase("Rejected")) {
                    filtered.add(d);
                }
            } else {
                // Otherwise (Verified), strict matching
                if (d.getStatus().equalsIgnoreCase(status)) {
                    filtered.add(d);
                }
            }
        }
        return filtered;
    }

    public static List<Driver> searchDrivers(String query, String currentTabStatus) {
        List<Driver> result = new ArrayList<>();
        // Get the filtered list for the current tab first
        List<Driver> tabDrivers = getDriversByStatus(currentTabStatus);

        for (Driver d : tabDrivers) {
            if (d.getName().toLowerCase().contains(query.toLowerCase()) || d.getId().contains(query)) {
                result.add(d);
            }
        }
        return result;
    }

    // --- Actions ---

    public static void updateDriver(Context context, String id, String name, String phone, String email, String address, String plate) {
        Driver d = getDriverById(id);
        if (d != null) {
            d.setName(name); d.setPhone(phone); d.setEmail(email); d.setAddress(address); d.setPlateNumber(plate);
            saveAll(context);
        }
    }

    public static void approveDriver(Context context, String id) {
        Driver d = getDriverById(id);
        if (d != null) {
            d.setStatus("Verified");
            d.setSuspended(false);
            saveAll(context);
        }
    }

    public static void rejectDriver(Context context, String id) {
        Driver d = getDriverById(id);
        if (d != null) {
            d.setStatus("Rejected"); // Stays in list but marked Rejected
            saveAll(context);
        }
    }

    public static void toggleSuspend(Context context, String id) {
        Driver d = getDriverById(id);
        if (d != null) {
            d.setSuspended(!d.isSuspended());
            saveAll(context);
        }
    }
}