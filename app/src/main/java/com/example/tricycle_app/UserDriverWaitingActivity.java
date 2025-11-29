package com.example.tricycle_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class UserDriverWaitingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userdriverwaiting); // Your layout file

        // 1. Setup Navigation
        UserNavbar.setup(this);

        // 2. Find Views
        LinearLayout btnContact = findViewById(R.id.btnContact);
        LinearLayout btnCancelRide = findViewById(R.id.btnCancelRide);
        LinearLayout btnBack = findViewById(R.id.btnBack);

        // 3. Contact Button Logic -> Go to Driver Arrived Screen
        // (In a real app, this might open a chat/call, but for this flow we simulate arrival)
        if (btnContact != null) {
            btnContact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(UserDriverWaitingActivity.this, UserArrivedActivity.class);
                    startActivity(intent);
                }
            });
        }

        // 4. Cancel Ride Logic
        if (btnCancelRide != null) {
            btnCancelRide.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(UserDriverWaitingActivity.this, "Ride Cancelled", Toast.LENGTH_SHORT).show();
                    // Navigate back to dashboard or home
                    Intent intent = new Intent(UserDriverWaitingActivity.this, UserMainDashboardActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
            });
        }

        // 5. Back Button Logic
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }
    }
}