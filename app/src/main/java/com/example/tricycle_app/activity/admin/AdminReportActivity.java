package com.example.tricycle_app.activity.admin;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tricycle_app.R;
import com.example.tricycle_app.utils.AdminNavbar;
import com.example.tricycle_app.utils.DataRepository;

import java.util.List;

public class AdminReportActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adminreports);

        AdminNavbar.setup(this);

        updateReportData();
    }

    private void updateReportData() {
        List<String[]> rides = DataRepository.readData(this, "rides.txt");
        List<String[]> passengers = DataRepository.readData(this, "passengers.txt");

        // 1. Trips Over Time (Total Completed Rides)
        int completedRides = 0;
        double totalEarnings = 0;

        for (String[] ride : rides) {
            if (ride.length > 7 && "Completed".equalsIgnoreCase(ride[7].trim())) {
                completedRides++;
                if (ride.length > 10) {
                    try {
                        totalEarnings += Double.parseDouble(ride[10].trim());
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        TextView tvTotalTrips = findViewById(R.id.tvTotalTrips); // Add ID to XML
        if (tvTotalTrips != null) tvTotalTrips.setText(String.valueOf(completedRides));

        // 2. Total Earnings
        TextView tvTotalEarnings = findViewById(R.id.tvTotalEarnings); // Add ID to XML
        if (tvTotalEarnings != null) tvTotalEarnings.setText("₱" + String.format("%.2f", totalEarnings));

        // 3. New Users
        int newUsersCount = passengers.size();
        TextView tvReportNewUsers = findViewById(R.id.tvReportNewUsers); // Add ID to XML
        if (tvReportNewUsers != null) tvReportNewUsers.setText(String.valueOf(newUsersCount));
    }
}