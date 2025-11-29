package com.example.tricycle_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class UserDriverConfirmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userdriverconfirm); // Your layout file

        // 1. Setup Navigation
        UserNavbar.setup(this);

        // 2. Find Views
        TextView btnConfirmRide = findViewById(R.id.btnConfirmRide);
        LinearLayout btnBack = findViewById(R.id.btnBack);

        // 3. Confirm Ride Logic -> Go to Driver Waiting Screen
        if (btnConfirmRide != null) {
            btnConfirmRide.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(UserDriverConfirmActivity.this, UserDriverWaitingActivity.class);
                    startActivity(intent);
                }
            });
        }

        // 4. Back Button Logic
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }
    }
}