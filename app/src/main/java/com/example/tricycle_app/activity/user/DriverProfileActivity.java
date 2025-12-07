package com.example.tricycle_app.activity.user;

import android.graphics.Color;
import android.os.Bundle;
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
    private EditText etName, etPhone, etEmail;
    private TextView tvHeaderName, tvDriverId, btnAction;
    private Driver currentDriver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driverprofile);

        // 1. Setup
        DriverNavbar.setup(this);
        DriverRepository.init(this);

        // 2. Mock Login (Replace with real session ID later)
        // For now, we fetch the first driver or a specific ID like "101"
        currentDriver = DriverRepository.getDriverById("101");
        if (currentDriver == null && !DriverRepository.getAllDrivers().isEmpty()) {
            currentDriver = DriverRepository.getAllDrivers().get(0);
        }

        // 3. Find Views
        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        etEmail = findViewById(R.id.etEmail);
        tvHeaderName = findViewById(R.id.tvHeaderName);
        tvDriverId = findViewById(R.id.tvDriverId);
        btnAction = findViewById(R.id.btnAction);
        LinearLayout btnBack = findViewById(R.id.btnBack);

        // 4. Populate Data
        if(currentDriver != null) {
            tvHeaderName.setText(currentDriver.getName());
            tvDriverId.setText("Driver ID: " + currentDriver.getId());
            etName.setText(currentDriver.getName());
            etPhone.setText(currentDriver.getPhone());
            etEmail.setText(currentDriver.getEmail());
        }

        // Disable editing initially
        setFieldsEnabled(false);

        // 5. Button Logic
        if (btnAction != null) {
            btnAction.setOnClickListener(v -> {
                if (!isEditing) {
                    // --- SWITCH TO EDIT MODE ---
                    isEditing = true;
                    setFieldsEnabled(true);
                    btnAction.setText("Save");
                    etName.requestFocus();
                } else {
                    // --- SAVE CHANGES ---
                    if (currentDriver != null) {
                        String newName = etName.getText().toString().trim();
                        String newPhone = etPhone.getText().toString().trim();
                        String newEmail = etEmail.getText().toString().trim();

                        if (newName.isEmpty() || newPhone.isEmpty()) {
                            Toast.makeText(this, "Name and Phone are required", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        // Update in Repository
                        DriverRepository.updateDriver(
                                this,
                                currentDriver.getId(),
                                newName,
                                newPhone,
                                newEmail,
                                currentDriver.getAddress(),
                                currentDriver.getPlateNumber()
                        );

                        // Update Header UI
                        tvHeaderName.setText(newName);
                    }

                    // --- SWITCH BACK TO VIEW MODE ---
                    isEditing = false;
                    setFieldsEnabled(false);
                    btnAction.setText("Edit");
                    Toast.makeText(this, "Profile Saved!", Toast.LENGTH_SHORT).show();
                }
            });
        }

        if (btnBack != null) btnBack.setOnClickListener(v -> finish());
    }

    private void setFieldsEnabled(boolean enabled) {
        etName.setEnabled(enabled);
        etPhone.setEnabled(enabled);
        etEmail.setEnabled(enabled);

        // Change text color to indicate state (Black = Editable, Grey = Read-only)
        int color = enabled ? Color.BLACK : Color.parseColor("#61768A");
        etName.setTextColor(color);
        etPhone.setTextColor(color);
        etEmail.setTextColor(color);

        // Add/Remove background to show input area
        if (enabled) {
            etName.setBackgroundResource(android.R.drawable.edit_text);
            etPhone.setBackgroundResource(android.R.drawable.edit_text);
            etEmail.setBackgroundResource(android.R.drawable.edit_text);
        } else {
            etName.setBackgroundColor(Color.TRANSPARENT);
            etPhone.setBackgroundColor(Color.TRANSPARENT);
            etEmail.setBackgroundColor(Color.TRANSPARENT);
        }
    }
}