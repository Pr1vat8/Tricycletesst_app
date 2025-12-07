package com.example.tricycle_app.repository;

import android.content.Context;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DriverEarningRepository {
    private static final String FILE_NAME = "driver_earnings.txt";

    public static class Earning {
        public String date;
        public String amount;
        public Earning(String d, String a) { date=d; amount=a; }
    }

    private static final List<Earning> allEarnings = new ArrayList<>();

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
        } catch (Exception e) { e.printStackTrace(); }
    }

    public static void loadAll(Context context) {
        allEarnings.clear();
        try (FileInputStream fis = context.openFileInput(FILE_NAME);
             BufferedReader reader = new BufferedReader(new InputStreamReader(fis))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] p = line.split(",");
                if (p.length >= 2) allEarnings.add(new Earning(p[0], p[1]));
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    public static List<Earning> getEarningsByFilter(String filter) {
        // Mock logic: Day=1 item, Week=5 items, Month=All items
        if(filter.equals("Day")) return allEarnings.subList(0, Math.min(allEarnings.size(), 1));
        if(filter.equals("Week")) return allEarnings.subList(0, Math.min(allEarnings.size(), 5));
        return allEarnings;
    }

    public static int getTotal(List<Earning> list) {
        int total = 0;
        for(Earning e : list) total += Integer.parseInt(e.amount.trim());
        return total;
    }
}