package com.example.tricycle_app;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class AdminPassengerActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Make sure you have created adminpassenger.xml in layout folder
        setContentView(R.layout.adminpassenger);

        AdminNavbar.setup(this);
    }
}