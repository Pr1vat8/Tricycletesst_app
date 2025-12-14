package com.example.tricycle_app.activity.driver;

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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DriverProfileActivity extends AppCompatActivity {

    private boolean isEditing = false;
    private EditText etName, etPhone, etEmail, etPlate, etAddress, etLicenseNumber, etLicenseExpiration;
    private TextView tvHeaderName, tvDriverId, btnAction, tvWarningBanner;
    private Driver currentDriver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driverprofile);

        DriverNavbar.setup(this);
        DriverRepository.init(this);

        // Simulate fetching the current logged-in driver.
        // In a real app, this ID comes from Session Manager.
        currentDriver = DriverRepository.getDriverById("101");
        if (currentDriver == null && !DriverRepository.getAllDrivers().isEmpty()) {
            currentDriver = DriverRepository.getAllDrivers().get(0);
        }

        initializeViews();
        populateData();
        checkAccountStatus();

        if (btnAction != null) {
            btnAction.setOnClickListener(v -> handleEditAction());
        }

        LinearLayout btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) btnBack.setOnClickListener(v -> finish());
    }

    private void initializeViews() {
        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        etEmail = findViewById(R.id.etEmail);
        etPlate = findViewById(R.id.etPlate);
        etAddress = findViewById(R.id.etAddress);
        etLicenseNumber = findViewById(R.id.etLicenseNumber);
        etLicenseExpiration = findViewById(R.id.etLicenseExpiration);

        tvHeaderName = findViewById(R.id.tvHeaderName);
        tvDriverId = findViewById(R.id.tvDriverId);
        btnAction = findViewById(R.id.btnAction);
        tvWarningBanner = findViewById(R.id.tvWarningBanner);

        setFieldsEnabled(false);
    }

    private void populateData() {
        if(currentDriver != null) {
            tvHeaderName.setText(currentDriver.getName());
            tvDriverId.setText("Driver ID: " + currentDriver.getId());
            etName.setText(currentDriver.getName());
            etPhone.setText(currentDriver.getPhone());
            etEmail.setText(currentDriver.getEmail());
            etPlate.setText(currentDriver.getPlateNumber());
            etAddress.setText(currentDriver.getAddress());
            etLicenseNumber.setText(currentDriver.getLicenseNumber());
            etLicenseExpiration.setText(currentDriver.getLicenseExpirationDate());
        }
    }

    private void checkAccountStatus() {
        if (currentDriver == null) return;

        // 1. Check Suspension
        if (currentDriver.isSuspended()) {
            String msg = "ACCOUNT SUSPENDED";
            if (currentDriver.getSuspensionEndDate() != null && !currentDriver.getSuspensionEndDate().isEmpty()) {
                msg += " UNTIL " + currentDriver.getSuspensionEndDate();
            }
            showWarning(msg);
            return;
        }

        // 2. Check License Expiration
        String expirationDate = currentDriver.getLicenseExpirationDate();
        if (expirationDate != null && !expirationDate.isEmpty()) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            try {
                Date expDate = sdf.parse(expirationDate);
                if (expDate != null && expDate.before(new Date())) {
                    showWarning("LICENSE EXPIRED! PLEASE RENEW IMMEDIATELY.");
                }
            } catch (ParseException e) {
                // Ignore parse errors
            }
        }
    }

    private void showWarning(String message) {
        if (tvWarningBanner != null) {
            tvWarningBanner.setVisibility(View.VISIBLE);
            tvWarningBanner.setText(message);
        }
    }

    private void handleEditAction() {
        if (!isEditing) {
            // Enable Editing
            isEditing = true;
            setFieldsEnabled(true);
            btnAction.setText("Save");
            etName.requestFocus();
        } else {
            // Save Changes
            if (currentDriver != null) {
                String newName = etName.getText().toString().trim();
                String newPhone = etPhone.getText().toString().trim();
                String newEmail = etEmail.getText().toString().trim();
                String newPlate = etPlate.getText().toString().trim();
                String newAddress = etAddress.getText().toString().trim();
                String newLicense = etLicenseNumber.getText().toString().trim();
                String newExpiration = etLicenseExpiration.getText().toString().trim();

                DriverRepository.updateDriverFull(
                        this,
                        currentDriver.getId(),
                        newName,
                        newPhone,
                        newEmail,
                        newAddress,
                        newPlate,
                        newLicense,
                        newExpiration
                );

                // Update local object to reflect changes immediately
                currentDriver = DriverRepository.getDriverById(currentDriver.getId());
                tvHeaderName.setText(newName);

                // Re-check status in case they updated the license date
                tvWarningBanner.setVisibility(View.GONE);
                checkAccountStatus();
            }
            isEditing = false;
            setFieldsEnabled(false);
            btnAction.setText("Edit");
            Toast.makeText(this, "Profile Saved!", Toast.LENGTH_SHORT).show();
        }
    }

    private void setFieldsEnabled(boolean enabled) {
        etName.setEnabled(enabled);
        etPhone.setEnabled(enabled);
        etEmail.setEnabled(enabled);
        etPlate.setEnabled(enabled);
        etAddress.setEnabled(enabled);
        etLicenseNumber.setEnabled(enabled);
        etLicenseExpiration.setEnabled(enabled);

        int color = enabled ? Color.BLACK : Color.parseColor("#61768A");
        etName.setTextColor(color);
        etPhone.setTextColor(color);
        etEmail.setTextColor(color);
        etPlate.setTextColor(color);
        etAddress.setTextColor(color);
        etLicenseNumber.setTextColor(color);
        etLicenseExpiration.setTextColor(color);

        if (enabled) {
            etName.setBackgroundResource(android.R.drawable.edit_text);
            etPhone.setBackgroundResource(android.R.drawable.edit_text);
            etEmail.setBackgroundResource(android.R.drawable.edit_text);
            etPlate.setBackgroundResource(android.R.drawable.edit_text);
            etAddress.setBackgroundResource(android.R.drawable.edit_text);
            etLicenseNumber.setBackgroundResource(android.R.drawable.edit_text);
            etLicenseExpiration.setBackgroundResource(android.R.drawable.edit_text);
        } else {
            etName.setBackgroundColor(Color.TRANSPARENT);
            etPhone.setBackgroundColor(Color.TRANSPARENT);
            etEmail.setBackgroundColor(Color.TRANSPARENT);
            etPlate.setBackgroundColor(Color.TRANSPARENT);
            etAddress.setBackgroundColor(Color.TRANSPARENT);
            etLicenseNumber.setBackgroundColor(Color.TRANSPARENT);
            etLicenseExpiration.setBackgroundColor(Color.TRANSPARENT);
        }
    }
}
