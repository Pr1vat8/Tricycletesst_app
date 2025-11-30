package com.example.tricycle_app;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AdminNavbar {

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
        LinearLayout navDrivers = currentActivity.findViewById(R.id.nav_drivers);
        LinearLayout navRides = currentActivity.findViewById(R.id.nav_rides);
        LinearLayout navFares = currentActivity.findViewById(R.id.nav_fares);
        LinearLayout navPayouts = currentActivity.findViewById(R.id.nav_payouts);

        if (navDashboard != null) {
            navDashboard.setOnClickListener(v -> {
                if (!(currentActivity instanceof AdminDashboardActivity)) {
                    startActivity(currentActivity, AdminDashboardActivity.class);
                }
            });
        }

        if (navDrivers != null) {
            navDrivers.setOnClickListener(v -> {
                if (!(currentActivity instanceof AdminUserActivity)) {
                    startActivity(currentActivity, AdminUserActivity.class);
                }
            });
        }

        if (navRides != null) {
            navRides.setOnClickListener(v -> {
                if (!(currentActivity instanceof AdminRidesHistoryActivity)) {
                    startActivity(currentActivity, AdminRidesHistoryActivity.class);
                }
            });
        }

        if (navFares != null) {
            navFares.setOnClickListener(v -> {
                if (!(currentActivity instanceof AdminFaresActivity)) {
                    startActivity(currentActivity, AdminFaresActivity.class);
                }
            });
        }

        if (navPayouts != null) {
            navPayouts.setOnClickListener(v -> {
                if (!(currentActivity instanceof AdminPayoutActivity)) {
                    startActivity(currentActivity, AdminPayoutActivity.class);
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