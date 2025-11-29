package com.example.tricycle_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class UserTripProgressActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usertripprogress); // Your layout file

        // 1. Setup Navigation
        UserNavbar.setup(this);

        // 2. Find Views
        // Note: In your XML, the "Arrived?" button has ID `btnShareTrip`
        LinearLayout btnArrived = findViewById(R.id.btnShareTrip);
        LinearLayout btnContactSupport = findViewById(R.id.btnContactSupport);
        LinearLayout btnBack = findViewById(R.id.btnBack);

        // 3. Arrived Button Logic -> Go to Trip Complete Screen
        if (btnArrived != null) {
            btnArrived.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(UserTripProgressActivity.this, UserTripCompleteActivity.class);
                    startActivity(intent);
                    finish(); // Optional: Finish this activity so back button doesn't return to progress
                }
            });
        }

        // 4. Contact Support Logic (Optional Placeholder)
        if (btnContactSupport != null) {
            btnContactSupport.setOnClickListener(v -> {
                Toast.makeText(this, "Contacting Support...", Toast.LENGTH_SHORT).show();
            });
        }

        // 5. Back Button Logic
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }
    }
}