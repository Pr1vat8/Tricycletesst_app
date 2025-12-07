package com.example.tricycle_app.utils;

import android.app.Activity;
import android.content.Intent;
import android.widget.LinearLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tricycle_app.activity.user.DriverDashHistoryActivity;
import com.example.tricycle_app.activity.user.DriverDashboardActivity;
import com.example.tricycle_app.activity.user.DriverEarningActivity;
import com.example.tricycle_app.activity.user.DriverProfileActivity;
import com.example.tricycle_app.R;

public class DriverNavbar {

    public static void setup(final Activity currentActivity) {

        // 1. Handle Insets
        LinearLayout navbarContainer = currentActivity.findViewById(R.id.navbar_container);
        if (navbarContainer != null) {
            ViewCompat.setOnApplyWindowInsetsListener(navbarContainer, (v, windowInsets) -> {
                Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(0, 0, 0, insets.bottom);
                return windowInsets;
            });
        }

        // 2. Buttons
        LinearLayout navDashboard = currentActivity.findViewById(R.id.nav_dashboard);
        LinearLayout navActivity = currentActivity.findViewById(R.id.nav_activity);
        LinearLayout navEarnings = currentActivity.findViewById(R.id.nav_earnings);
        LinearLayout navProfile = currentActivity.findViewById(R.id.nav_profile);

        if (navDashboard != null) {
            navDashboard.setOnClickListener(v -> {
                if (!(currentActivity instanceof DriverDashboardActivity)) {
                    startActivity(currentActivity, DriverDashboardActivity.class);
                }
            });
        }

        if (navActivity != null) {
            navActivity.setOnClickListener(v -> {
                if (!(currentActivity instanceof DriverDashHistoryActivity)) {
                    startActivity(currentActivity, DriverDashHistoryActivity.class);
                }
            });
        }

        if (navEarnings != null) {
            navEarnings.setOnClickListener(v -> {
                if (!(currentActivity instanceof DriverEarningActivity)) {
                    startActivity(currentActivity, DriverEarningActivity.class);
                }
            });
        }

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