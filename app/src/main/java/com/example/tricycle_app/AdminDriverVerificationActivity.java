package com.example.tricycle_app;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class AdminDriverVerificationActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Make sure you have created admindriververification.xml in layout folder
        setContentView(R.layout.admindriververification);

        AdminNavbar.setup(this);
    }
}