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
                if (parts.length >= 14) {
                    // New Format with Gender, DOB, Age
                    passengerList.add(new Passenger(
                            parts[0].trim(), // ID
                            parts[1].trim(), // Name
                            parts[2].trim(), // Phone
                            parts[3].trim(), // Email
                            parts[4].trim(), // Address
                            parts[5].trim(), // Gender
                            parts[6].trim(), // BirthDate
                            parts[7].trim(), // Age
                            Boolean.parseBoolean(parts[8].trim()), // Suspended
                            parts[9].trim(), // DateJoined
                            parts[10].trim(), // Start
                            parts[11].trim(), // End
                            parts[12].trim(), // User
                            parts[13].trim()  // Pass
                    ));
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

    public static Passenger login(String username, String password) {
        for (Passenger p : passengerList) {
            if (p.getUsername().equals(username) && p.getPassword().equals(password)) {
                return p;
            }
        }
        return null;
    }

    public static List<Passenger> getAllPassengers() { return passengerList; }

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

    public static void suspendPassenger(Context context, int index, String startDate, String endDate) {
        if (index >= 0 && index < passengerList.size()) {
            Passenger p = passengerList.get(index);
            p.setSuspended(true);
            p.setSuspendStartDate(startDate);
            p.setSuspendEndDate(endDate);
            saveAll(context);
        }
    }

    public static void unsuspendPassenger(Context context, int index) {
        if (index >= 0 && index < passengerList.size()) {
            Passenger p = passengerList.get(index);
            p.setSuspended(false);
            p.setSuspendStartDate("");
            p.setSuspendEndDate("");
            saveAll(context);
        }
    }
}