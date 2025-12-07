package com.example.tricycle_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class UserClientProfileActivity extends AppCompatActivity {

    private TextView tvUserName, tvPhone, tvMemberSince, tvAddress, tvAge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userclientprofile);

        UserNavbar.setup(this);
        UserRepository.init(this);

        tvUserName = findViewById(R.id.tvUserName);
        tvPhone = findViewById(R.id.tvPhone);
        tvAddress = findViewById(R.id.tvAddress);
        tvAge = findViewById(R.id.tvAge); // New
        tvMemberSince = findViewById(R.id.tvMemberSince);

        LinearLayout btnBack = findViewById(R.id.btnBack);
        LinearLayout btnEditProfile = findViewById(R.id.btnEditProfile);
        LinearLayout btnPaymentMethods = findViewById(R.id.btnPaymentMethods);
        LinearLayout btnSavedPlaces = findViewById(R.id.btnSavedPlaces);
        LinearLayout btnRideHistory = findViewById(R.id.btnRideHistory);

        loadUserData();

        if(btnBack != null) btnBack.setOnClickListener(v -> finish());

        if(btnEditProfile != null) {
            btnEditProfile.setOnClickListener(v -> {
                startActivity(new Intent(this, UserEditProfileActivity.class));
            });
        }

        if(btnPaymentMethods != null) {
            btnPaymentMethods.setOnClickListener(v -> startActivity(new Intent(this, UserPaymentMethodsActivity.class)));
        }

        if(btnSavedPlaces != null) {
            btnSavedPlaces.setOnClickListener(v -> startActivity(new Intent(this, UserSavedPlacesActivity.class)));
        }

        if(btnRideHistory != null) {
            btnRideHistory.setOnClickListener(v -> startActivity(new Intent(this, UserRideHistoryActivity.class)));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadUserData();
    }

    private void loadUserData() {
        User user = UserRepository.getUser();
        if (user != null) {
            if (tvUserName != null) tvUserName.setText(user.getName());
            if (tvPhone != null) tvPhone.setText(user.getPhone());
            if (tvAddress != null) tvAddress.setText(user.getAddress());
            if (tvAge != null) tvAge.setText("Age: " + user.getAge());
            if (tvMemberSince != null) tvMemberSince.setText("Member since " + user.getMemberSince());
        }
    }
}