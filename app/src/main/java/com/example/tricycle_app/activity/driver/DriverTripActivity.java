package com.example.tricycle_app.activity.driver;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.tricycle_app.R;
import com.example.tricycle_app.utils.DriverNavbar;

public class DriverTripActivity extends AppCompatActivity {

    private TextView btnAction, tvStatusTitle, tvStatusDesc;
    private int currentState = 0;
    // 0: Heading to Passenger, 1: Waiting, 2: In Progress, 3: Complete

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drivertrip);


        btnAction = findViewById(R.id.btnAction);
        tvStatusTitle = findViewById(R.id.tvStatusTitle);
        tvStatusDesc = findViewById(R.id.tvStatusDesc);

        // Initial State (After accepting)
        updateUI();

        btnAction.setOnClickListener(v -> {
            currentState++;
            if (currentState > 3) {
                // Done
                Toast.makeText(this, "Trip Completed! Payment Received.", Toast.LENGTH_LONG).show();
                finish(); // Go back to requests or dashboard
            } else {
                updateUI();
            }
        });
    }

    private void updateUI() {
        switch (currentState) {
            case 0:
                tvStatusTitle.setText("Heading to Passenger");
                tvStatusDesc.setText("Navigate to the pickup point.");
                btnAction.setText("I've Arrived");
                btnAction.setBackgroundResource(R.drawable.bg_pill_blue);
                break;
            case 1:
                tvStatusTitle.setText("Waiting for Passenger");
                tvStatusDesc.setText("Passenger has been notified.");
                btnAction.setText("Start Trip");
                btnAction.setBackgroundResource(R.drawable.bg_pill_green);
                break;
            case 2:
                tvStatusTitle.setText("Trip in Progress");
                tvStatusDesc.setText("Driving to destination...");
                btnAction.setText("Complete Trip");
                btnAction.setBackgroundResource(R.drawable.bg_pill_blue);
                break;
            case 3:
                tvStatusTitle.setText("Payment");
                tvStatusDesc.setText("Collect Cash: ₱15.00");
                btnAction.setText("Confirm Payment");
                btnAction.setBackgroundResource(R.drawable.bg_pill_green);
                break;
        }
    }
}