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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RideRepository {

    private static final String FILE_NAME = "rides.txt";
    private static final List<Ride> rideList = new ArrayList<>();

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
        rideList.clear();
        try (FileInputStream fis = context.openFileInput(FILE_NAME);
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader reader = new BufferedReader(isr)) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 11) {
                    rideList.add(new Ride(
                            parts[0].trim(), parts[1].trim(), parts[2].trim(),
                            parts[3].trim(), parts[4].trim(), parts[5].trim(), parts[6].trim(),
                            parts[7].trim(), parts[8].trim(), parts[9].trim(), parts[10].trim()));
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    public static List<Ride> getAllRides() { return rideList; }

    public static Ride getRideById(String id) {
        for (Ride r : rideList) {
            if (r.getRideId().equals(id)) return r;
        }
        return null;
    }

    public static List<Ride> searchRides(String query) {
        List<Ride> result = new ArrayList<>();
        for (Ride r : rideList) {
            if (r.getRideId().contains(query) || r.getDate().toLowerCase().contains(query.toLowerCase())) {
                result.add(r);
            }
        }
        return result;
    }

    // --- NEW: Sorting Logic ---
    public static List<Ride> getSortedRides(boolean newestFirst) {
        List<Ride> sortedList = new ArrayList<>(rideList);
        Collections.sort(sortedList, new Comparator<Ride>() {
            SimpleDateFormat format = new SimpleDateFormat("MMMM dd yyyy hh:mm a", Locale.US);
            @Override
            public int compare(Ride r1, Ride r2) {
                try {
                    // Combine date and time for comparison
                    Date d1 = format.parse(r1.getDate() + " " + r1.getTime());
                    Date d2 = format.parse(r2.getDate() + " " + r2.getTime());
                    if (newestFirst) {
                        return d2.compareTo(d1); // Descending
                    } else {
                        return d1.compareTo(d2); // Ascending
                    }
                } catch (Exception e) {
                    return 0;
                }
            }
        });
        return sortedList;
    }
}