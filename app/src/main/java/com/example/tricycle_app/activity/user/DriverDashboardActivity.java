package com.example.tricycle_app.activity.user;

import android.content.Intent;
import android.graphics.Color; // Import Color class
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tricycle_app.R;
import com.example.tricycle_app.utils.DriverNavbar;

public class DriverDashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driverdashboard); // Connects to your XML

        // 1. Setup Navigation (Ensure DriverNavbar.java exists)
        DriverNavbar.setup(this);

        // 2. Find Views
        LinearLayout btnRideRequest = findViewById(R.id.btnRideRequest);
        Switch switchStatus = findViewById(R.id.switchStatus);
        TextView tvStatus = findViewById(R.id.tvStatus);

        // 3. Ride Request Click Logic
        if (btnRideRequest != null) {
            btnRideRequest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(DriverDashboardActivity.this, DriverRequestActivity.class);
                    startActivity(intent);
                }
            });
        }

        // 4. Toggle Switch Logic
        if (switchStatus != null) {
            switchStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        tvStatus.setText("Online");
                        // Use parseColor to fix the "symbol not found" error
                        tvStatus.setTextColor(Color.parseColor("#088738")); // Green
                        Toast.makeText(DriverDashboardActivity.this, "You are now Online", Toast.LENGTH_SHORT).show();
                    } else {
                        tvStatus.setText("Offline");
                        // Use parseColor to fix the "symbol not found" error
                        tvStatus.setTextColor(Color.parseColor("#61768A")); // Grey
                        Toast.makeText(DriverDashboardActivity.this, "You are now Offline", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}