package com.example.tricycle_app.repository;

import android.content.Context;
import com.example.tricycle_app.model.FareLocation;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

public class FareRepository {

    private static final String FILE_NAME = "fares.txt";
    private static final List<FareLocation> fareList = new ArrayList<>();

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
        fareList.clear();
        try (FileInputStream fis = context.openFileInput(FILE_NAME);
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader reader = new BufferedReader(isr)) {

            String line;
            while ((line = reader.readLine()) != null) {
                // Split by comma: "Centamina,123 Main St,15.00"
                String[] parts = line.split(",");

                // Ensure we have all 3 parts: Name, Description, Price
                if (parts.length == 3) {
                    String name = parts[0].trim();
                    String description = parts[1].trim();

                    double price = 0.0;
                    try {
                        price = Double.parseDouble(parts[2].trim());
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }

                    fareList.add(new FareLocation(name, description, price));
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    public static void saveAll(Context context) {
        try (FileOutputStream fos = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE)) {
            for (FareLocation f : fareList) {
                // Save format: Name,Description,Price
                String line = f.getName() + "," + f.getDescription() + "," + f.getBaseFare() + "\n";
                fos.write(line.getBytes());
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    public static List<FareLocation> getAllFares() {
        return fareList;
    }

    public static FareLocation getFareByName(String name) {
        for (FareLocation f : fareList) {
            if (f.getName().equalsIgnoreCase(name)) return f;
        }
        return null;
    }

    // --- ADD FUNCTIONALITY ---
    // Updated to accept Description and Price as double
    public static void addFare(Context context, String name, String description, double price) {
        fareList.add(new FareLocation(name, description, price));
        saveAll(context);
    }

    // Overload for when adding from Text Inputs (String price)
    public static void addFare(Context context, String name, String description, String priceString) {
        double price = 0.0;
        try {
            price = Double.parseDouble(priceString);
        } catch (NumberFormatException e) { e.printStackTrace(); }
        addFare(context, name, description, price);
    }

    // Updated to handle double price
    public static void updateFare(Context context, String name, double newPrice) {
        FareLocation fare = getFareByName(name);
        if (fare != null) {
            fare.setBaseFare(newPrice);
            saveAll(context);
        }
    }

    // Overload for String price input
    public static void updateFare(Context context, String name, String newPriceString) {
        try {
            double newPrice = Double.parseDouble(newPriceString);
            updateFare(context, name, newPrice);
        } catch (NumberFormatException e) { e.printStackTrace(); }
    }

    public static boolean checkFareExists(String name) {
        return getFareByName(name) != null;
    }
}