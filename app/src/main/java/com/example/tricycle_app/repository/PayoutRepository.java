package com.example.tricycle_app.repository;

import android.content.Context;
import com.example.tricycle_app.model.Payout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class PayoutRepository {

    private static final String FILE_NAME = "payouts.txt";
    private static final List<Payout> payoutList = new ArrayList<>();

    public static void init(Context context) {
        File file = new File(context.getFilesDir(), FILE_NAME);
        if (!file.exists()) {
            createDefaultData(context);
        } else {
            loadAll(context);
        }
    }

    private static void createDefaultData(Context context) {
        payoutList.clear();
        payoutList.add(new Payout("Ethan Carter", "150", "Pending", "GCash"));
        payoutList.add(new Payout("Olivia Bennett", "200", "Pending", "PayMaya"));
        payoutList.add(new Payout("Noah Thompson", "180", "Pending", "GCash"));
        payoutList.add(new Payout("Ava Martinez", "220", "Pending", "PayMaya"));
        payoutList.add(new Payout("Liam Harris", "170", "Pending", "GCash"));
        payoutList.add(new Payout("Sophia Wilson", "300", "Paid", "GCash"));

        saveAll(context);
    }

    public static void loadAll(Context context) {
        payoutList.clear();
        try (FileInputStream fis = context.openFileInput(FILE_NAME);
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader reader = new BufferedReader(isr)) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    payoutList.add(new Payout(parts[0].trim(), parts[1].trim(), parts[2].trim(), parts[3].trim()));
                } else if (parts.length == 3) {
                    // Legacy support: Default to GCash if missing
                    payoutList.add(new Payout(parts[0].trim(), parts[1].trim(), parts[2].trim(), "GCash"));
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    public static void saveAll(Context context) {
        try (FileOutputStream fos = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE)) {
            for (Payout p : payoutList) {
                fos.write((p.toCsvString() + "\n").getBytes());
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    public static List<Payout> getPayoutsByStatus(String status) {
        List<Payout> filtered = new ArrayList<>();
        for (Payout p : payoutList) {
            if (p.getStatus().equalsIgnoreCase(status)) {
                filtered.add(p);
            }
        }
        return filtered;
    }

    public static List<Payout> getAllPayouts() {
        return payoutList;
    }

    public static void markAsPaid(Context context, Payout payout) {
        payout.setStatus("Paid");
        saveAll(context);
    }

    // New Method for Reverting Status
    public static void markAsPending(Context context, Payout payout) {
        payout.setStatus("Pending");
        saveAll(context);
    }
}