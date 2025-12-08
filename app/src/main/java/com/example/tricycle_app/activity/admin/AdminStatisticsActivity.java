package com.example.tricycle_app.activity.admin;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.tricycle_app.R;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AdminStatisticsActivity extends AppCompatActivity {

    private TextView tvDriversOnline, tvNewUsers, tvRevenueToday, tvRevenueWeekly, tvRevenueMonthly;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_statistics);

        // Initialize Views
        LinearLayout btnBack = findViewById(R.id.btnBack);
        tvDriversOnline = findViewById(R.id.tvDriversOnline);
        tvNewUsers = findViewById(R.id.tvNewUsers);
        tvRevenueToday = findViewById(R.id.tvRevenueToday);
        tvRevenueWeekly = findViewById(R.id.tvRevenueWeekly);
        tvRevenueMonthly = findViewById(R.id.tvRevenueMonthly);

        if (btnBack != null) btnBack.setOnClickListener(v -> finish());

        // Load Data from Text File
        loadStatisticsData();
    }

    private void loadStatisticsData() {
        try {
            // Open the file from assets
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(getAssets().open("statistics.txt")));

            String line;
            while ((line = reader.readLine()) != null) {
                // Split line by comma (key,value)
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String key = parts[0].trim();
                    String value = parts[1].trim();

                    switch (key) {
                        case "drivers_online":
                            tvDriversOnline.setText(value);
                            break;
                        case "new_users":
                            tvNewUsers.setText(value);
                            break;
                        case "todays_revenue":
                            tvRevenueToday.setText("₱" + value);
                            break;
                        case "weekly_revenue":
                            tvRevenueWeekly.setText("₱" + value);
                            break;
                        case "monthly_revenue":
                            tvRevenueMonthly.setText("₱" + value);
                            break;
                    }
                }
            }
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}