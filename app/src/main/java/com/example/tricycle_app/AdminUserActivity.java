package com.example.tricycle_app;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class AdminUserActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adminusers); // Your drivers/users layout

        // Initialize Navigation Bar
        AdminNavbar.setup(this);
    }
}