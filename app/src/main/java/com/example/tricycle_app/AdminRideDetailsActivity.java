package com.example.tricycle_app;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;

public class AdminRideDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Ensure you have created 'res/layout/adminridedetails.xml'
        setContentView(R.layout.adminridedetails);

        // Setup Navigation
        AdminNavbar.setup(this);

        // Back Button Logic
        LinearLayout btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }
    }
}