package com.example.tricycle_app;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class UserNavbar {

    public static void setup(final Activity currentActivity) {
        // 1. Handle Window Insets (Dynamic Bottom Padding)
        LinearLayout navbarContainer = currentActivity.findViewById(R.id.navbar_container);
        if (navbarContainer != null) {
            ViewCompat.setOnApplyWindowInsetsListener(navbarContainer, (v, windowInsets) -> {
                Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
                // Apply the bottom inset as padding
                v.setPadding(0, 0, 0, insets.bottom);
                return windowInsets;
            });
        }

        // 2. Setup Buttons
        LinearLayout navHome = currentActivity.findViewById(R.id.nav_home);
        LinearLayout navActivity = currentActivity.findViewById(R.id.nav_activity);
        LinearLayout navProfile = currentActivity.findViewById(R.id.nav_profile);
        LinearLayout navHelp = currentActivity.findViewById(R.id.nav_help);

        if (navHome != null) {
            navHome.setOnClickListener(v -> {
                if (!(currentActivity instanceof UserMainDashboardActivity)) {
                    startActivity(currentActivity, UserMainDashboardActivity.class);
                }
            });
        }

        if (navActivity != null) {
            navActivity.setOnClickListener(v -> {
                if (!(currentActivity instanceof UserRideHistoryActivity)) {
                    startActivity(currentActivity, UserRideHistoryActivity.class);
                }
            });
        }

        if (navProfile != null) {
            navProfile.setOnClickListener(v -> {
                if (!(currentActivity instanceof UserClientProfileActivity)) {
                    startActivity(currentActivity, UserClientProfileActivity.class);
                }
            });
        }

        if (navHelp != null) {
            navHelp.setOnClickListener(v -> {
                // Help logic
            });
        }
    }

    private static void startActivity(Activity current, Class<?> target) {
        Intent intent = new Intent(current, target);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        current.startActivity(intent);
        current.overridePendingTransition(0, 0);
        current.finish();
    }
}