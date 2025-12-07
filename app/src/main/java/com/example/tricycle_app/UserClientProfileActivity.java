package com.example.tricycle_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;

public class UserClientProfileActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userclientprofile);

        UserNavbar.setup(this);

        LinearLayout btnBack = findViewById(R.id.btnBack);
        LinearLayout btnEditProfile = findViewById(R.id.btnEditProfile);
        LinearLayout btnPaymentMethods = findViewById(R.id.btnPaymentMethods);
        LinearLayout btnSavedPlaces = findViewById(R.id.btnSavedPlaces);
        LinearLayout btnRideHistory = findViewById(R.id.btnRideHistory);

        if(btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        if(btnEditProfile != null) {
            btnEditProfile.setOnClickListener(v -> {
                startActivity(new Intent(this, UserEditProfileActivity.class));
            });
        }

        if(btnPaymentMethods != null) {
            btnPaymentMethods.setOnClickListener(v -> {
                startActivity(new Intent(this, UserPaymentMethodsActivity.class));
            });
        }

        if(btnSavedPlaces != null) {
            btnSavedPlaces.setOnClickListener(v -> {
                startActivity(new Intent(this, UserSavedPlacesActivity.class));
            });
        }

        if(btnRideHistory != null) {
            btnRideHistory.setOnClickListener(v -> {
                startActivity(new Intent(this, UserRideHistoryActivity.class));
            });
        }
    }
}