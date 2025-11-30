package com.example.tricycle_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class AdminDashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admindashboard);

        // Initialize Navigation
        AdminNavbar.setup(this);

        // Find Views
        TextView btnViewVerifications = findViewById(R.id.btnViewVerifications);
        TextView btnViewChart = findViewById(R.id.btnViewChart);

        // LOGIC 1: Pending Verification Button -> Open Verification Screen
        if (btnViewVerifications != null) {
            btnViewVerifications.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Load admindriververification.xml via its activity
                    Intent intent = new Intent(AdminDashboardActivity.this, AdminDriverVerificationActivity.class);
                    startActivity(intent);
                }
            });
        }

        // LOGIC 2: View Chart Button -> Open Reports
        if (btnViewChart != null) {
            btnViewChart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(AdminDashboardActivity.this, AdminReportActivity.class);
                    startActivity(intent);
                }
            });
        }
    }
}