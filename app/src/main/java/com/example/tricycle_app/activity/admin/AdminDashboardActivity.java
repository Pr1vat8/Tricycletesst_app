package com.example.tricycle_app.activity.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tricycle_app.R;
import com.example.tricycle_app.utils.AdminNavbar;
import com.example.tricycle_app.utils.DataRepository;

import java.util.List;

public class AdminDashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admindashboard);

        // Initialize Navigation
        AdminNavbar.setup(this);

        // --- 1. LOAD DATA ---
        updateDashboardData();

        // --- 2. CLICK LISTENERS ---

        // Pending Verification Button -> Opens AdminUserActivity (Pending Tab)
        TextView btnViewVerifications = findViewById(R.id.btnViewVerifications);
        if (btnViewVerifications != null) {
            btnViewVerifications.setOnClickListener(v -> {
                Intent intent = new Intent(AdminDashboardActivity.this, AdminUserActivity.class);
                intent.putExtra("TARGET_TAB", "Pending");
                startActivity(intent);
            });
        }

        // Statistics Listener (Shared for 3 cards)
        View.OnClickListener openStatsListener = v -> {
            Intent intent = new Intent(AdminDashboardActivity.this, AdminStatisticsActivity.class);
            startActivity(intent);
        };

        View cvDriversOnline = findViewById(R.id.cvDriversOnline);
        View cvRevenue = findViewById(R.id.cvRevenue);
        View cvNewUsers = findViewById(R.id.cvNewUsers);

        if (cvDriversOnline != null) cvDriversOnline.setOnClickListener(openStatsListener);
        if (cvRevenue != null) cvRevenue.setOnClickListener(openStatsListener);
        if (cvNewUsers != null) cvNewUsers.setOnClickListener(openStatsListener);

        // View Chart Button -> Open Reports
        TextView btnViewChart = findViewById(R.id.btnViewChart);
        if (btnViewChart != null) {
            btnViewChart.setOnClickListener(v -> {
                Intent intent = new Intent(AdminDashboardActivity.this, AdminReportActivity.class);
                startActivity(intent);
            });
        }
    }

    private void updateDashboardData() {
        // --- READ FROM STATISTICS.TXT ---
        List<String[]> stats = DataRepository.readData(this, "statistics.txt");

        TextView tvDriversOnline = findViewById(R.id.tvDriversOnline);
        TextView tvNewUsers = findViewById(R.id.tvNewUsers);
        TextView tvRevenue = findViewById(R.id.tvRevenue);

        for (String[] row : stats) {
            if (row.length >= 2) {
                String key = row[0].trim();
                String value = row[1].trim();

                if ("drivers_online".equals(key) && tvDriversOnline != null) {
                    tvDriversOnline.setText(value);
                } else if ("new_users".equals(key) && tvNewUsers != null) {
                    tvNewUsers.setText(value);
                } else if ("todays_revenue".equals(key) && tvRevenue != null) {
                    tvRevenue.setText("₱" + value);
                }
            }
        }

        // --- READ PENDING FROM DRIVERS.TXT ---
        // (Calculated dynamically because it might change as you verify users)
        List<String[]> drivers = DataRepository.readData(this, "drivers.txt");
        int pendingCount = 0;
        for (String[] driver : drivers) {
            // Check all columns for the word "Pending"
            for (String field : driver) {
                if ("Pending".equalsIgnoreCase(field.trim())) {
                    pendingCount++;
                    break;
                }
            }
        }

        TextView tvPendingVerifications = findViewById(R.id.tvPendingVerifications);
        if (tvPendingVerifications != null) {
            tvPendingVerifications.setText(pendingCount + " new applications");
        }
    }
}