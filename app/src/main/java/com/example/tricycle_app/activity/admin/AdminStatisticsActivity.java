package com.example.tricycle_app.activity.admin;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.tricycle_app.R;
import com.example.tricycle_app.model.Driver;
import com.example.tricycle_app.model.Ride;
import com.example.tricycle_app.repository.DriverRepository;
import com.example.tricycle_app.repository.RideRepository;
import com.example.tricycle_app.utils.AdminNavbar;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminStatisticsActivity extends AppCompatActivity {

    private TextView tvTotalRevenue, tvAppEarnings, tvDriverEarnings;
    private LinearLayout driverEarningsContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_statistics);

        AdminNavbar.setup(this);
        RideRepository.init(this);
        DriverRepository.init(this);

        // Initialize Views
        LinearLayout btnBack = findViewById(R.id.btnBack);
        tvTotalRevenue = findViewById(R.id.tvTotalRevenue);
        tvAppEarnings = findViewById(R.id.tvAppEarnings);
        tvDriverEarnings = findViewById(R.id.tvDriverEarnings);
        driverEarningsContainer = findViewById(R.id.driverEarningsContainer);

        if (btnBack != null) btnBack.setOnClickListener(v -> finish());

        calculateAndDisplayRevenue();
    }

    private void calculateAndDisplayRevenue() {
        List<Ride> rides = RideRepository.getAllRides();
        double totalRevenue = 0;
        Map<String, Double> driverRevenueMap = new HashMap<>();

        for (Ride ride : rides) {
            // Assuming we count all rides or filtered by status.
            // Usually, only "Completed" rides should count.
            if ("Completed".equalsIgnoreCase(ride.getStatus())) {
                try {
                    String fareStr = ride.getTotalFare().replace("₱", "").replace(",", "").trim();
                    double fare = Double.parseDouble(fareStr);
                    totalRevenue += fare;

                    String driverId = ride.getDriver(); // This might be Name or ID.
                    // To be safe, let's treat it as a key.
                    // Ideally, Ride should store Driver ID.
                    // If it stores Name, we might have issues with duplicate names, but for now we use what we have.

                    driverRevenueMap.put(driverId, driverRevenueMap.getOrDefault(driverId, 0.0) + fare);

                } catch (NumberFormatException e) {
                    // Ignore invalid fare formats
                }
            }
        }

        double appCommission = totalRevenue * 0.15;
        double driverNet = totalRevenue * 0.85;

        tvTotalRevenue.setText(String.format("₱%.2f", totalRevenue));
        tvAppEarnings.setText(String.format("₱%.2f", appCommission));
        tvDriverEarnings.setText(String.format("₱%.2f", driverNet));

        // Populate Driver List
        driverEarningsContainer.removeAllViews();

        for (Map.Entry<String, Double> entry : driverRevenueMap.entrySet()) {
            String driverKey = entry.getKey();
            double dTotal = entry.getValue();
            double dEarnings = dTotal * 0.85; // Driver takes 85%

            // Try to resolve Driver Name if Key is ID
            Driver driver = DriverRepository.getDriverById(driverKey);
            String driverName = (driver != null) ? driver.getName() : driverKey;

            addDriverRow(driverName, dEarnings, dTotal);
        }

        if (driverRevenueMap.isEmpty()) {
            TextView emptyView = new TextView(this);
            emptyView.setText("No ride data available.");
            emptyView.setPadding(0, 20, 0, 0);
            driverEarningsContainer.addView(emptyView);
        }
    }

    private void addDriverRow(String name, double earnings, double totalGenerated) {
        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setPadding(0, 20, 0, 20);
        row.setBackgroundResource(R.drawable.bg_rounded_border); // Reuse existing drawable or simple background
        // Since I can't verify if bg_rounded_border exists (it was used in XML), I'll assume it does.
        // Actually, in the XML read previously, it was used.

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, 0, 16);
        row.setLayoutParams(params);
        row.setGravity(Gravity.CENTER_VERTICAL);

        // Name
        TextView tvName = new TextView(this);
        tvName.setText(name);
        tvName.setTextSize(16);
        tvName.setTypeface(null, Typeface.BOLD);
        tvName.setTextColor(getResources().getColor(android.R.color.black));
        tvName.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));

        // Earnings Column
        LinearLayout earningsCol = new LinearLayout(this);
        earningsCol.setOrientation(LinearLayout.VERTICAL);
        earningsCol.setGravity(Gravity.END);

        TextView tvAmount = new TextView(this);
        tvAmount.setText(String.format("₱%.2f", earnings));
        tvAmount.setTextSize(16);
        tvAmount.setTypeface(null, Typeface.BOLD);
        tvAmount.setTextColor(getResources().getColor(android.R.color.holo_green_dark));

        TextView tvLabel = new TextView(this);
        tvLabel.setText("Total: ₱" + String.format("%.2f", totalGenerated));
        tvLabel.setTextSize(12);
        tvLabel.setTextColor(getResources().getColor(android.R.color.darker_gray));

        earningsCol.addView(tvAmount);
        earningsCol.addView(tvLabel);

        row.addView(tvName);
        row.addView(earningsCol);

        driverEarningsContainer.addView(row);
    }
}
