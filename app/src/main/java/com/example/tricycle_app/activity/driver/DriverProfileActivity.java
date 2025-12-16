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

public class DriverProfileActivity extends AppCompatActivity {

    private boolean isEditing = false;
    private EditText etName, etPhone, etEmail, etPlate;
    private TextView tvHeaderName, tvDriverId, tvStatus, btnAction;
    private Driver currentDriver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driverprofile);

        DriverNavbar.setup(this);
        DriverRepository.init(this);

        // Check if ID was passed (e.g. from Login when suspended)
        String passedId = getIntent().getStringExtra("DRIVER_ID");
        if (passedId != null) {
            currentDriver = DriverRepository.getDriverById(passedId);
        } else {
            // Default fallback
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
        tvHeaderName = findViewById(R.id.tvHeaderName);
        tvDriverId = findViewById(R.id.tvDriverId);
        tvStatus = findViewById(R.id.tvStatus); // Ensure this ID exists in XML
        btnAction = findViewById(R.id.btnAction);
    }

    private void loadDriverData() {
        if(currentDriver != null) {
            tvHeaderName.setText(currentDriver.getName());
            tvDriverId.setText("Driver ID: " + currentDriver.getId());
            etName.setText(currentDriver.getName());
            etPhone.setText(currentDriver.getPhone());
            etEmail.setText(currentDriver.getEmail());
            etPlate.setText(currentDriver.getPlateNumber());

            // SUSPENSION CHECK
            if (currentDriver.isSuspended()) {
                String suspendMsg = "Status: Suspended";
                if(currentDriver.getSuspendStartDate() != null && !currentDriver.getSuspendStartDate().isEmpty()) {
                    suspendMsg += "\n(" + currentDriver.getSuspendStartDate() + " to " + currentDriver.getSuspendEndDate() + ")";
                }
                tvStatus.setText(suspendMsg);
                tvStatus.setTextColor(Color.RED);

                // Disable editing if suspended
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

        DriverRepository.updateDriver(
                this,
                currentDriver.getId(),
                newName,
                newPhone,
                newEmail,
                currentDriver.getAddress(),
                newPlate
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

        int color = enabled ? Color.BLACK : Color.parseColor("#61768A");
        etName.setTextColor(color); etPhone.setTextColor(color);
        etEmail.setTextColor(color); etPlate.setTextColor(color);
    }
}