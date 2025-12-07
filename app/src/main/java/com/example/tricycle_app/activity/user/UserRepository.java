package com.example.tricycle_app.activity.user;

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
                // Check for 8 columns now
                if (parts.length >= 8) {
                    currentUser = new User(
                            parts[0].trim(), // Name
                            parts[1].trim(), // Phone
                            parts[2].trim(), // Email
                            parts[3].trim(), // Address
                            parts[4].trim(), // Gender
                            parts[5].trim(), // BirthDate
                            parts[6].trim(), // Age
                            parts[7].trim()  // MemberSince
                    );
                } else {
                    // Fallback for older formats
                    currentUser = new User("Guest", "0000", "email", "Address", "Gender", "BirthDate", "0", "2024");
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

    public static User getUser() { return currentUser; }

    public static void updateUser(Context context, String name, String phone, String email, String address, String gender, String birthDate, String age) {
        if (currentUser != null) {
            currentUser.setName(name);
            currentUser.setPhone(phone);
            currentUser.setEmail(email);
            currentUser.setAddress(address);
            currentUser.setGender(gender);
            currentUser.setBirthDate(birthDate);
            currentUser.setAge(age);
            saveUser(context);
        }
    }
}