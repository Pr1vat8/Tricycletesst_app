package com.example.tricycle_app.activity.driver;

import android.app.DatePickerDialog; // Import added
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.tricycle_app.R;
import com.example.tricycle_app.model.Driver;
import com.example.tricycle_app.repository.DriverRepository;
import com.example.tricycle_app.utils.DriverNavbar;

import java.util.Calendar; // Import added

public class DriverProfileActivity extends AppCompatActivity {

    private boolean isEditing = false;
    // Added etAddress and etLicenseExpiration
    private EditText etName, etPhone, etEmail, etPlate, etAddress, etLicenseExpiration;
    private TextView tvHeaderName, tvDriverId, tvStatus, btnAction;
    private Driver currentDriver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driverprofile);

        DriverNavbar.setup(this);
        DriverRepository.init(this);

        String passedId = getIntent().getStringExtra("DRIVER_ID");
        if (passedId != null) {
            currentDriver = DriverRepository.getDriverById(passedId);
        } else {
            currentDriver = DriverRepository.getDriverById("101");
        }

        initViews();
        loadDriverData();

        LinearLayout btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) btnBack.setOnClickListener(v -> finish());
    }

    private void initViews() {
        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        etEmail = findViewById(R.id.etEmail);
        etPlate = findViewById(R.id.etPlate);

        // NEW: Initialize Address and Expiration fields
        etAddress = findViewById(R.id.etAddress);
        etLicenseExpiration = findViewById(R.id.etLicenseExpiration);

        tvHeaderName = findViewById(R.id.tvHeaderName);
        tvDriverId = findViewById(R.id.tvDriverId);
        tvStatus = findViewById(R.id.tvStatus);
        btnAction = findViewById(R.id.btnAction);

        // NEW: Setup Date Picker for Expiration
        etLicenseExpiration.setOnClickListener(v -> {
            if (isEditing) {
                showDatePicker();
            }
        });
    }

    private void showDatePicker() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year1, monthOfYear, dayOfMonth) -> {
                    // Format: YYYY-MM-DD
                    String selectedDate = year1 + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                    etLicenseExpiration.setText(selectedDate);
                }, year, month, day);
        datePickerDialog.show();
    }

    private void loadDriverData() {
        if(currentDriver != null) {
            tvHeaderName.setText(currentDriver.getName());
            tvDriverId.setText("Driver ID: " + currentDriver.getId());
            etName.setText(currentDriver.getName());
            etPhone.setText(currentDriver.getPhone());
            etEmail.setText(currentDriver.getEmail());
            etPlate.setText(currentDriver.getPlateNumber());

            // NEW: Load Address and Expiration
            etAddress.setText(currentDriver.getAddress());
            // Assuming your Driver model has a getLicenseExpirationDate() method
            // etLicenseExpiration.setText(currentDriver.getLicenseExpirationDate());

            if (currentDriver.isSuspended()) {
                String suspendMsg = "Status: Suspended";
                if(currentDriver.getSuspendStartDate() != null && !currentDriver.getSuspendStartDate().isEmpty()) {
                    suspendMsg += "\n(" + currentDriver.getSuspendStartDate() + " to " + currentDriver.getSuspendEndDate() + ")";
                }
                tvStatus.setText(suspendMsg);
                tvStatus.setTextColor(Color.RED);

                if (btnAction != null) {
                    btnAction.setVisibility(View.GONE);
                }
            } else {
                tvStatus.setText("Status: " + currentDriver.getStatus());
                if("Verified".equalsIgnoreCase(currentDriver.getStatus())) {
                    tvStatus.setTextColor(Color.parseColor("#088738"));
                } else {
                    tvStatus.setTextColor(Color.parseColor("#FF9800"));
                }
            }
        }
        setFieldsEnabled(false);
        setupEditButton();
    }

    private void setupEditButton() {
        if (btnAction != null && currentDriver != null && !currentDriver.isSuspended()) {
            btnAction.setOnClickListener(v -> {
                if (!isEditing) {
                    isEditing = true;
                    setFieldsEnabled(true);
                    btnAction.setText("Save");
                    etName.requestFocus();
                } else {
                    saveChanges();
                }
            });
        }
    }

    private void saveChanges() {
        String newName = etName.getText().toString().trim();
        String newPhone = etPhone.getText().toString().trim();
        String newEmail = etEmail.getText().toString().trim();
        String newPlate = etPlate.getText().toString().trim();

        // NEW: Get Address and Expiration data
        String newAddress = etAddress.getText().toString().trim();
        String newExpiration = etLicenseExpiration.getText().toString().trim();

        // NOTE: You must update your DriverRepository.updateDriver method signature
        // to accept newExpiration!
        DriverRepository.updateDriver(
                this,
                currentDriver.getId(),
                newName,
                newPhone,
                newEmail,
                newAddress, // Passed the new Address
                newPlate
                // newExpiration  <-- Add this to your repository call
        );

        tvHeaderName.setText(newName);

        isEditing = false;
        setFieldsEnabled(false);
        btnAction.setText("Edit");
        Toast.makeText(this, "Profile Saved!", Toast.LENGTH_SHORT).show();
    }

    private void setFieldsEnabled(boolean enabled) {
        etName.setEnabled(enabled);
        etPhone.setEnabled(enabled);
        etEmail.setEnabled(enabled);
        etPlate.setEnabled(enabled);

        // NEW: Enable/Disable Address
        etAddress.setEnabled(enabled);

        // For Date Picker, we usually keep clickable true but enabled false for text input
        // Or simply toggle clickable for the listener
        etLicenseExpiration.setEnabled(enabled);
        etLicenseExpiration.setClickable(enabled);

        int color = enabled ? Color.BLACK : Color.parseColor("#61768A");
        etName.setTextColor(color);
        etPhone.setTextColor(color);
        etEmail.setTextColor(color);
        etPlate.setTextColor(color);
        etAddress.setTextColor(color);
        etLicenseExpiration.setTextColor(color);
    }
}