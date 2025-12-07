package com.example.tricycle_app.activity.user;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.tricycle_app.R;
import com.example.tricycle_app.model.Ride;
import com.example.tricycle_app.repository.DriverRideRepository;
import com.example.tricycle_app.utils.DriverNavbar;

public class DriverRideDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driverridedetails);

        DriverNavbar.setup(this);
        DriverRideRepository.init(this);

        String rideId = getIntent().getStringExtra("RIDE_ID");
        Ride r = DriverRideRepository.getRideById(rideId);

        LinearLayout btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) btnBack.setOnClickListener(v -> finish());

        if (r != null) {
            setText(R.id.tvRideId, "Ride #" + r.getRideId());
            setText(R.id.tvDate, r.getDate() + " • " + r.getTime());
            setText(R.id.tvStatus, r.getStatus());
            setText(R.id.tvFrom, r.getFromLocation());
            setText(R.id.tvTo, r.getToLocation());
            setText(R.id.tvTotalFare, "₱" + r.getTotalFare());

            // Set Status Color
            TextView tvStatus = findViewById(R.id.tvStatus);
            if (r.getStatus().equalsIgnoreCase("Completed")) {
                tvStatus.setTextColor(android.graphics.Color.parseColor("#088738")); // Green
            } else if (r.getStatus().equalsIgnoreCase("Cancelled")) {
                tvStatus.setTextColor(android.graphics.Color.RED);
            }
        } else {
            Toast.makeText(this, "Ride details not found.", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void setText(int id, String text) {
        TextView tv = findViewById(id);
        if (tv != null) tv.setText(text);
    }
}