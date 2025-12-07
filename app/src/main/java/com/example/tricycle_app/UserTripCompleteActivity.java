package com.example.tricycle_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class UserTripCompleteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usertripcomplete);

        // 1. Setup Navigation Bar
        UserNavbar.setup(this);

        // 2. Find Views
        TextView btnRatePay = findViewById(R.id.btnRatePay);
        LinearLayout btnBack = findViewById(R.id.btnBack);

        // 3. "Rate & Pay" Button Logic -> Go to Home Dashboard
        if (btnRatePay != null) {
            btnRatePay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(UserTripCompleteActivity.this, "Payment Successful!", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(UserTripCompleteActivity.this, UserMainDashboardActivity.class);
                    // Clear the back stack so the user cannot go back to this screen
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
            });
        }

        // 4. Back Button Logic
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }
    }
}