package com.example.tricycle_app;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AdminRideDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adminridedetails);

        AdminNavbar.setup(this);
        // Ensure Repo is ready if navigating directly or reloading
        RideRepository.init(this);

        String rideId = getIntent().getStringExtra("RIDE_ID");
        Ride r = RideRepository.getRideById(rideId);

        if (r != null) {
            setText(R.id.tvHeaderRideId, "Ride #" + r.getRideId());
            setText(R.id.tvPassenger, r.getPassenger());
            setText(R.id.tvDriver, r.getDriver());

            // --- New Fields ---
            setText(R.id.tvFrom, r.getFromLocation());
            setText(R.id.tvTo, r.getToLocation());
            // ------------------

            setText(R.id.tvDate, r.getDate());
            setText(R.id.tvTime, r.getTime());

            TextView tvStatus = findViewById(R.id.tvStatus);
            tvStatus.setText(r.getStatus());

            setText(R.id.tvBaseFare, "₱" + r.getBaseFare());
            setText(R.id.tvDistanceFare, "₱" + r.getDistanceFare());
            setText(R.id.tvTotalFare, "₱" + r.getTotalFare());

            // Status Color Logic
            if (r.getStatus().equalsIgnoreCase("Completed")) {
                tvStatus.setTextColor(android.graphics.Color.parseColor("#088738")); // Green
            } else if (r.getStatus().equalsIgnoreCase("Cancelled")) {
                tvStatus.setTextColor(android.graphics.Color.RED);
            } else {
                tvStatus.setTextColor(android.graphics.Color.parseColor("#4A739C")); // Default Blue/Grey
            }

        } else {
            Toast.makeText(this, "Ride Not Found", Toast.LENGTH_SHORT).show();
        }

        LinearLayout btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) btnBack.setOnClickListener(v -> finish());
    }

    private void setText(int id, String text) {
        TextView tv = findViewById(id);
        if (tv != null) tv.setText(text);
    }
}