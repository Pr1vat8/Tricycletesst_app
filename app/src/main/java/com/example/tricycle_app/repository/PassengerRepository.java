package com.example.tricycle_app.repository;

import android.content.Context;
import com.example.tricycle_app.model.Passenger;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class PassengerRepository {

    private static final String FILE_NAME = "passengers.txt";
    private static final List<Passenger> passengerList = new ArrayList<>();

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
        passengerList.clear();
        try (FileInputStream fis = context.openFileInput(FILE_NAME);
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader reader = new BufferedReader(isr)) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                // Default date if missing
                String dateJoined = "January 01 2024";

                if (parts.length >= 7) {
                    // Format: ID, Name, Phone, Email, Address, Suspended, DateJoined
                    dateJoined = parts[6].trim();
                    passengerList.add(new Passenger(
                            parts[0].trim(), parts[1].trim(), parts[2].trim(),
                            parts[3].trim(), parts[4].trim(),
                            Boolean.parseBoolean(parts[5].trim()),
                            dateJoined));
                } else if (parts.length >= 5) {
                    // Handle legacy data (backward compatibility)
                    boolean suspended = parts.length > 5 && Boolean.parseBoolean(parts[5].trim());
                    passengerList.add(new Passenger(
                            parts[0].trim(), parts[1].trim(), parts[2].trim(),
                            parts[3].trim(), parts[4].trim(),
                            suspended,
                            dateJoined));
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    public static void saveAll(Context context) {
        try (FileOutputStream fos = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE)) {
            for (Passenger p : passengerList) {
                fos.write((p.toCsvString() + "\n").getBytes());
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    public static List<Passenger> getAllPassengers() { return passengerList; }

    // --- RESTORED METHODS BELOW ---

    public static List<Passenger> searchPassengers(String query) {
        List<Passenger> filteredList = new ArrayList<>();
        for (Passenger p : passengerList) {
            if (p.getName().toLowerCase().contains(query.toLowerCase()) ||
                    p.getPhone().contains(query)) {
                filteredList.add(p);
            }
        }
        return filteredList;
    }

    public static Passenger getPassenger(int index) {
        if (index >= 0 && index < passengerList.size()) return passengerList.get(index);
        return null;
    }

    public static void updatePassenger(Context context, int index, String name, String phone, String email, String address) {
        if (index >= 0 && index < passengerList.size()) {
            Passenger p = passengerList.get(index);
            p.setName(name); p.setPhone(phone); p.setEmail(email); p.setAddress(address);
            saveAll(context);
        }
    }

    public static void toggleSuspend(Context context, int index) {
        if (index >= 0 && index < passengerList.size()) {
            Passenger p = passengerList.get(index);
            p.setSuspended(!p.isSuspended());
            saveAll(context);
        }
    }
}