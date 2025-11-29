package com.example.tricycle_app;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

public class UserNavbar {

    public static void setup(final Activity currentActivity) {

        LinearLayout navHome = currentActivity.findViewById(R.id.nav_home);
        LinearLayout navActivity = currentActivity.findViewById(R.id.nav_activity);
        LinearLayout navProfile = currentActivity.findViewById(R.id.nav_profile);
        LinearLayout navHelp = currentActivity.findViewById(R.id.nav_help);

        // Home -> UserMainDashboardActivity
        if (navHome != null) {
            navHome.setOnClickListener(v -> {
                if (!(currentActivity instanceof UserMainDashboardActivity)) {
                    startActivity(currentActivity, UserMainDashboardActivity.class);
                }
            });
        }

        // Activity -> UserRideHistoryActivity
        if (navActivity != null) {
            navActivity.setOnClickListener(v -> {
                if (!(currentActivity instanceof UserRideHistoryActivity)) {
                    startActivity(currentActivity, UserRideHistoryActivity.class);
                }
            });
        }

        // Profile -> UserClientProfileActivity
        if (navProfile != null) {
            navProfile.setOnClickListener(v -> {
                if (!(currentActivity instanceof UserClientProfileActivity)) {
                    startActivity(currentActivity, UserClientProfileActivity.class);
                }
            });
        }

        // Help -> (Optional, add logic if you have a help screen)
        if (navHelp != null) {
            navHelp.setOnClickListener(v -> {
                // Add help intent here later
            });
        }
    }

    private static void startActivity(Activity current, Class<?> target) {
        Intent intent = new Intent(current, target);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        current.startActivity(intent);
        current.overridePendingTransition(0, 0); // Disable animation for tab feel
        current.finish();
    }
}