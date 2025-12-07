package com.example.tricycle_app.activity.user;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tricycle_app.R;
import com.example.tricycle_app.model.Ride;
import com.example.tricycle_app.repository.UserRideRepository;
import com.example.tricycle_app.utils.UserNavbar;

public class UserRideDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userridedetails);

        UserNavbar.setup(this);
        UserRideRepository.init(this);

        String rideId = getIntent().getStringExtra("RIDE_ID");
        Ride r = UserRideRepository.getRideById(rideId);

        if (r != null) {
            // Populate fields (No ID shown)
            setText(R.id.tvFrom, r.getFromLocation());
            setText(R.id.tvTo, r.getToLocation());
            setText(R.id.tvDriver, r.getDriver());
            setText(R.id.tvDateTime, r.getDate() + ", " + r.getTime());
            setText(R.id.tvTotalFare, "₱" + r.getTotalFare());

            TextView tvStatus = findViewById(R.id.tvStatus);
            tvStatus.setText(r.getStatus());

            if (r.getStatus().equalsIgnoreCase("Completed")) {
                tvStatus.setTextColor(android.graphics.Color.parseColor("#088738"));
            } else if (r.getStatus().equalsIgnoreCase("Cancelled")) {
                tvStatus.setTextColor(android.graphics.Color.RED);
            }
        } else {
            Toast.makeText(this, "Ride Not Found", Toast.LENGTH_SHORT).show();
            finish();
        }

        LinearLayout btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) btnBack.setOnClickListener(v -> finish());
    }

    private void setText(int id, String text) {
        TextView tv = findViewById(id);
        if (tv != null) tv.setText(text);
    }
}