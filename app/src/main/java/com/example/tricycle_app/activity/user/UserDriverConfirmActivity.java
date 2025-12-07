package com.example.tricycle_app.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tricycle_app.R;
import com.example.tricycle_app.model.FareLocation;
import com.example.tricycle_app.repository.FareRepository;
import com.example.tricycle_app.utils.UserNavbar;
import com.example.tricycle_app.utils.UserTripManager;

public class UserDriverConfirmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userdriverconfirm);

        UserNavbar.setup(this);

        // --- RETRIEVE DATA ---
        UserTripManager trip = UserTripManager.getInstance();

        // Find Views (Ensure you added IDs to these TextViews in XML!)
        TextView tvDriverName = findViewById(R.id.tvDriverName); // Add ID in XML
        TextView tvFrom = findViewById(R.id.tvFrom);             // Add ID in XML
        TextView tvTo = findViewById(R.id.tvTo);                 // Add ID in XML
        TextView tvPrice = findViewById(R.id.tvPrice);           // Add ID in XML

        // Set Data
        if(tvDriverName != null) tvDriverName.setText("Driver: " + trip.getDriverName());
        if(tvFrom != null) tvFrom.setText(trip.getFromLocation());
        if(tvTo != null) tvTo.setText(trip.getToLocation());

        // Calculate/Set Price (Mock logic or lookup)
        String price = "15.00"; // Default
        FareLocation loc = FareRepository.getFareByName(trip.getToLocation());
        if(loc != null) price = loc.getBaseFare();

        trip.setTripDetails(price, "2.5 km", "5 min"); // Save for later
        if(tvPrice != null) tvPrice.setText("₱" + price);


        TextView btnConfirmRide = findViewById(R.id.btnConfirmRide);
        if (btnConfirmRide != null) {
            btnConfirmRide.setOnClickListener(v -> {
                Intent intent = new Intent(UserDriverConfirmActivity.this, UserDriverWaitingActivity.class);
                startActivity(intent);
            });
        }

        LinearLayout btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) btnBack.setOnClickListener(v -> finish());
    }
}