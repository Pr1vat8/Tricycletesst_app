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
import com.example.tricycle_app.model.Passenger;
import com.example.tricycle_app.repository.PassengerRepository;
import com.example.tricycle_app.utils.AdminNavbar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AdminPassengerDetailsActivity extends AppCompatActivity {

    private boolean isEditing = false;
    private int passengerIndex;

    private EditText etName, etPhone, etEmail, etAddress;
    private TextView tvHeaderName, tvHeaderId, tvStatus;
    private TextView btnLeft, btnRight;
    private LinearLayout btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adminpassengerdetails);

        AdminNavbar.setup(this);

        passengerIndex = getIntent().getIntExtra("PASSENGER_INDEX", 0);

        initViews();
        loadData();

        if (btnBack != null) btnBack.setOnClickListener(v -> finish());

        if (btnLeft != null) {
            btnLeft.setOnClickListener(v -> {
                if (isEditing) saveChanges();
                else enableEditing();
            });
        }
    }

    private void initViews() {
        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        etEmail = findViewById(R.id.etEmail);
        etAddress = findViewById(R.id.etAddress);

        tvHeaderName = findViewById(R.id.tvHeaderName);
        tvHeaderId = findViewById(R.id.tvHeaderId);
        tvStatus = findViewById(R.id.tvStatus);

        btnLeft = findViewById(R.id.btnLeft);   // Edit Button
        btnRight = findViewById(R.id.btnRight); // Suspend Button
        btnBack = findViewById(R.id.btnBack);
    }

    private void loadData() {
        Passenger p = PassengerRepository.getPassenger(passengerIndex);
        if (p != null) {
            etName.setText(p.getName());
            etPhone.setText(p.getPhone());
            etEmail.setText(p.getEmail());
            etAddress.setText(p.getAddress());

            tvHeaderName.setText(p.getName());
            tvHeaderId.setText("Passenger ID: " + p.getId());

            updateSuspendUI(p);
        }
    }

    private void updateSuspendUI(Passenger p) {
        // Clear previous listeners to avoid stacking
        btnRight.setOnClickListener(null);

        if (p.isSuspended()) {
            // SUSPENDED STATE
            String statusText = "Status: Suspended";
            if(p.getSuspendStartDate() != null && !p.getSuspendStartDate().isEmpty()) {
                statusText += "\n(" + p.getSuspendStartDate() + " - " + p.getSuspendEndDate() + ")";
            }
            tvStatus.setText(statusText);
            tvStatus.setTextColor(Color.RED);
            tvHeaderName.setTextColor(Color.RED);

            btnRight.setText("Unsuspend");
            btnRight.setBackgroundResource(R.drawable.bg_pill_green);

            // Logic to Unsuspend
            btnRight.setOnClickListener(v -> {
                new AlertDialog.Builder(this)
                        .setTitle("Unsuspend Passenger?")
                        .setMessage("Reactivate this account?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            PassengerRepository.unsuspendPassenger(this, passengerIndex);
                            loadData(); // Refresh UI
                            Toast.makeText(this, "Account Reactivated", Toast.LENGTH_SHORT).show();
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            });

        } else {
            // ACTIVE STATE
            tvStatus.setText("Status: Active");
            tvStatus.setTextColor(Color.parseColor("#088738"));
            tvHeaderName.setTextColor(Color.parseColor("#0D141C"));

            btnRight.setText("Suspend");
            btnRight.setBackgroundResource(R.drawable.bg_pill_red);

            // Logic to Suspend (Show Date Dialog)
            btnRight.setOnClickListener(v -> showSuspendDateDialog());
        }
    }

    private void showSuspendDateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // We reuse the driver suspension layout
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

        // Optional: Change the hardcoded title "Suspend Driver" to "Suspend Passenger"
        // Since the TextView doesn't have an ID in your provided XML, we can't easily change it here
        // without traversing views, but functionality will work.

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
            // Call the new suspend method with dates
            PassengerRepository.suspendPassenger(this, passengerIndex, tvFrom.getText().toString(), tvTo.getText().toString());
            Toast.makeText(this, "Passenger Suspended", Toast.LENGTH_SHORT).show();
            loadData(); // Refresh UI
            dialog.dismiss();
        });

        btnCancel.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    private void enableEditing() {
        isEditing = true;
        btnLeft.setText("Save");
        btnLeft.setTextColor(Color.WHITE);
        btnLeft.setBackgroundResource(R.drawable.bg_pill_blue);

        etName.setEnabled(true); etPhone.setEnabled(true); etEmail.setEnabled(true); etAddress.setEnabled(true);
        etName.requestFocus();
    }

    private void saveChanges() {
        PassengerRepository.updatePassenger(this, passengerIndex,
                etName.getText().toString(), etPhone.getText().toString(),
                etEmail.getText().toString(), etAddress.getText().toString());

        isEditing = false;
        btnLeft.setText("Edit");
        btnLeft.setTextColor(Color.parseColor("#0D141C"));
        btnLeft.setBackgroundResource(R.drawable.bg_pill_grey);

        etName.setEnabled(false); etPhone.setEnabled(false); etEmail.setEnabled(false); etAddress.setEnabled(false);
        Toast.makeText(this, "Changes Saved!", Toast.LENGTH_SHORT).show();

        tvHeaderName.setText(etName.getText().toString());
    }
}