package com.example.tricycle_app;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

public class DriverNavbar {

    public static void setup(final Activity currentActivity) {

        // Find buttons
        LinearLayout navDashboard = currentActivity.findViewById(R.id.nav_dashboard);
        LinearLayout navActivity = currentActivity.findViewById(R.id.nav_activity); // "My Rides"
        LinearLayout navEarnings = currentActivity.findViewById(R.id.nav_earnings);
        LinearLayout navProfile = currentActivity.findViewById(R.id.nav_profile);

        // 1. Dashboard -> DriverDashboardActivity
        if (navDashboard != null) {
            navDashboard.setOnClickListener(v -> {
                if (!(currentActivity instanceof DriverDashboardActivity)) {
                    startActivity(currentActivity, DriverDashboardActivity.class);
                }
            });
        }

        // 2. Activity (My Rides) -> DriverDashHistoryActivity
        if (navActivity != null) {
            navActivity.setOnClickListener(v -> {
                if (!(currentActivity instanceof DriverDashHistoryActivity)) {
                    startActivity(currentActivity, DriverDashHistoryActivity.class);
                }
            });
        }

        // 3. Earnings -> DriverEarningActivity
        if (navEarnings != null) {
            navEarnings.setOnClickListener(v -> {
                if (!(currentActivity instanceof DriverEarningActivity)) {
                    startActivity(currentActivity, DriverEarningActivity.class);
                }
            });
        }

        // 4. Profile -> DriverProfileActivity
        if (navProfile != null) {
            navProfile.setOnClickListener(v -> {
                if (!(currentActivity instanceof DriverProfileActivity)) {
                    startActivity(currentActivity, DriverProfileActivity.class);
                }
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