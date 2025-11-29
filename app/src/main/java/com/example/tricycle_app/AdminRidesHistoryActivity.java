package com.example.tricycle_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;

public class AdminRidesHistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adminrideshistory);

        // 1. Setup Navigation
        AdminNavbar.setup(this);

        // 2. Define listener for ride clicks
        View.OnClickListener rideClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the Details Screen
                Intent intent = new Intent(AdminRidesHistoryActivity.this, AdminRideDetailsActivity.class);
                startActivity(intent);
            }
        };

        // 3. Find buttons and attach listeners
        LinearLayout btnViewRide1 = findViewById(R.id.btnViewRide1);
        LinearLayout btnViewRide2 = findViewById(R.id.btnViewRide2);
        LinearLayout btnViewRide3 = findViewById(R.id.btnViewRide3);
        LinearLayout btnViewRide4 = findViewById(R.id.btnViewRide4);
        LinearLayout btnViewRide5 = findViewById(R.id.btnViewRide5);
        LinearLayout btnViewRide6 = findViewById(R.id.btnViewRide6);
        LinearLayout btnViewRide7 = findViewById(R.id.btnViewRide7);

        if (btnViewRide1 != null) btnViewRide1.setOnClickListener(rideClickListener);
        if (btnViewRide2 != null) btnViewRide2.setOnClickListener(rideClickListener);
        if (btnViewRide3 != null) btnViewRide3.setOnClickListener(rideClickListener);
        if (btnViewRide4 != null) btnViewRide4.setOnClickListener(rideClickListener);
        if (btnViewRide5 != null) btnViewRide5.setOnClickListener(rideClickListener);
        if (btnViewRide6 != null) btnViewRide6.setOnClickListener(rideClickListener);
        if (btnViewRide7 != null) btnViewRide7.setOnClickListener(rideClickListener);

        // Back Button
        LinearLayout btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }
    }
}