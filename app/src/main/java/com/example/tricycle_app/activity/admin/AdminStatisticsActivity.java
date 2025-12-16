package com.example.tricycle_app.activity.admin;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tricycle_app.R;
import com.example.tricycle_app.adapter.StatRideAdapter;
import com.example.tricycle_app.model.Passenger;
import com.example.tricycle_app.model.Ride;
import com.example.tricycle_app.repository.PassengerRepository;
import com.example.tricycle_app.repository.RideRepository;
import com.example.tricycle_app.utils.AdminNavbar;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AdminStatisticsActivity extends AppCompatActivity {

    // UI Components
    private TextView tvDriversOnline, tvNewUsers, tvTotalRevenue, tvTotalRides;
    private TextView tvDateFrom, tvDateTo;

    // Recycler Components
    private RecyclerView recyclerView;
    private StatRideAdapter adapter;

    // Logic Components
    private Calendar calendarFrom, calendarTo;
    private SimpleDateFormat displayDateFormat; // Format for UI labels
    private SimpleDateFormat isoDateFormat;     // Format for data parsing (yyyy-MM-dd)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_statistics);

        // 1. Initialize Helpers & Repositories
        AdminNavbar.setup(this);
        RideRepository.init(this);
        PassengerRepository.init(this);

        // 2. Bind Views
        tvDriversOnline = findViewById(R.id.tvDriversOnline);
        tvNewUsers = findViewById(R.id.tvNewUsers);
        tvTotalRevenue = findViewById(R.id.tvTotalRevenue);
        tvTotalRides = findViewById(R.id.tvTotalRides);
        tvDateFrom = findViewById(R.id.tvDateFrom);
        tvDateTo = findViewById(R.id.tvDateTo);

        LinearLayout btnFrom = findViewById(R.id.btnDateFrom);
        LinearLayout btnTo = findViewById(R.id.btnDateTo);
        LinearLayout btnBack = findViewById(R.id.btnBack);

        // 3. Setup RecyclerView
        recyclerView = findViewById(R.id.recyclerViewStatistics);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setNestedScrollingEnabled(false);

        // 4. Back Button
        if (btnBack != null) btnBack.setOnClickListener(v -> finish());

        // 5. Date Logic Setup
        displayDateFormat = new SimpleDateFormat("MMMM dd yyyy", Locale.US);
        isoDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        calendarFrom = Calendar.getInstance();
        calendarTo = Calendar.getInstance();

        // --- FIX: SET DEFAULT RANGE TO COVER 2023 DATA ---
        // From: January 01, 2023
        calendarFrom.set(2023, Calendar.JANUARY, 1);
        // To: Current Date (or Dec 31, 2024)
        calendarTo.set(2024, Calendar.DECEMBER, 31);

        // 6. Load Initial Data
        updateDateLabels();
        loadDriversOnlineFromFile();
        calculateDynamicData();

        // 7. Date Picker Listeners
        if (btnFrom != null) btnFrom.setOnClickListener(v -> showDatePicker(calendarFrom));
        if (btnTo != null) btnTo.setOnClickListener(v -> showDatePicker(calendarTo));
    }

    private void showDatePicker(Calendar targetCalendar) {
        new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            targetCalendar.set(Calendar.YEAR, year);
            targetCalendar.set(Calendar.MONTH, month);
            targetCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            // Validation: Start date cannot be after End date
            if (calendarFrom.after(calendarTo)) {
                Toast.makeText(this, "Start date cannot be after End date", Toast.LENGTH_SHORT).show();
                calendarFrom.setTime(calendarTo.getTime());
            }

            updateDateLabels();
            calculateDynamicData();
        }, targetCalendar.get(Calendar.YEAR), targetCalendar.get(Calendar.MONTH), targetCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void updateDateLabels() {
        if (tvDateFrom != null) tvDateFrom.setText(displayDateFormat.format(calendarFrom.getTime()));
        if (tvDateTo != null) tvDateTo.setText(displayDateFormat.format(calendarTo.getTime()));
    }

    private void calculateDynamicData() {
        List<Ride> allRides = RideRepository.getAllRides();
        List<Passenger> passengers = PassengerRepository.getAllPassengers();
        List<Ride> filteredRides = new ArrayList<>();

        double revenue = 0;
        int rideCount = 0;
        int newUsersCount = 0;

        // Use truncateTime to ignore hours/minutes for accurate day comparison
        Date from = truncateTime(calendarFrom.getTime());
        Date to = truncateTime(calendarTo.getTime());

        // A. Filter Rides & Calculate Revenue/Count
        for (Ride r : allRides) {
            try {
                if (r.getDate() != null && !r.getDate().isEmpty()) {
                    // Rides usually use "MMMM dd yyyy" format based on your adapter
                    Date rideDate = displayDateFormat.parse(r.getDate());

                    if (rideDate != null && !rideDate.before(from) && !rideDate.after(to)) {
                        filteredRides.add(r);
                        rideCount++;
                        if ("Completed".equalsIgnoreCase(r.getStatus())) {
                            try {
                                revenue += Double.parseDouble(r.getTotalFare());
                            } catch (NumberFormatException e) {
                                // Ignore invalid fare strings
                            }
                        }
                    }
                }
            } catch (Exception e) { e.printStackTrace(); }
        }

        // B. Calculate New Users (Joined in Range)
        for (Passenger p : passengers) {
            try {
                if (p.getDateJoined() != null && !p.getDateJoined().isEmpty()) {
                    Date joinDate = null;
                    String rawDate = p.getDateJoined().trim();

                    // 1. Try ISO Format (yyyy-MM-dd) - Common in CSV
                    try {
                        joinDate = isoDateFormat.parse(rawDate);
                    } catch (Exception e) {
                        // 2. Fallback to Display Format (MMMM dd yyyy)
                        try {
                            joinDate = displayDateFormat.parse(rawDate);
                        } catch (Exception ex) {
                            // Date format unknown, skip
                        }
                    }

                    if (joinDate != null) {
                        // Check range (inclusive)
                        if (!joinDate.before(from) && !joinDate.after(to)) {
                            newUsersCount++;
                        }
                    }
                }
            } catch (Exception e) { e.printStackTrace(); }
        }

        // C. Update UI
        if (tvTotalRides != null) tvTotalRides.setText(String.valueOf(rideCount));
        if (tvNewUsers != null) tvNewUsers.setText(String.valueOf(newUsersCount));
        if (tvTotalRevenue != null) tvTotalRevenue.setText("₱" + String.format("%.2f", revenue));

        // D. Update RecyclerView
        if (adapter == null) {
            adapter = new StatRideAdapter(this, filteredRides);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.updateList(filteredRides);
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

    private void loadDriversOnlineFromFile() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(getAssets().open("statistics.txt")));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2 && "drivers_online".equals(parts[0].trim())) {
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