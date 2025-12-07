package com.example.tricycle_app.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.tricycle_app.R;
import com.example.tricycle_app.utils.DriverNavbar;
import com.example.tricycle_app.activity.driver.DriverProfileActivity;

public class DriverDashboardActivity extends AppCompatActivity {

    private TextView tvStatus;
    private Switch switchStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driverdashboard);

        DriverNavbar.setup(this);

        tvStatus = findViewById(R.id.tvStatus);
        switchStatus = findViewById(R.id.switchStatus);

        // Ride Request Button
        LinearLayout btnRideRequest = findViewById(R.id.btnRideRequest);

        // Profile Button (top right transparent box)
        LinearLayout btnProfile = findViewById(R.id.btnProfile);

        // Earnings Cards
        LinearLayout cardToday = findViewById(R.id.cardToday);
        LinearLayout cardWeek = findViewById(R.id.cardWeek);
        LinearLayout cardMonth = findViewById(R.id.cardMonth);

        // --- STATUS SWITCH LOGIC ---
        if (switchStatus != null) {
            switchStatus.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    tvStatus.setText("Online");
                    Toast.makeText(this, "You are now ONLINE", Toast.LENGTH_SHORT).show();
                } else {
                    tvStatus.setText("Offline");
                    Toast.makeText(this, "You are now OFFLINE", Toast.LENGTH_SHORT).show();
                }
            });
        }

        // --- RIDE REQUEST NAVIGATION ---
        if (btnRideRequest != null) {
            btnRideRequest.setOnClickListener(v -> {
                if (switchStatus.isChecked()) {
                    startActivity(new Intent(this, DriverRequestActivity.class));
                } else {
                    Toast.makeText(this, "Go Online to view requests", Toast.LENGTH_SHORT).show();
                }
            });
        }

        // --- EARNINGS NAVIGATION ---
        if (cardToday != null) cardToday.setOnClickListener(v -> openEarnings("Day"));
        if (cardWeek != null) cardWeek.setOnClickListener(v -> openEarnings("Week"));
        if (cardMonth != null) cardMonth.setOnClickListener(v -> openEarnings("Month"));

        // --- PROFILE NAVIGATION ---
        if (btnProfile != null) {
            btnProfile.setOnClickListener(v -> {
                startActivity(new Intent(this, DriverProfileActivity.class));
            });
        }
    }

    private void openEarnings(String tabFilter) {
        Intent intent = new Intent(this, DriverEarningActivity.class);
        intent.putExtra("TAB_FILTER", tabFilter);
        startActivity(intent);
    }
}