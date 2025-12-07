package com.example.tricycle_app.repository;

import android.content.Context;

import com.example.tricycle_app.model.Ride;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class UserRideRepository {

    private static final String FILE_NAME = "user_rides.txt";
    private static final List<Ride> userRideList = new ArrayList<>();

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
        userRideList.clear();
        try (FileInputStream fis = context.openFileInput(FILE_NAME);
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader reader = new BufferedReader(isr)) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                // Expecting 11 columns matching Ride.java
                if (parts.length >= 11) {
                    userRideList.add(new Ride(
                            parts[0].trim(), parts[1].trim(), parts[2].trim(),
                            parts[3].trim(), parts[4].trim(), parts[5].trim(),
                            parts[6].trim(), parts[7].trim(), parts[8].trim(),
                            parts[9].trim(), parts[10].trim()));
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    public static List<Ride> getAllRides() { return userRideList; }

    // --- Added for Details Activity ---
    public static Ride getRideById(String id) {
        for (Ride r : userRideList) {
            if (r.getRideId().equals(id)) return r;
        }
        return null;
    }

    // --- Added for Saving New Rides ---
    public static void addRide(Context context, Ride ride) {
        userRideList.add(0, ride); // Add to top of the list
        try (FileOutputStream fos = context.openFileOutput(FILE_NAME, Context.MODE_APPEND)) {
            String line = ride.toCsvString() + "\n";
            fos.write(line.getBytes());
        } catch (Exception e) { e.printStackTrace(); }
    }
}