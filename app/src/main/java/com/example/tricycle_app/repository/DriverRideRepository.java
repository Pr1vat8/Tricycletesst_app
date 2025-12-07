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

public class DriverRideRepository {
    private static final String FILE_NAME = "driver_rides.txt";
    private static final List<Ride> allRides = new ArrayList<>();

    public static void init(Context context) {
        File file = new File(context.getFilesDir(), FILE_NAME);
        if (!file.exists()) copyFileFromAssets(context);
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
        allRides.clear();
        try (FileInputStream fis = context.openFileInput(FILE_NAME);
             BufferedReader reader = new BufferedReader(new InputStreamReader(fis))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] p = line.split(",");
                if (p.length >= 7) {
                    // ID, Date, Time, From, To, Price, Status
                    allRides.add(new Ride(p[0], "N/A", "Self", p[3], p[4], p[1], p[2], p[6], p[5], "0", p[5]));
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    public static List<Ride> getRidesByTab(String tab) {
        List<Ride> filtered = new ArrayList<>();
        for (Ride r : allRides) {
            boolean isUpcoming = r.getStatus().equalsIgnoreCase("Upcoming");
            if (tab.equalsIgnoreCase("Upcoming") && isUpcoming) filtered.add(r);
            else if (tab.equalsIgnoreCase("Past") && !isUpcoming) filtered.add(r);
        }
        return filtered;
    }

    // --- NEW METHOD ---
    public static Ride getRideById(String id) {
        for (Ride r : allRides) {
            if (r.getRideId().equalsIgnoreCase(id)) return r;
        }
        return null;
    }
}