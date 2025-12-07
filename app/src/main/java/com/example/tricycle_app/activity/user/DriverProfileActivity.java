package com.example.tricycle_app.activity.user;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tricycle_app.R;
import com.example.tricycle_app.utils.DriverNavbar;

public class DriverProfileActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driverprofile); // Ensure this XML exists
        DriverNavbar.setup(this);
    }
}