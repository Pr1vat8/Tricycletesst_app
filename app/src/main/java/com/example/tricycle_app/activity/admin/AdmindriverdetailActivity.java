package com.example.tricycle_app.activity.admin;

import android.app.AlertDialog;
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
import com.example.tricycle_app.utils.AdminNavbar;

public class AdmindriverdetailActivity extends AppCompatActivity {

    private String driverId;
    private boolean isEditing = false;

    private EditText etName, etPhone, etEmail, etAddress, etPlate;
    private TextView tvStatus, tvHeaderName, tvHeaderId;
    private TextView btnLeft, btnRight;
    private LinearLayout btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admindriverdetails);

        AdminNavbar.setup(this);

        driverId = getIntent().getStringExtra("DRIVER_ID");

        initViews();
        loadDriverData();

        if (btnBack != null) btnBack.setOnClickListener(v -> finish());
    }

    private void initViews() {
        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        etEmail = findViewById(R.id.etEmail);
        etAddress = findViewById(R.id.etAddress);
        etPlate = findViewById(R.id.etPlate);

        tvHeaderName = findViewById(R.id.tvHeaderName);
        tvHeaderId = findViewById(R.id.tvHeaderId);
        tvStatus = findViewById(R.id.tvStatus);

        btnLeft = findViewById(R.id.btnLeft);
        btnRight = findViewById(R.id.btnRight);
        btnBack = findViewById(R.id.btnBack);
    }

    private void loadDriverData() {
        Driver d = DriverRepository.getDriverById(driverId);
        if (d == null) {
            Toast.makeText(this, "Driver not found!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        etName.setText(d.getName());
        etPhone.setText(d.getPhone());
        etEmail.setText(d.getEmail());
        etAddress.setText(d.getAddress());
        etPlate.setText(d.getPlateNumber());

        tvHeaderName.setText(d.getName());
        tvHeaderId.setText("Driver ID: " + d.getId());

        // Status Text Color Logic
        tvStatus.setText("Status: " + d.getStatus());
        if(d.getStatus().equalsIgnoreCase("Rejected")) {
            tvStatus.setTextColor(Color.RED);
        } else if (d.getStatus().equalsIgnoreCase("Pending")) {
            tvStatus.setTextColor(Color.parseColor("#FF9800")); // Orange
        } else {
            tvStatus.setTextColor(Color.parseColor("#088738")); // Green
        }

        // Logic to switch button text/function
        if (d.getStatus().equalsIgnoreCase("Pending") || d.getStatus().equalsIgnoreCase("Rejected")) {
            setupPendingUI();
        } else {
            setupVerifiedUI(d);
        }
    }

    private void setupPendingUI() {
        // --- BUTTON 1: APPROVE ---
        btnLeft.setText("Approve Driver");
        // FIX: Use setBackgroundResource to keep the pill shape
        btnLeft.setBackgroundResource(R.drawable.bg_pill_blue);
        btnLeft.setTextColor(Color.WHITE);

        btnLeft.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Approve Application")
                    .setMessage("Are you sure you want to verify this driver?")
                    .setPositiveButton("Approve", (dialog, which) -> {
                        DriverRepository.approveDriver(this, driverId);
                        Toast.makeText(this, "Driver Approved!", Toast.LENGTH_SHORT).show();
                        finish();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });

        // --- BUTTON 2: REJECT ---
        btnRight.setText("Reject Application");
        // FIX: Use setBackgroundResource
        btnRight.setBackgroundResource(R.drawable.bg_pill_grey);
        btnRight.setTextColor(Color.parseColor("#0D141C"));

        btnRight.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Reject Application")
                    .setMessage("Are you sure you want to reject this application?")
                    .setPositiveButton("Reject", (dialog, which) -> {
                        DriverRepository.rejectDriver(this, driverId);
                        Toast.makeText(this, "Application Rejected", Toast.LENGTH_SHORT).show();
                        finish();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });
    }

    private void setupVerifiedUI(Driver d) {
        // --- BUTTON 1: EDIT ---
        btnLeft.setText("Edit");
        // FIX: Use setBackgroundResource
        btnLeft.setBackgroundResource(R.drawable.bg_pill_grey);
        btnLeft.setTextColor(Color.parseColor("#0D141C"));

        btnLeft.setOnClickListener(v -> {
            if (isEditing) saveChanges();
            else enableEditing();
        });

        // --- BUTTON 2: SUSPEND / UNSUSPEND ---
        if (d.isSuspended()) {
            btnRight.setText("Unsuspend");
            // FIX: Use Green Pill
            btnRight.setBackgroundResource(R.drawable.bg_pill_green);
            tvHeaderName.setTextColor(Color.RED);
        } else {
            btnRight.setText("Suspend");
            // FIX: Use Red Pill
            btnRight.setBackgroundResource(R.drawable.bg_pill_red);
            tvHeaderName.setTextColor(Color.parseColor("#0D141C"));
        }
        btnRight.setTextColor(Color.WHITE);

        btnRight.setOnClickListener(v -> showSuspendDialog(d));
    }

    private void enableEditing() {
        isEditing = true;
        btnLeft.setText("Save");
        // FIX: Use Blue Pill
        btnLeft.setBackgroundResource(R.drawable.bg_pill_blue);
        btnLeft.setTextColor(Color.WHITE);

        etName.setEnabled(true); etPhone.setEnabled(true);
        etEmail.setEnabled(true); etAddress.setEnabled(true); etPlate.setEnabled(true);
    }

    private void saveChanges() {
        DriverRepository.updateDriver(this, driverId,
                etName.getText().toString(), etPhone.getText().toString(),
                etEmail.getText().toString(), etAddress.getText().toString(), etPlate.getText().toString());

        isEditing = false;
        loadDriverData();
        etName.setEnabled(false); etPhone.setEnabled(false);
        etEmail.setEnabled(false); etAddress.setEnabled(false); etPlate.setEnabled(false);
        Toast.makeText(this, "Changes Saved", Toast.LENGTH_SHORT).show();
    }

    private void showSuspendDialog(Driver d) {
        String title = d.isSuspended() ? "Unsuspend Driver?" : "Suspend Driver?";

        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage("Are you sure you want to proceed?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    DriverRepository.toggleSuspend(this, driverId);
                    loadDriverData();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}