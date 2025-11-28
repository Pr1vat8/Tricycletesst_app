package com.example.tricycle_app;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

public class AdminNavbar {

    public static void setup(final Activity currentActivity) {

        // 1. Find the buttons from the included layout
        LinearLayout navDashboard = currentActivity.findViewById(R.id.nav_dashboard);
        LinearLayout navDrivers = currentActivity.findViewById(R.id.nav_drivers);
        LinearLayout navRides = currentActivity.findViewById(R.id.nav_rides);
        LinearLayout navFares = currentActivity.findViewById(R.id.nav_fares);
        LinearLayout navPayouts = currentActivity.findViewById(R.id.nav_payouts);

        // 2. Set Click Listeners

        // DASHBOARD BUTTON
        navDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(currentActivity instanceof AdminDashboardActivity)) {
                    Intent intent = new Intent(currentActivity, AdminDashboardActivity.class);
                    // Clear back stack to prevent "Back" button loops
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    currentActivity.startActivity(intent);
                    currentActivity.overridePendingTransition(0, 0); // No animation
                }
            }
        });

        // DRIVERS BUTTON
        navDrivers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(currentActivity instanceof AdminUserActivity)) {
                    Intent intent = new Intent(currentActivity, AdminUserActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    currentActivity.startActivity(intent);
                    currentActivity.overridePendingTransition(0, 0);
                }
            }
        });

        // RIDES BUTTON
        navRides.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(currentActivity instanceof AdminRidesHistoryActivity)) {
                    Intent intent = new Intent(currentActivity, AdminRidesHistoryActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    currentActivity.startActivity(intent);
                    currentActivity.overridePendingTransition(0, 0);
                }
            }
        });

        // FARES BUTTON
        navFares.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(currentActivity instanceof AdminFaresActivity)) {
                    Intent intent = new Intent(currentActivity, AdminFaresActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    currentActivity.startActivity(intent);
                    currentActivity.overridePendingTransition(0, 0);
                }
            }
        });

        // PAYOUTS BUTTON
        navPayouts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(currentActivity instanceof AdminPayoutActivity)) {
                    Intent intent = new Intent(currentActivity, AdminPayoutActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    currentActivity.startActivity(intent);
                    currentActivity.overridePendingTransition(0, 0);
                }
            }
        });
    }
}