package com.example.tricycle_app;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AdminPassengerDetailsActivity extends AppCompatActivity {

    private boolean isEditing = false;
    private int passengerIndex;

    private EditText etName, etPhone, etEmail, etAddress;
    private TextView tvHeaderName, tvHeaderId, tvStatus;
    private TextView btnLeft, btnRight;
    private LinearLayout btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adminpassengerdetails);

        AdminNavbar.setup(this);

        passengerIndex = getIntent().getIntExtra("PASSENGER_INDEX", 0);

        initViews();
        loadData();

        if (btnBack != null) btnBack.setOnClickListener(v -> finish());

        if (btnLeft != null) {
            btnLeft.setOnClickListener(v -> {
                if (isEditing) saveChanges();
                else enableEditing();
            });
        }

        if (btnRight != null) {
            btnRight.setOnClickListener(v -> showSuspendDialog());
        }
    }

    private void initViews() {
        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        etEmail = findViewById(R.id.etEmail);
        etAddress = findViewById(R.id.etAddress);

        tvHeaderName = findViewById(R.id.tvHeaderName);
        tvHeaderId = findViewById(R.id.tvHeaderId);
        tvStatus = findViewById(R.id.tvStatus);

        btnLeft = findViewById(R.id.btnLeft);   // Edit Button
        btnRight = findViewById(R.id.btnRight); // Suspend Button
        btnBack = findViewById(R.id.btnBack);
    }

    private void loadData() {
        Passenger p = PassengerRepository.getPassenger(passengerIndex);
        if (p != null) {
            etName.setText(p.getName());
            etPhone.setText(p.getPhone());
            etEmail.setText(p.getEmail());
            etAddress.setText(p.getAddress());

            tvHeaderName.setText(p.getName());
            tvHeaderId.setText("Passenger ID: " + p.getId());

            updateSuspendUI(p.isSuspended());
        }
    }

    private void updateSuspendUI(boolean isSuspended) {
        if (isSuspended) {
            // SUSPENDED STATE
            tvStatus.setText("Status: Suspended");
            tvStatus.setTextColor(Color.RED);
            tvHeaderName.setTextColor(Color.RED);

            btnRight.setText("Unsuspend");
            btnRight.setBackgroundResource(R.drawable.bg_pill_green); // Green Pill
        } else {
            // ACTIVE STATE
            tvStatus.setText("Status: Active");
            tvStatus.setTextColor(Color.parseColor("#088738")); // Green Text
            tvHeaderName.setTextColor(Color.parseColor("#0D141C"));

            btnRight.setText("Suspend");
            btnRight.setBackgroundResource(R.drawable.bg_pill_red); // Red Pill
        }
    }

    private void enableEditing() {
        isEditing = true;
        btnLeft.setText("Save");
        btnLeft.setTextColor(Color.WHITE);
        btnLeft.setBackgroundResource(R.drawable.bg_pill_blue);

        etName.setEnabled(true); etPhone.setEnabled(true); etEmail.setEnabled(true); etAddress.setEnabled(true);
        etName.requestFocus();
    }

    private void saveChanges() {
        PassengerRepository.updatePassenger(this, passengerIndex,
                etName.getText().toString(), etPhone.getText().toString(),
                etEmail.getText().toString(), etAddress.getText().toString());

        isEditing = false;
        btnLeft.setText("Edit");
        btnLeft.setTextColor(Color.parseColor("#0D141C"));
        btnLeft.setBackgroundResource(R.drawable.bg_pill_grey);

        etName.setEnabled(false); etPhone.setEnabled(false); etEmail.setEnabled(false); etAddress.setEnabled(false);
        Toast.makeText(this, "Changes Saved!", Toast.LENGTH_SHORT).show();

        tvHeaderName.setText(etName.getText().toString());
    }

    private void showSuspendDialog() {
        Passenger p = PassengerRepository.getPassenger(passengerIndex);
        if (p == null) return;

        String title = p.isSuspended() ? "Unsuspend Passenger?" : "Suspend Passenger?";
        String msg = p.isSuspended() ? "Reactivate this account?" : "Suspend this account?";

        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton("Yes", (dialog, which) -> {
                    PassengerRepository.toggleSuspend(this, passengerIndex);
                    loadData(); // Refresh UI
                    String toastMsg = p.isSuspended() ? "Account Reactivated" : "Account Suspended";
                    Toast.makeText(this, toastMsg, Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}