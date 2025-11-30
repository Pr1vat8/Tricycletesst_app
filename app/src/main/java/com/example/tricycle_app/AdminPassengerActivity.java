package com.example.tricycle_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;

public class AdminPassengerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adminpassenger); // Connects to the XML above

        // 1. Setup Navigation
        AdminNavbar.setup(this);

        // 2. Setup Back Button
        LinearLayout btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        // 3. Create Listener for Passengers
        View.OnClickListener passengerClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Load the Passenger Details Screen
                Intent intent = new Intent(AdminPassengerActivity.this, AdminPassengerDetailsActivity.class);
                startActivity(intent);
            }
        };

        // 4. Find Views and Set Listeners
        LinearLayout btnPassenger1 = findViewById(R.id.btnPassenger1);
        LinearLayout btnPassenger2 = findViewById(R.id.btnPassenger2);
        LinearLayout btnPassenger3 = findViewById(R.id.btnPassenger3);

        if (btnPassenger1 != null) btnPassenger1.setOnClickListener(passengerClickListener);
        if (btnPassenger2 != null) btnPassenger2.setOnClickListener(passengerClickListener);
        if (btnPassenger3 != null) btnPassenger3.setOnClickListener(passengerClickListener);
    }
}