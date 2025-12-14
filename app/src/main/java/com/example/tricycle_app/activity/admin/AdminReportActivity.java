package com.example.tricycle_app.activity.admin;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tricycle_app.R;
import com.example.tricycle_app.utils.AdminNavbar;
import com.example.tricycle_app.utils.DataRepository;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class AdminReportActivity extends AppCompatActivity {

    private TextView tvDateFrom, tvDateTo;
    private TextView tvTotalTrips, tvTotalEarnings, tvCommission, tvReportNewUsers;
    private LinearLayout layoutDriverBreakdown;

    private Calendar calendarFrom, calendarTo;
    private SimpleDateFormat dateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adminreports);

        AdminNavbar.setup(this);

        // 1. Initialize Views
        tvDateFrom = findViewById(R.id.tvDateFrom);
        tvDateTo = findViewById(R.id.tvDateTo);
        tvTotalTrips = findViewById(R.id.tvTotalTrips);
        tvTotalEarnings = findViewById(R.id.tvTotalEarnings);
        tvCommission = findViewById(R.id.tvCommission);
        tvReportNewUsers = findViewById(R.id.tvReportNewUsers);
        layoutDriverBreakdown = findViewById(R.id.layoutDriverBreakdown);

        LinearLayout btnBack = findViewById(R.id.btnBack);
        LinearLayout btnDateFrom = findViewById(R.id.btnDateFrom);
        LinearLayout btnDateTo = findViewById(R.id.btnDateTo);

        if (btnBack != null) btnBack.setOnClickListener(v -> finish());

        // 2. Setup Date Logic
        dateFormat = new SimpleDateFormat("MMMM dd yyyy", Locale.US);
        calendarFrom = Calendar.getInstance();
        calendarTo = Calendar.getInstance();

        // Default Range: Set to match your sample data (Oct - Nov 2024)
        calendarFrom.set(2024, Calendar.OCTOBER, 1);
        calendarTo.set(2024, Calendar.NOVEMBER, 30);

        updateDateLabels();
        updateReportData();

        // 3. Date Selectors
        if (btnDateFrom != null) btnDateFrom.setOnClickListener(v -> showDatePicker(calendarFrom));
        if (btnDateTo != null) btnDateTo.setOnClickListener(v -> showDatePicker(calendarTo));
    }

    private void showDatePicker(Calendar targetCalendar) {
        new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            targetCalendar.set(Calendar.YEAR, year);
            targetCalendar.set(Calendar.MONTH, month);
            targetCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            // Validate: From must be <= To
            if (calendarFrom.after(calendarTo)) {
                Toast.makeText(this, "Start date must be before End date", Toast.LENGTH_SHORT).show();
                calendarFrom.setTime(calendarTo.getTime()); // Reset to valid
            }

            updateDateLabels();
            updateReportData(); // Recalculate on date change
        }, targetCalendar.get(Calendar.YEAR), targetCalendar.get(Calendar.MONTH), targetCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void updateDateLabels() {
        if (tvDateFrom != null) tvDateFrom.setText(dateFormat.format(calendarFrom.getTime()));
        if (tvDateTo != null) tvDateTo.setText(dateFormat.format(calendarTo.getTime()));
    }

    private void updateReportData() {
        List<String[]> rides = DataRepository.readData(this, "rides.txt");
        List<String[]> passengers = DataRepository.readData(this, "passengers.txt");

        int completedRides = 0;
        double totalEarnings = 0;
        int newUsersCount = 0;

        // Map to store Earnings per Driver Name
        Map<String, Double> driverEarningsMap = new HashMap<>();

        Date fromDate = truncateTime(calendarFrom.getTime());
        Date toDate = truncateTime(calendarTo.getTime());

        // --- 1. Process Rides (Revenue, Commission, Trips, Driver Breakdown) ---
        for (String[] ride : rides) {
            // Index 2: Driver Name, Index 5: Date, Index 7: Status, Index 10: TotalFare
            if (ride.length > 10 && "Completed".equalsIgnoreCase(ride[7].trim())) {
                try {
                    String dateStr = ride[5].trim();
                    Date rideDate = dateFormat.parse(dateStr);

                    // Check if date is within range
                    if (rideDate != null && !rideDate.before(fromDate) && !rideDate.after(toDate)) {
                        completedRides++;
                        double fare = Double.parseDouble(ride[10].trim());
                        totalEarnings += fare;

                        // Add to Driver Map
                        String driverName = ride[2].trim();
                        double currentTotal = driverEarningsMap.containsKey(driverName) ? driverEarningsMap.get(driverName) : 0.0;
                        driverEarningsMap.put(driverName, currentTotal + fare);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        // --- 2. Calculate Total Commission (15%) ---
        double totalCommission = totalEarnings * 0.15;

        // --- 3. Process Passengers (New Users) ---
        for (String[] p : passengers) {
            if (p.length > 6) {
                try {
                    String dateStr = p[6].trim();
                    Date joinDate = dateFormat.parse(dateStr);
                    if (joinDate != null && !joinDate.before(fromDate) && !joinDate.after(toDate)) {
                        newUsersCount++;
                    }
                } catch (Exception e) { e.printStackTrace(); }
            }
        }

        // --- 4. Update Top Stats UI ---
        if (tvTotalTrips != null) tvTotalTrips.setText(String.valueOf(completedRides));
        if (tvTotalEarnings != null) tvTotalEarnings.setText("₱" + String.format("%.2f", totalEarnings));
        if (tvCommission != null) tvCommission.setText("₱" + String.format("%.2f", totalCommission));
        if (tvReportNewUsers != null) tvReportNewUsers.setText(String.valueOf(newUsersCount));

        // --- 5. Update Driver Breakdown List ---
        if (layoutDriverBreakdown != null) {
            layoutDriverBreakdown.removeAllViews(); // Clear old list

            for (Map.Entry<String, Double> entry : driverEarningsMap.entrySet()) {
                View itemView = LayoutInflater.from(this).inflate(R.layout.item_payout, layoutDriverBreakdown, false);

                TextView tvName = itemView.findViewById(R.id.tvDriverName);
                TextView tvAmount = itemView.findViewById(R.id.tvAmount);
                TextView tvAction = itemView.findViewById(R.id.tvAction); // Using this for extra info
                LinearLayout btnAction = itemView.findViewById(R.id.btnAction); // The container

                // Data
                String name = entry.getKey();
                double amount = entry.getValue();
                double driverCommission = amount * 0.15;

                if (tvName != null) tvName.setText(name);
                if (tvAmount != null) tvAmount.setText("Total: ₱" + String.format("%.2f", amount));

                // Show Commission contribution in the "Action" text area
                if (tvAction != null) {
                    tvAction.setText("Commission: ₱" + String.format("%.2f", driverCommission));
                    tvAction.setTextColor(getResources().getColor(android.R.color.holo_blue_dark));
                }

                // Remove button look since it's a report
                if (btnAction != null) {
                    btnAction.setBackground(null);
                }

                layoutDriverBreakdown.addView(itemView);
            }
        }
    }

    private Date truncateTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
}