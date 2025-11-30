package com.example.tricycle_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class AdminUserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adminusers);

        // 1. Initialize Navigation Bar
        AdminNavbar.setup(this);

        // 2. Logic for "Passengers" button -> Open AdminPassengerActivity
        TextView btnPassengers = findViewById(R.id.btnPassengers);
        if (btnPassengers != null) {
            btnPassengers.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(AdminUserActivity.this, AdminPassengerActivity.class);
                    startActivity(intent);
                }
            });
        }

        // 3. Logic for Driver Rows -> Open AdminDriverVerificationActivity
        // We create a generic listener for all driver rows
        View.OnClickListener driverClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminUserActivity.this, AdmindriverdetailActivity.class);
                startActivity(intent);
            }
        };

        // Link the listener to the 5 driver rows we added IDs to
        LinearLayout driver1 = findViewById(R.id.driver_1);
        LinearLayout driver2 = findViewById(R.id.driver_2);
        LinearLayout driver3 = findViewById(R.id.driver_3);
        LinearLayout driver4 = findViewById(R.id.driver_4);
        LinearLayout driver5 = findViewById(R.id.driver_5);

        if (driver1 != null) driver1.setOnClickListener(driverClickListener);
        if (driver2 != null) driver2.setOnClickListener(driverClickListener);
        if (driver3 != null) driver3.setOnClickListener(driverClickListener);
        if (driver4 != null) driver4.setOnClickListener(driverClickListener);
        if (driver5 != null) driver5.setOnClickListener(driverClickListener);
    }
}