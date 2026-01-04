package com.example.tricycle_app.activity.admin;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tricycle_app.R;
import com.example.tricycle_app.model.Driver;
import com.example.tricycle_app.repository.DriverRepository;
import com.example.tricycle_app.utils.AdminNavbar;

import java.util.UUID;

public class AdminAddDriverActivity extends AppCompatActivity {

    private EditText etName, etPhone, etEmail, etAddress, etRoute, etPlate, etModel, etAccountNumber;
    private TextView btnUploadLicense, btnUploadRegistration;
    private TextView activeUploadButton;

    private final ActivityResultLauncher<Intent> galleryLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    if (activeUploadButton != null) {
                        activeUploadButton.setText("Done");
                        activeUploadButton.setTextColor(Color.parseColor("#088738"));
                        Toast.makeText(this, "Document selected!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_driver);

        AdminNavbar.setup(this);
        DriverRepository.init(this);

        initViews();

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());
        findViewById(R.id.btnSaveDriver).setOnClickListener(v -> saveDriver());
    }

    private void initViews() {
        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        etEmail = findViewById(R.id.etEmail);
        etAddress = findViewById(R.id.etAddress);
        etRoute = findViewById(R.id.etRoute); // NEW: Route Field
        etModel = findViewById(R.id.etModel);
        etPlate = findViewById(R.id.etPlate);
        etAccountNumber = findViewById(R.id.etAccountNumber);

        btnUploadLicense = findViewById(R.id.btnUploadLicense);
        btnUploadRegistration = findViewById(R.id.btnUploadRegistration);

        btnUploadLicense.setOnClickListener(v -> {
            activeUploadButton = btnUploadLicense;
            openGallery();
        });

        btnUploadRegistration.setOnClickListener(v -> {
            activeUploadButton = btnUploadRegistration;
            openGallery();
        });
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryLauncher.launch(intent);
    }

    private void saveDriver() {
        String name = etName.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        String route = etRoute.getText().toString().trim(); // Get Route
        String model = etModel.getText().toString().trim();
        String plate = etPlate.getText().toString().trim();
        String accountNum = etAccountNumber.getText().toString().trim();

        if (name.isEmpty() || phone.isEmpty() || plate.isEmpty() || route.isEmpty()) {
            Toast.makeText(this, "Please fill in required fields (including Route)", Toast.LENGTH_SHORT).show();
            return;
        }

        String id = UUID.randomUUID().toString().substring(0, 8);
        String username = name.split(" ")[0].toLowerCase();
        String password = "123";

        // Fixed Constructor Call: Added 'route' parameter
        Driver newDriver = new Driver(
                id,
                name,
                phone,
                email,
                address,
                plate,
                route, // <--- Added this to fix the error
                "Verified",
                false,
                "",
                "",
                username,
                password
        );

        DriverRepository.addDriver(this, newDriver);
        Toast.makeText(this, "Driver Added! User: " + username + " Pass: " + password, Toast.LENGTH_LONG).show();
        finish();
    }
}