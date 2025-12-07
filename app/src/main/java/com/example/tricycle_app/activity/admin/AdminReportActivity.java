package com.example.tricycle_app.activity.admin;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tricycle_app.R;
import com.example.tricycle_app.utils.AdminNavbar;

public class AdminReportActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adminreports);// Ensure you have created adminreports.xml

        AdminNavbar.setup(this);
    }
}