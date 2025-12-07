package com.example.tricycle_app;

import android.content.Context;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class SavedPlaceRepository {

    private static final String FILE_NAME = "saved_places.txt";
    private static final List<SavedPlace> savedPlaces = new ArrayList<>();

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
        savedPlaces.clear();
        try (FileInputStream fis = context.openFileInput(FILE_NAME);
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader reader = new BufferedReader(isr)) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 2); // Split into Name and Address
                if (parts.length >= 2) {
                    savedPlaces.add(new SavedPlace(parts[0].trim(), parts[1].trim()));
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    public static List<SavedPlace> getAllSavedPlaces() { return savedPlaces; }

    // --- SAVE FUNCTION ---
    public static void addSavedPlace(Context context, SavedPlace place) {
        savedPlaces.add(place);
        try (FileOutputStream fos = context.openFileOutput(FILE_NAME, Context.MODE_APPEND)) {
            String line = place.getName() + "," + place.getAddress() + "\n";
            fos.write(line.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}