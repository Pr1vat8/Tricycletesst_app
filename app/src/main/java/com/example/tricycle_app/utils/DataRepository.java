package com.example.tricycle_app.utils;

import android.content.Context;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DataRepository {

    // Reads a text file from assets and returns a list of string arrays (rows/columns)
    public static List<String[]> readData(Context context, String fileName) {
        List<String[]> data = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(context.getAssets().open(fileName)));
            String line;
            while ((line = reader.readLine()) != null) {
                // Split by comma, ignoring whitespace around it
                String[] parts = line.split("\\s*,\\s*");
                if (parts.length > 0) {
                    data.add(parts);
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
}