package com.example.tricycle_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;

public class UserMainDashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usermaindashboard);

        // 1. Setup the Navigation Bar (Home, Activity, Profile)
        UserNavbar.setup(this);

        // 2. Find the "Request a ride" button
        LinearLayout btnRequestRide = findViewById(R.id.btnRequestRide);

        // 3. Add Click Function
        if (btnRequestRide != null) {
            btnRequestRide.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Load userdriverselect.xml via the new Activity
                    Intent intent = new Intent(UserMainDashboardActivity.this, UserSetLocationActivity.class);
                    startActivity(intent);
                }
            });
        }

        // Optional: Wire up the Back button if needed
        LinearLayout btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }
    }
}