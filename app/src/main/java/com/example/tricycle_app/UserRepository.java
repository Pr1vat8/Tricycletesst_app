package com.example.tricycle_app;

import android.content.Context;
import com.example.tricycle_app.model.User;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class UserRepository {

    private static final String FILE_NAME = "user_profile.txt";
    private static User currentUser;

    public static void init(Context context) {
        File file = new File(context.getFilesDir(), FILE_NAME);
        if (!file.exists()) {
            copyFileFromAssets(context);
        }
        loadUser(context);
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

    public static void loadUser(Context context) {
        try (FileInputStream fis = context.openFileInput(FILE_NAME);
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader reader = new BufferedReader(isr)) {

            String line = reader.readLine();
            if (line != null) {
                String[] parts = line.split(",");
                if (parts.length >= 11) {
                    // Load with suspension details
                    currentUser = new User(
                            parts[0].trim(), parts[1].trim(), parts[2].trim(),
                            parts[3].trim(), parts[4].trim(), parts[5].trim(),
                            parts[6].trim(), parts[7].trim(),
                            Boolean.parseBoolean(parts[8].trim()), // isSuspended
                            parts[9].trim(), // Start
                            parts[10].trim() // End
                    );
                } else if (parts.length >= 8) {
                    // Legacy load
                    currentUser = new User(
                            parts[0].trim(), parts[1].trim(), parts[2].trim(),
                            parts[3].trim(), parts[4].trim(), parts[5].trim(),
                            parts[6].trim(), parts[7].trim()
                    );
                }
            }
        } catch (Exception e) { e.printStackTrace(); }

        if (currentUser == null) {
            currentUser = new User("Guest User", "0000", "guest@email.com", "City, Province", "Male", "01/01/2000", "25", "2024");
        }
    }

    public static void saveUser(Context context) {
        if (currentUser == null) return;
        try (FileOutputStream fos = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE)) {
            String line = currentUser.toCsvString();
            fos.write(line.getBytes());
        } catch (Exception e) { e.printStackTrace(); }
    }

    public static void setCurrentUser(Context context, User user) {
        currentUser = user;
        saveUser(context);
    }

    public static User getUser() { return currentUser; }
}