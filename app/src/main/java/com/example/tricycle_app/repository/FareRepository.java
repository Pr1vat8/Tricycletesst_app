package com.example.tricycle_app.repository;

import android.content.Context;

import com.example.tricycle_app.model.FareLocation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FareRepository {

    private static final String FILE_NAME = "fares.txt";
    private static final List<FareLocation> fareList = new ArrayList<>();

    public static void init(Context context) {
        File file = new File(context.getFilesDir(), FILE_NAME);
        if (!file.exists()) {
            createDefaultData(context);
        } else {
            loadAll(context);
        }
    }

    private static void createDefaultData(Context context) {
        fareList.clear();
        // Default Data with 15.00 Base Fare
        fareList.add(new FareLocation("Centamina", "123 Main St", "15.00"));
        fareList.add(new FareLocation("Bus Terminal", "456 Oak Ave", "15.00"));
        fareList.add(new FareLocation("Merkado", "789 Pine Ln", "15.00"));
        fareList.add(new FareLocation("Purok 1", "Zone 1", "15.00"));
        fareList.add(new FareLocation("Lumbo", "Zone 2", "15.00"));
        fareList.add(new FareLocation("Hangkol", "Zone 3", "15.00"));
        fareList.add(new FareLocation("Plaza", "Near the park", "15.00"));
        fareList.add(new FareLocation("Central School", "Near the mall", "15.00"));
        fareList.add(new FareLocation("Old Terminal", "Near the hospital", "15.00"));

        saveAll(context);
    }

    public static void loadAll(Context context) {
        fareList.clear();
        try (FileInputStream fis = context.openFileInput(FILE_NAME);
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader reader = new BufferedReader(isr)) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    fareList.add(new FareLocation(parts[0].trim(), parts[1].trim(), parts[2].trim()));
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    public static void saveAll(Context context) {
        try (FileOutputStream fos = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE)) {
            for (FareLocation f : fareList) {
                fos.write((f.toCsvString() + "\n").getBytes());
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    public static List<FareLocation> getAllFares() { return fareList; }

    public static FareLocation getFareByName(String name) {
        for (FareLocation f : fareList) {
            if (f.getName().equalsIgnoreCase(name)) return f;
        }
        return null;
    }

    public static void updateFare(Context context, String name, String newFare) {
        FareLocation f = getFareByName(name);
        if (f != null) {
            f.setBaseFare(newFare);
            saveAll(context);
        }
    }
}