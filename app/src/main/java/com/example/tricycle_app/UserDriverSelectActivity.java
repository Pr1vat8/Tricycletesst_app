package com.example.tricycle_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class UserDriverSelectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userdriverselect); // Your driver list XML

        // 1. Setup Navigation Bar
        UserNavbar.setup(this);

        // 2. Find Views
        TextView btnConfirm = findViewById(R.id.btnConfirm);
        LinearLayout btnBack = findViewById(R.id.btnBack);

        // 3. Confirm Button Logic
        if (btnConfirm != null) {
            btnConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // In a real app, you would check if a driver is selected here.
                    // For now, we proceed to payment.
                    Intent intent = new Intent(UserDriverSelectActivity.this, UserPaymentSelectActivity.class);
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