package com.example.tricycle_app.activity.user;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tricycle_app.R;
import com.example.tricycle_app.utils.UserNavbar;
import com.example.tricycle_app.utils.UserTripManager;

public class UserDriverSelectActivity extends AppCompatActivity {

    private View selectedDriverView = null;
    private String selectedDriverName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userdriverselect);

        UserNavbar.setup(this);

        TextView btnConfirm = findViewById(R.id.btnConfirm);
        LinearLayout btnBack = findViewById(R.id.btnBack);

        // Pass name to helper
        setupDriver(findViewById(R.id.driver1), "Mike");
        setupDriver(findViewById(R.id.driver2), "Robin");
        setupDriver(findViewById(R.id.driver3), "Loloy");

        if (btnConfirm != null) {
            btnConfirm.setOnClickListener(v -> {
                if (selectedDriverName.isEmpty()) {
                    Toast.makeText(this, "Please select a driver", Toast.LENGTH_SHORT).show();
                } else {
                    // SAVE DRIVER TO MANAGER
                    UserTripManager.getInstance().setDriver(selectedDriverName);

                    Intent intent = new Intent(UserDriverSelectActivity.this, UserPaymentSelectActivity.class);
                    startActivity(intent);
                }
            });
        }

        if (btnBack != null) btnBack.setOnClickListener(v -> finish());
    }

    private void setupDriver(View view, String name) {
        if (view == null) return;
        view.setOnClickListener(v -> {
            if (selectedDriverView != null) selectedDriverView.setBackgroundColor(Color.TRANSPARENT);
            selectedDriverView = view;
            selectedDriverView.setBackgroundResource(R.drawable.bg_rounded_light_grey);
            selectedDriverName = name;
        });
    }
}