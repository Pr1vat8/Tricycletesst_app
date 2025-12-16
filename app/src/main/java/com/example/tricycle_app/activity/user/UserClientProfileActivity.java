package com.example.tricycle_app.activity.user;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tricycle_app.R;
import com.example.tricycle_app.model.User;
import com.example.tricycle_app.utils.UserNavbar;

public class UserClientProfileActivity extends AppCompatActivity {

    private TextView tvUserName, tvPhone, tvMemberSince, tvAddress, tvAge;
    // New TextView for suspension status
    private TextView tvStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userclientprofile);

        UserNavbar.setup(this);
        UserRepository.init(this);

        tvUserName = findViewById(R.id.tvUserName);
        tvPhone = findViewById(R.id.tvPhone);
        tvAddress = findViewById(R.id.tvAddress);
        tvAge = findViewById(R.id.tvAge);
        tvMemberSince = findViewById(R.id.tvMemberSince);

        // Add Status TextView dynamically (since XML wasn't fully edited)
        // Or find it if you added it to XML. Here I'll add it to the profile container layout.
        LinearLayout profileContainer = (LinearLayout) tvUserName.getParent();
        tvStatus = new TextView(this);
        tvStatus.setTextSize(16);
        tvStatus.setPadding(0, 10, 0, 0);
        profileContainer.addView(tvStatus);

        loadUserData();

        // Buttons...
        LinearLayout btnBack = findViewById(R.id.btnBack);
        if(btnBack != null) btnBack.setOnClickListener(v -> finish());

        findViewById(R.id.btnEditProfile).setOnClickListener(v -> startActivity(new Intent(this, UserEditProfileActivity.class)));
        findViewById(R.id.btnPaymentMethods).setOnClickListener(v -> startActivity(new Intent(this, UserPaymentMethodsActivity.class)));
        findViewById(R.id.btnSavedPlaces).setOnClickListener(v -> startActivity(new Intent(this, UserSavedPlacesActivity.class)));
        findViewById(R.id.btnRideHistory).setOnClickListener(v -> startActivity(new Intent(this, UserRideHistoryActivity.class)));
    }

    private void loadUserData() {
        User user = UserRepository.getUser();
        if (user != null) {
            tvUserName.setText(user.getName());
            tvPhone.setText(user.getPhone());
            tvAddress.setText(user.getAddress());
            tvAge.setText("Age: " + user.getAge());
            tvMemberSince.setText("Member since " + user.getMemberSince());

            // SUSPENSION CHECK
            if (user.isSuspended()) {
                tvStatus.setVisibility(TextView.VISIBLE);
                String msg = "STATUS: SUSPENDED";
                if(user.getSuspendStartDate() != null && !user.getSuspendStartDate().isEmpty()) {
                    msg += "\nFrom: " + user.getSuspendStartDate() + "\nTo: " + user.getSuspendEndDate();
                }
                tvStatus.setText(msg);
                tvStatus.setTextColor(Color.RED);
                tvStatus.setTypeface(null, android.graphics.Typeface.BOLD);
            } else {
                tvStatus.setText("Status: Active");
                tvStatus.setTextColor(Color.parseColor("#088738")); // Green
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadUserData();
    }
}