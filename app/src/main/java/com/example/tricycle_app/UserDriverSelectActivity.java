package com.example.tricycle_app;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class UserDriverSelectActivity extends AppCompatActivity {

    private View selectedDriver = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userdriverselect);

        UserNavbar.setup(this);

        TextView btnConfirm = findViewById(R.id.btnConfirm);
        LinearLayout btnBack = findViewById(R.id.btnBack);

        // Setup Drivers
        setupDriverSelect(findViewById(R.id.driver1));
        setupDriverSelect(findViewById(R.id.driver2));
        setupDriverSelect(findViewById(R.id.driver3));

        if (btnConfirm != null) {
            btnConfirm.setOnClickListener(v -> {
                if (selectedDriver == null) {
                    Toast.makeText(this, "Please select a driver", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(UserDriverSelectActivity.this, UserPaymentSelectActivity.class);
                    startActivity(intent);
                }
            });
        }

        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }
    }

    private void setupDriverSelect(View view) {
        if (view == null) return;
        view.setOnClickListener(v -> {
            // Reset old selection
            if (selectedDriver != null) {
                selectedDriver.setBackgroundColor(Color.TRANSPARENT);
            }
            // Set new selection
            selectedDriver = view;
            selectedDriver.setBackgroundResource(R.drawable.bg_rounded_light_grey);
        });
    }
}