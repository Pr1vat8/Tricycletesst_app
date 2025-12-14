package com.example.tricycle_app.activity.admin;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
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
import java.util.Locale;

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

        // Status Logic
        if(d.getStatus().equalsIgnoreCase("Rejected")) {
            tvStatus.setText("Status: Rejected");
            tvStatus.setTextColor(Color.RED);
        } else if (d.getStatus().equalsIgnoreCase("Pending")) {
            tvStatus.setText("Status: Pending");
            tvStatus.setTextColor(Color.parseColor("#FF9800"));
        } else {
            // Verified
            if (d.isSuspended()) {
                tvStatus.setText("Status: Suspended\n(" + d.getSuspendStartDate() + " - " + d.getSuspendEndDate() + ")");
                tvStatus.setTextColor(Color.RED);
            } else {
                tvStatus.setText("Status: Verified");
                tvStatus.setTextColor(Color.parseColor("#088738"));
            }
        }

        if (d.getStatus().equalsIgnoreCase("Pending") || d.getStatus().equalsIgnoreCase("Rejected")) {
            setupPendingUI();
        } else {
            setupVerifiedUI(d);
        }
    }

    private void setupPendingUI() {
        btnLeft.setText("Approve Driver");
        btnLeft.setBackgroundResource(R.drawable.bg_pill_blue);
        btnLeft.setTextColor(Color.WHITE);

        btnLeft.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Approve Application")
                    .setMessage("Verify this driver?")
                    .setPositiveButton("Approve", (dialog, which) -> {
                        DriverRepository.approveDriver(this, driverId);
                        Toast.makeText(this, "Driver Approved!", Toast.LENGTH_SHORT).show();
                        loadDriverData();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });

        btnRight.setText("Reject Application");
        btnRight.setBackgroundResource(R.drawable.bg_pill_grey);
        btnRight.setTextColor(Color.parseColor("#0D141C"));

        btnRight.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Reject Application")
                    .setMessage("Reject this application?")
                    .setPositiveButton("Reject", (dialog, which) -> {
                        DriverRepository.rejectDriver(this, driverId);
                        Toast.makeText(this, "Application Rejected", Toast.LENGTH_SHORT).show();
                        loadDriverData();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });
    }

    private void setupVerifiedUI(Driver d) {
        // Button 1: Edit
        btnLeft.setText("Edit");
        btnLeft.setBackgroundResource(R.drawable.bg_pill_grey);
        btnLeft.setTextColor(Color.parseColor("#0D141C"));

        btnLeft.setOnClickListener(v -> {
            if (isEditing) saveChanges();
            else enableEditing();
        });

        // Button 2: Suspend/Unsuspend
        if (d.isSuspended()) {
            btnRight.setText("Unsuspend");
            btnRight.setBackgroundResource(R.drawable.bg_pill_green);
            tvHeaderName.setTextColor(Color.RED);

            btnRight.setOnClickListener(v -> {
                new AlertDialog.Builder(this)
                        .setTitle("Unsuspend Driver?")
                        .setMessage("This will reactivate the driver account.")
                        .setPositiveButton("Unsuspend", (dialog, which) -> {
                            DriverRepository.unsuspendDriver(this, driverId);
                            loadDriverData();
                            Toast.makeText(this, "Driver Unsuspended", Toast.LENGTH_SHORT).show();
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            });
        } else {
            btnRight.setText("Suspend");
            btnRight.setBackgroundResource(R.drawable.bg_pill_red);
            tvHeaderName.setTextColor(Color.parseColor("#0D141C"));

            btnRight.setOnClickListener(v -> showSuspendDateDialog());
        }
        btnRight.setTextColor(Color.WHITE);
    }

    private void showSuspendDateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_suspend_driver, null);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();

        // Dialog Views
        LinearLayout btnFrom = dialogView.findViewById(R.id.btnFromDate);
        LinearLayout btnTo = dialogView.findViewById(R.id.btnToDate);
        TextView tvFrom = dialogView.findViewById(R.id.tvFromDate);
        TextView tvTo = dialogView.findViewById(R.id.tvToDate);
        TextView btnConfirm = dialogView.findViewById(R.id.btnConfirmSuspend);
        TextView btnCancel = dialogView.findViewById(R.id.btnCancelSuspend);

        final Calendar calFrom = Calendar.getInstance();
        final Calendar calTo = Calendar.getInstance();
        calTo.add(Calendar.DAY_OF_YEAR, 7); // Default 1 week

        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy", Locale.US);
        tvFrom.setText(sdf.format(calFrom.getTime()));
        tvTo.setText(sdf.format(calTo.getTime()));

        // Date Pickers
        btnFrom.setOnClickListener(v -> {
            new DatePickerDialog(this, (view, year, month, day) -> {
                calFrom.set(year, month, day);
                tvFrom.setText(sdf.format(calFrom.getTime()));
            }, calFrom.get(Calendar.YEAR), calFrom.get(Calendar.MONTH), calFrom.get(Calendar.DAY_OF_MONTH)).show();
        });

        btnTo.setOnClickListener(v -> {
            new DatePickerDialog(this, (view, year, month, day) -> {
                calTo.set(year, month, day);
                tvTo.setText(sdf.format(calTo.getTime()));
            }, calTo.get(Calendar.YEAR), calTo.get(Calendar.MONTH), calTo.get(Calendar.DAY_OF_MONTH)).show();
        });

        btnConfirm.setOnClickListener(v -> {
            if (calFrom.after(calTo)) {
                Toast.makeText(this, "Start date cannot be after End date", Toast.LENGTH_SHORT).show();
                return;
            }
            DriverRepository.suspendDriver(this, driverId, tvFrom.getText().toString(), tvTo.getText().toString());
            Toast.makeText(this, "Driver Suspended", Toast.LENGTH_SHORT).show();
            loadDriverData();
            dialog.dismiss();
        });

        btnCancel.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    private void enableEditing() {
        isEditing = true;
        btnLeft.setText("Save");
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
}