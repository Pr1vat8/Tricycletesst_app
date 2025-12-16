package com.example.tricycle_app.activity.user;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tricycle_app.R;
import com.example.tricycle_app.adapter.DriverHistoryAdapter;
import com.example.tricycle_app.model.Ride;
import com.example.tricycle_app.repository.DriverEarningRepository;
import com.example.tricycle_app.repository.RideRepository;
import com.example.tricycle_app.utils.DriverNavbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DriverEarningActivity extends AppCompatActivity {

    private TextView tvTotalEarnings, tvTotalRides;
    private TextView tvDateFrom, tvDateTo;

    private RecyclerView rvRideHistory;
    private DriverHistoryAdapter rideAdapter;

    // Date Filters
    private Calendar calendarFrom;
    private Calendar calendarTo;
    private SimpleDateFormat displayFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.US);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driverearning);

        DriverNavbar.setup(this);
        DriverEarningRepository.init(this);

        // 1. Initialize Your New RideRepository
        // This copies rides.txt from assets to storage if needed, then loads them.
        RideRepository.init(this);

        // 2. Initialize Views
        tvTotalEarnings = findViewById(R.id.tvTotalEarnings);
        tvTotalRides = findViewById(R.id.tvTotalRides);
        tvDateFrom = findViewById(R.id.tvDateFrom);
        tvDateTo = findViewById(R.id.tvDateTo);

        LinearLayout btnDateFrom = findViewById(R.id.btnDateFrom);
        LinearLayout btnDateTo = findViewById(R.id.btnDateTo);
        LinearLayout btnBack = findViewById(R.id.btnBack);

        // 3. Setup RecyclerView
        rvRideHistory = findViewById(R.id.rvRideHistory);
        rvRideHistory.setLayoutManager(new LinearLayoutManager(this));
        rvRideHistory.setNestedScrollingEnabled(false);

        rideAdapter = new DriverHistoryAdapter(new ArrayList<>());
        rvRideHistory.setAdapter(rideAdapter);

        // 4. Initialize Dates
        calendarFrom = Calendar.getInstance();
        calendarTo = Calendar.getInstance();

        // --- KEY FIX: Default Start Date to Jan 1, 2024 ---
        // Since your rides.txt has 2024 dates, we must start here or they won't show.
        calendarFrom.set(2024, Calendar.JANUARY, 1);

        // 5. Listeners
        btnDateFrom.setOnClickListener(v -> showDatePicker(calendarFrom, true));
        btnDateTo.setOnClickListener(v -> showDatePicker(calendarTo, false));

        if (btnBack != null) btnBack.setOnClickListener(v -> finish());

        // 6. Initial Load
        updateDateLabels();
        updateList();

        // Debug Check
        if (RideRepository.getAllRides().isEmpty()) {
            Toast.makeText(this, "No rides loaded. Check assets/rides.txt", Toast.LENGTH_LONG).show();
        }
    }

    private void showDatePicker(Calendar targetCal, boolean isFrom) {
        DatePickerDialog picker = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                    targetCal.set(Calendar.YEAR, year);
                    targetCal.set(Calendar.MONTH, month);
                    targetCal.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                    // Validate: From Date cannot be after To Date
                    if (isFrom && targetCal.after(calendarTo)) {
                        calendarTo.setTime(targetCal.getTime());
                    } else if (!isFrom && targetCal.before(calendarFrom)) {
                        calendarFrom.setTime(targetCal.getTime());
                    }

                    updateDateLabels();
                    updateList();
                },
                targetCal.get(Calendar.YEAR),
                targetCal.get(Calendar.MONTH),
                targetCal.get(Calendar.DAY_OF_MONTH)
        );
        picker.show();
    }

    private void updateDateLabels() {
        if (tvDateFrom != null) tvDateFrom.setText(displayFormat.format(calendarFrom.getTime()));
        if (tvDateTo != null) tvDateTo.setText(displayFormat.format(calendarTo.getTime()));
    }

    private void updateList() {
        List<Ride> filteredRides = filterRideList();

        // Update Adapter
        if (rideAdapter != null) {
            rideAdapter.updateData(filteredRides);
        }

        // Calculate Totals based on the filtered list
        double totalEarnings = 0;
        for(Ride r : filteredRides) {
            try {
                if("Completed".equalsIgnoreCase(r.getStatus())) {
                    // Remove "₱" or "," before parsing
                    String rawFare = r.getTotalFare().replaceAll("[^\\d.]", "");
                    if (!rawFare.isEmpty()) {
                        totalEarnings += Double.parseDouble(rawFare);
                    }
                }
            } catch (Exception e) {
                // Ignore parsing errors for clean UX
            }
        }

        if (tvTotalEarnings != null) tvTotalEarnings.setText("₱" + String.format("%.2f", totalEarnings));
        if (tvTotalRides != null) tvTotalRides.setText(String.valueOf(filteredRides.size()));
    }

    private List<Ride> filterRideList() {
        // --- USE NEW FEATURE: Sort by newest first ---
        List<Ride> allRides = RideRepository.getSortedRides(true);
        List<Ride> result = new ArrayList<>();

        Date from = truncateTime(calendarFrom.getTime());
        Date to = truncateTime(calendarTo.getTime());

        for (Ride r : allRides) {
            // Parse using the format from your text file: "September 25 2024"
            Date rideDate = parseDateSafe(r.getDate());
            if (rideDate == null) continue;

            Date cleanRideDate = truncateTime(rideDate);

            // Check if date is within range [from, to] (inclusive)
            if (!cleanRideDate.before(from) && !cleanRideDate.after(to)) {
                result.add(r);
            }
        }
        return result;
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

    private Date parseDateSafe(String dateStr) {
        if (dateStr == null || dateStr.isEmpty()) return null;
        // Matches your text file format: "September 25 2024"
        String[] formats = { "MMMM dd yyyy", "MMM dd, yyyy", "yyyy-MM-dd" };

        for (String format : formats) {
            try {
                return new SimpleDateFormat(format, Locale.US).parse(dateStr);
            } catch (Exception e) {
                // Try next format
            }
        }
        return null;
    }
}