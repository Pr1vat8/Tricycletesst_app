package com.example.tricycle_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView; // Import CardView

public class AdminDashboardActivity extends AppCompatActivity {

    private LinearLayout btnBack;
    private CardView btnViewChart; // Changed from TextView to CardView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admindashboard);

        // 1. Find the views
        btnBack = findViewById(R.id.btnBack);

        // This ID is attached to a CardView in your XML, so we cast it to CardView here
        btnViewChart = findViewById(R.id.btnViewChart);

        // 2. Back Button Logic
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // 3. View Chart Logic
        btnViewChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ensure AdminReportActivity is created and registered in Manifest
                Intent intent = new Intent(AdminDashboardActivity.this, AdminReportActivity.class);
                startActivity(intent);
            }
        });
    }
}