package com.example.tricycle_app.activity.admin;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tricycle_app.R;
import com.example.tricycle_app.model.Driver;
import com.example.tricycle_app.repository.DriverRepository;
import com.example.tricycle_app.utils.AdminNavbar;

import java.util.UUID;

public class AdminAddDriverActivity extends AppCompatActivity {

    private EditText etName, etPhone, etEmail, etAddress, etPlate, etLicenseNumber, etLicenseExpiration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_driver.xml); // Using the newly created layout

        // Initialize Repository
        DriverRepository.init(this);
        AdminNavbar.setup(this);

        // UI Bindings
        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        etEmail = findViewById(R.id.etEmail);
        etAddress = findViewById(R.id.etAddress);
        etPlate = findViewById(R.id.etPlate);
        etLicenseNumber = findViewById(R.id.etLicenseNumber);
        etLicenseExpiration = findViewById(R.id.etLicenseExpiration);

        Button btnAddDriver = findViewById(R.id.btnAddDriver);
        LinearLayout btnBack = findViewById(R.id.btnBack);

        if (btnAddDriver != null) {
            btnAddDriver.setOnClickListener(v -> saveDriver());
        }

        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }
    }

    private void saveDriver() {
        String name = etName.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        String plate = etPlate.getText().toString().trim();
        String license = etLicenseNumber.getText().toString().trim();
        String expiration = etLicenseExpiration.getText().toString().trim();

        // Basic Validation
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please fill in all required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Generate a random ID (In a real app, this might come from the server or auto-increment)
        String id = String.valueOf(System.currentTimeMillis() % 10000); // Simple ID generation

        // Create new Driver object
        // Status: "Verified" because Admin is adding them directly
        // Suspended: false
        Driver newDriver = new Driver(
                id, name, phone, email, address, plate, "Verified", false, license, expiration, ""
        );

        DriverRepository.addDriver(this, newDriver);

        Toast.makeText(this, "Driver Added Successfully!", Toast.LENGTH_SHORT).show();
        finish();
    }
}
