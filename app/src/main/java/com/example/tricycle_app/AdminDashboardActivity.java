package com.example.tricycle_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView; // Imported TextView
import androidx.appcompat.app.AppCompatActivity;

public class AdminDashboardActivity extends AppCompatActivity {

    private LinearLayout btnBack;
    private TextView btnViewChart; // Changed to TextView to match the XML

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admindashboard);

        // -----------------------------------------------------------
        // 1. SETUP BOTTOM NAVIGATION (Crucial for the buttons to work)
        // -----------------------------------------------------------
        AdminNavbar.setup(this);

        // 2. Find the views
        // Note: You must add android:id="@+id/btnBack" to your header icon in XML if you want this to work.
        btnBack = findViewById(R.id.btnBack);

        // Note: You must add android:id="@+id/btnViewChart" to the "View Chart" text in XML.
        btnViewChart = findViewById(R.id.btnViewChart);

        // 3. Back Button Logic (Optional for Dashboard)
        if (btnBack != null) {
            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed(); // Better than finish() for navigation handling
                }
            });
        }

        // 4. View Chart Logic
        if (btnViewChart != null) {
            btnViewChart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Navigate to the Reports/Chart screen
                    Intent intent = new Intent(AdminDashboardActivity.this, AdminReportActivity.class);
                    startActivity(intent);
                }
            });
        }
    }
}