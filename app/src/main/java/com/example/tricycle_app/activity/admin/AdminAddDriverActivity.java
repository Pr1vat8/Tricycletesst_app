package com.example.tricycle_app.activity.admin;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.tricycle_app.R;
import com.example.tricycle_app.model.Driver;
import com.example.tricycle_app.repository.DriverRepository;
import com.example.tricycle_app.utils.AdminNavbar;
import java.util.UUID;

public class AdminAddDriverActivity extends AppCompatActivity {

    private EditText etName, etPhone, etEmail, etAddress, etPlate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_driver);

        AdminNavbar.setup(this);
        DriverRepository.init(this);

        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        etEmail = findViewById(R.id.etEmail);
        etAddress = findViewById(R.id.etAddress);
        etPlate = findViewById(R.id.etPlate);

        TextView btnSave = findViewById(R.id.btnSaveDriver);
        btnSave.setOnClickListener(v -> saveDriver());

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());
    }

    private void saveDriver() {
        String name = etName.getText().toString();
        String phone = etPhone.getText().toString();
        String email = etEmail.getText().toString();
        String address = etAddress.getText().toString();
        String plate = etPlate.getText().toString();

        if (name.isEmpty() || phone.isEmpty() || plate.isEmpty()) {
            Toast.makeText(this, "Please fill in required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        String id = UUID.randomUUID().toString().substring(0, 8);
        Driver newDriver = new Driver(id, name, phone, email, address, plate, "Verified", false);

        DriverRepository.addDriver(this, newDriver); // Needs addDriver method in repo
        Toast.makeText(this, "Driver Added Successfully", Toast.LENGTH_SHORT).show();
        finish();
    }
}