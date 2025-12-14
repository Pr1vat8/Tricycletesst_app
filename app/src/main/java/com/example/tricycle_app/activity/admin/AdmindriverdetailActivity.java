package com.example.tricycle_app.activity.admin;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tricycle_app.R;
import com.example.tricycle_app.model.Driver;
import com.example.tricycle_app.repository.DriverRepository;
import com.example.tricycle_app.utils.AdminNavbar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AdmindriverdetailActivity extends AppCompatActivity {

    private String driverId;
    private boolean isEditing = false;

    private EditText etName, etPhone, etEmail, etAddress, etPlate, etLicenseNumber, etLicenseExpiration;
    private TextView tvStatus, tvHeaderName, tvHeaderId;
    private TextView btnLeft, btnRight;
    private LinearLayout btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admindriverdetails); // I need to update this layout to include new fields if I want them visible here too

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
        // Assuming layout might not have these IDs yet, need to be careful.
        // I will update the layout xml as well to include them.
        etLicenseNumber = findViewById(R.id.etLicenseNumber);
        etLicenseExpiration = findViewById(R.id.etLicenseExpiration);

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
        if (etLicenseNumber != null) etLicenseNumber.setText(d.getLicenseNumber());
        if (etLicenseExpiration != null) etLicenseExpiration.setText(d.getLicenseExpirationDate());

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

            btnRight.setOnClickListener(v -> {
                new AlertDialog.Builder(this)
                    .setTitle("Unsuspend Driver?")
                    .setMessage("Are you sure you want to lift the suspension?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        DriverRepository.toggleSuspend(this, driverId);
                        loadDriverData();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
            });

        } else {
            btnRight.setText("Suspend");
            // FIX: Use Red Pill
            btnRight.setBackgroundResource(R.drawable.bg_pill_red);
            tvHeaderName.setTextColor(Color.parseColor("#0D141C"));

            btnRight.setOnClickListener(v -> showSuspendDialog(d));
        }
        btnRight.setTextColor(Color.WHITE);
    }

    private void enableEditing() {
        isEditing = true;
        btnLeft.setText("Save");
        // FIX: Use Blue Pill
        btnLeft.setBackgroundResource(R.drawable.bg_pill_blue);
        btnLeft.setTextColor(Color.WHITE);

        etName.setEnabled(true); etPhone.setEnabled(true);
        etEmail.setEnabled(true); etAddress.setEnabled(true); etPlate.setEnabled(true);
        if (etLicenseNumber != null) etLicenseNumber.setEnabled(true);
        if (etLicenseExpiration != null) etLicenseExpiration.setEnabled(true);
    }

    private void saveChanges() {
        String license = (etLicenseNumber != null) ? etLicenseNumber.getText().toString() : "";
        String expiration = (etLicenseExpiration != null) ? etLicenseExpiration.getText().toString() : "";

        DriverRepository.updateDriverFull(this, driverId,
                etName.getText().toString(), etPhone.getText().toString(),
                etEmail.getText().toString(), etAddress.getText().toString(), etPlate.getText().toString(),
                license, expiration);

        isEditing = false;
        loadDriverData();
        etName.setEnabled(false); etPhone.setEnabled(false);
        etEmail.setEnabled(false); etAddress.setEnabled(false); etPlate.setEnabled(false);
        if (etLicenseNumber != null) etLicenseNumber.setEnabled(false);
        if (etLicenseExpiration != null) etLicenseExpiration.setEnabled(false);

        Toast.makeText(this, "Changes Saved", Toast.LENGTH_SHORT).show();
    }

    private void showSuspendDialog(Driver d) {
        // Create an input for days
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        input.setHint("Enter number of days");

        new AlertDialog.Builder(this)
                .setTitle("Suspend Driver")
                .setMessage("Enter suspension duration in days:")
                .setView(input)
                .setPositiveButton("Suspend", (dialog, which) -> {
                    String daysStr = input.getText().toString();
                    if (!daysStr.isEmpty()) {
                        int days = Integer.parseInt(daysStr);
                        calculateAndSetSuspension(days);
                    } else {
                        Toast.makeText(this, "Invalid number of days", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void calculateAndSetSuspension(int days) {
        // Calculate End Date
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, days);
        Date endDate = calendar.getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        String dateString = sdf.format(endDate);

        DriverRepository.setSuspensionEndDate(this, driverId, dateString);
        loadDriverData();
        Toast.makeText(this, "Driver suspended for " + days + " days.", Toast.LENGTH_SHORT).show();
    }
}
