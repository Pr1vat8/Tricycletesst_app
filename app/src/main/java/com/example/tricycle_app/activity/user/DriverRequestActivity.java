package com.example.tricycle_app.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.tricycle_app.R;
import com.example.tricycle_app.utils.DriverNavbar;
// Import the trip activity if it's in a different package
import com.example.tricycle_app.activity.driver.DriverTripActivity;

public class DriverRequestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driverequest);

        DriverNavbar.setup(this);

        // FIX: Changed from TextView to View (or LinearLayout)
        View btnAccept = findViewById(R.id.btnAccept);

        // Optional: Setup Decline button too
        View btnDecline = findViewById(R.id.btnDecline);

        if (btnAccept != null) {
            btnAccept.setOnClickListener(v -> {
                Toast.makeText(this, "Request Accepted", Toast.LENGTH_SHORT).show();

                // Navigate to the Trip Process
                Intent intent = new Intent(DriverRequestActivity.this, DriverTripActivity.class);
                startActivity(intent);
                finish(); // Close request screen so back button doesn't return here
            });
        }

        if (btnDecline != null) {
            btnDecline.setOnClickListener(v -> {
                Toast.makeText(this, "Request Declined", Toast.LENGTH_SHORT).show();
                finish(); // Go back to dashboard
            });
        }

        LinearLayout btnBack = findViewById(R.id.btnBack);
        if(btnBack != null) btnBack.setOnClickListener(v -> finish());
    }
}