package com.example.tricycle_app.activity.driver;

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
    private EditText etName, etPhone, etEmail, etPlate;
    private TextView tvHeaderName, tvDriverId, btnAction;
    private Driver currentDriver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driverprofile);

        DriverNavbar.setup(this);
        DriverRepository.init(this);

        currentDriver = DriverRepository.getDriverById("101");
        if (currentDriver == null && !DriverRepository.getAllDrivers().isEmpty()) {
            currentDriver = DriverRepository.getAllDrivers().get(0);
        }

        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        etEmail = findViewById(R.id.etEmail);
        etPlate = findViewById(R.id.etPlate); // New Field
        tvHeaderName = findViewById(R.id.tvHeaderName);
        tvDriverId = findViewById(R.id.tvDriverId);
        btnAction = findViewById(R.id.btnAction);
        LinearLayout btnBack = findViewById(R.id.btnBack);

        if(currentDriver != null) {
            tvHeaderName.setText(currentDriver.getName());
            tvDriverId.setText("Driver ID: " + currentDriver.getId());
            etName.setText(currentDriver.getName());
            etPhone.setText(currentDriver.getPhone());
            etEmail.setText(currentDriver.getEmail());
            etPlate.setText(currentDriver.getPlateNumber());
        }

        setFieldsEnabled(false);

        if (btnAction != null) {
            btnAction.setOnClickListener(v -> {
                if (!isEditing) {
                    isEditing = true;
                    setFieldsEnabled(true);
                    btnAction.setText("Save");
                    etName.requestFocus();
                } else {
                    if (currentDriver != null) {
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
                                newPlate // Save Plate
                        );
                        tvHeaderName.setText(newName);
                    }
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
        etPlate.setEnabled(enabled);

        int color = enabled ? Color.BLACK : Color.parseColor("#61768A");
        etName.setTextColor(color);
        etPhone.setTextColor(color);
        etEmail.setTextColor(color);
        etPlate.setTextColor(color);

        if (enabled) {
            etName.setBackgroundResource(android.R.drawable.edit_text);
            etPhone.setBackgroundResource(android.R.drawable.edit_text);
            etEmail.setBackgroundResource(android.R.drawable.edit_text);
            etPlate.setBackgroundResource(android.R.drawable.edit_text);
        } else {
            etName.setBackgroundColor(Color.TRANSPARENT);
            etPhone.setBackgroundColor(Color.TRANSPARENT);
            etEmail.setBackgroundColor(Color.TRANSPARENT);
            etPlate.setBackgroundColor(Color.TRANSPARENT);
        }
    }
}