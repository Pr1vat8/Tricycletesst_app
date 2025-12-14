package com.example.tricycle_app.activity.admin;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tricycle_app.R;
import com.example.tricycle_app.model.Passenger;
import com.example.tricycle_app.model.Ride;
import com.example.tricycle_app.repository.PassengerRepository;
import com.example.tricycle_app.repository.RideRepository;
import com.example.tricycle_app.utils.AdminNavbar;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AdminStatisticsActivity extends AppCompatActivity {

    private TextView tvDriversOnline, tvNewUsers, tvTotalRevenue, tvTotalRides;
    private TextView tvDateFrom, tvDateTo;

    private Calendar calendarFrom, calendarTo;
    private SimpleDateFormat dateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_statistics);

        AdminNavbar.setup(this);
        RideRepository.init(this);
        PassengerRepository.init(this);

        // UI Initialization
        tvDriversOnline = findViewById(R.id.tvDriversOnline);
        tvNewUsers = findViewById(R.id.tvNewUsers);
        tvTotalRevenue = findViewById(R.id.tvTotalRevenue);
        tvTotalRides = findViewById(R.id.tvTotalRides);
        tvDateFrom = findViewById(R.id.tvDateFrom);
        tvDateTo = findViewById(R.id.tvDateTo);
        LinearLayout btnFrom = findViewById(R.id.btnDateFrom);
        LinearLayout btnTo = findViewById(R.id.btnDateTo);
        LinearLayout btnBack = findViewById(R.id.btnBack);

        if (btnBack != null) btnBack.setOnClickListener(v -> finish());

        // Date Format matches data: "MMMM dd yyyy" (e.g., October 01 2024)
        dateFormat = new SimpleDateFormat("MMMM dd yyyy", Locale.US);
        calendarFrom = Calendar.getInstance();
        calendarTo = Calendar.getInstance();

        // --- SET DEFAULT DATE TO OCTOBER 2024 (MATCHING YOUR DATA) ---
        calendarFrom.set(2024, Calendar.OCTOBER, 1);
        calendarTo.set(2024, Calendar.OCTOBER, 31);

        updateDateLabels();
        loadDriversOnlineFromFile(); // Reads static "drivers_online" from txt
        calculateDynamicData();      // Calculates Revenue/Rides from txt based on date

        // Date Pickers
        if (btnFrom != null) btnFrom.setOnClickListener(v -> showDatePicker(calendarFrom));
        if (btnTo != null) btnTo.setOnClickListener(v -> showDatePicker(calendarTo));
    }

    private void showDatePicker(Calendar targetCalendar) {
        new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            targetCalendar.set(Calendar.YEAR, year);
            targetCalendar.set(Calendar.MONTH, month);
            targetCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            // Validation: From cannot be after To
            if (calendarFrom.after(calendarTo)) {
                Toast.makeText(this, "Start date cannot be after End date", Toast.LENGTH_SHORT).show();
                // Reset to valid state (copy To date)
                calendarFrom.setTime(calendarTo.getTime());
            }

            updateDateLabels();
            calculateDynamicData();
        }, targetCalendar.get(Calendar.YEAR), targetCalendar.get(Calendar.MONTH), targetCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void updateDateLabels() {
        if (tvDateFrom != null) tvDateFrom.setText(dateFormat.format(calendarFrom.getTime()));
        if (tvDateTo != null) tvDateTo.setText(dateFormat.format(calendarTo.getTime()));
    }

    private void calculateDynamicData() {
        List<Ride> rides = RideRepository.getAllRides();
        List<Passenger> passengers = PassengerRepository.getAllPassengers();

        double revenue = 0;
        int rideCount = 0;
        int newUsersCount = 0;

        Date from = truncateTime(calendarFrom.getTime());
        Date to = truncateTime(calendarTo.getTime());

        // 1. Calculate Rides & Revenue
        for (Ride r : rides) {
            try {
                if (r.getDate() != null) {
                    Date rideDate = dateFormat.parse(r.getDate());
                    // Check if date is within range (Inclusive)
                    if (rideDate != null && !rideDate.before(from) && !rideDate.after(to)) {
                        rideCount++;
                        if ("Completed".equalsIgnoreCase(r.getStatus())) {
                            revenue += Double.parseDouble(r.getTotalFare());
                        }
                    }
                }
            } catch (Exception e) { e.printStackTrace(); }
        }

        // 2. Calculate New Users
        for (Passenger p : passengers) {
            try {
                if (p.getDateJoined() != null) {
                    Date joinDate = dateFormat.parse(p.getDateJoined());
                    if (joinDate != null && !joinDate.before(from) && !joinDate.after(to)) {
                        newUsersCount++;
                    }
                }
            } catch (Exception e) { e.printStackTrace(); }
        }

        // Update UI
        if (tvTotalRides != null) tvTotalRides.setText(String.valueOf(rideCount));
        if (tvNewUsers != null) tvNewUsers.setText(String.valueOf(newUsersCount));
        if (tvTotalRevenue != null) tvTotalRevenue.setText("₱" + String.format("%.2f", revenue));
    }

    // Helper to ignore time part of date for strict day comparison
    private Date truncateTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    private void loadDriversOnlineFromFile() {
        try {
            // Only reading "drivers_online" because other stats are now dynamic
            BufferedReader reader = new BufferedReader(new InputStreamReader(getAssets().open("statistics.txt")));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2 && "drivers_online".equals(parts[0].trim())) {
                    if (tvDriversOnline != null) tvDriversOnline.setText(parts[1].trim());
                    break;
                }
            }
            reader.close();
        } catch (Exception e) {
            if (tvDriversOnline != null) tvDriversOnline.setText("0");
        }
    }
}