package com.example.tricycle_app.activity.admin;

import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tricycle_app.R;
import com.example.tricycle_app.utils.AdminNavbar;

public class AdminDriverVerificationActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admindriververification);

        // Basic logic for back/approve/suspend...
        LinearLayout btnBack = findViewById(R.id.btnBack);
        if(btnBack != null) btnBack.setOnClickListener(v -> finish());

        AdminNavbar.setup(this);
    }
}