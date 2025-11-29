package com.example.tricycle_app;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class UserClientProfileActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userclientprofile); // Ensure this XML file exists!

        // Initialize Navigation
        UserNavbar.setup(this);
    }
}