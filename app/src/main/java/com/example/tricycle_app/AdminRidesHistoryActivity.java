package com.example.tricycle_app;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class AdminRidesHistoryActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adminrideshistory); // Your rides history layout

        // Initialize Navigation Bar
        AdminNavbar.setup(this);
    }
}