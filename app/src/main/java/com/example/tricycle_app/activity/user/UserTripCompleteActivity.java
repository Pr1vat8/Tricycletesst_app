package com.example.tricycle_app.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tricycle_app.R;
import com.example.tricycle_app.model.Ride;
import com.example.tricycle_app.repository.UserRideRepository;
import com.example.tricycle_app.utils.UserNavbar;
import com.example.tricycle_app.utils.UserTripManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class UserTripCompleteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usertripcomplete);

        UserNavbar.setup(this);
        UserRideRepository.init(this); // Init Repo

        TextView btnRatePay = findViewById(R.id.btnRatePay);
        LinearLayout btnBack = findViewById(R.id.btnBack);

        if (btnRatePay != null) {
            btnRatePay.setOnClickListener(v -> {
                saveRideToHistory(); // Save Data

                Toast.makeText(this, "Payment Successful!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UserTripCompleteActivity.this, UserMainDashboardActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            });
        }

        if (btnBack != null) btnBack.setOnClickListener(v -> finish());
    }

    private void saveRideToHistory() {
        UserTripManager trip = UserTripManager.getInstance();

        // Generate Mock Data
        String id = String.valueOf(new Random().nextInt(90000) + 10000);
        String date = new SimpleDateFormat("MMMM dd yyyy", Locale.US).format(new Date());
        String time = new SimpleDateFormat("hh:mm a", Locale.US).format(new Date());

        // Create Ride Object
        Ride newRide = new Ride(
                id,
                "Vincent Comendador", // User Name
                trip.getDriverName(),
                trip.getFromLocation(),
                trip.getToLocation(),
                date,
                time,
                "Completed",
                trip.getPrice(),
                "0", // Distance Fare
                trip.getPrice() // Total
        );

        // Append to file
        UserRideRepository.addRide(this, newRide);
    }
}