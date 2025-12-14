package com.example.tricycle_app.activity.admin;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tricycle_app.R;
import com.example.tricycle_app.model.Payout;
import com.example.tricycle_app.repository.PayoutRepository;
import com.example.tricycle_app.utils.AdminNavbar;

public class AdminPayoutDetailsActivity extends AppCompatActivity {

    private String driverName, amount, method, status;
    private TextView tvStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_payout_details);

        AdminNavbar.setup(this);
        PayoutRepository.init(this);

        // Get Data from Intent
        driverName = getIntent().getStringExtra("DRIVER_NAME");
        amount = getIntent().getStringExtra("AMOUNT");
        method = getIntent().getStringExtra("METHOD");
        status = getIntent().getStringExtra("STATUS");

        // Bind Views
        TextView tvAmount = findViewById(R.id.tvDetailAmount);
        TextView tvDriver = findViewById(R.id.tvDetailDriver);
        TextView tvMethod = findViewById(R.id.tvDetailMethod);
        tvStatus = findViewById(R.id.tvDetailStatus);

        TextView btnMarkPaid = findViewById(R.id.btnMarkPaid);
        TextView btnUnpay = findViewById(R.id.btnUnpay);

        // Set Text
        tvAmount.setText("₱" + amount);
        tvDriver.setText(driverName);
        tvMethod.setText(method);
        tvStatus.setText(status);

        // Logic for Buttons
        if ("Pending".equalsIgnoreCase(status)) {
            btnMarkPaid.setVisibility(View.VISIBLE);
            btnUnpay.setVisibility(View.GONE);
            tvStatus.setTextColor(android.graphics.Color.parseColor("#E67E22")); // Orange
        } else {
            btnMarkPaid.setVisibility(View.GONE);
            btnUnpay.setVisibility(View.VISIBLE);
            tvStatus.setTextColor(android.graphics.Color.parseColor("#27AE60")); // Green
        }

        // Button Actions
        btnMarkPaid.setOnClickListener(v -> {
            // Find object in repo (simplified for this context, ideally pass ID)
            // Assuming uniqueness by name/amount for now or using the reference logic in repo
            Payout p = findPayout();
            if (p != null) {
                PayoutRepository.markAsPaid(this, p);
                Toast.makeText(this, "Marked as Paid", Toast.LENGTH_SHORT).show();
                finish(); // Return to list
            }
        });

        btnUnpay.setOnClickListener(v -> {
            Payout p = findPayout();
            if (p != null) {
                PayoutRepository.markAsPending(this, p);
                Toast.makeText(this, "Reverted to Pending", Toast.LENGTH_SHORT).show();
                finish(); // Return to list
            }
        });

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());
    }

    private Payout findPayout() {
        // Simple search to find the matching object in the loaded repository
        for (Payout p : PayoutRepository.getPayoutsByStatus(status)) { // This searches current status
            // NOTE: In a real app, pass a unique ID. Here we match attributes.
            // If status changed, we search all.
        }
        // Better approach: Search ALL for match
        for (Payout p : PayoutRepository.getAllPayouts()) {
            if (p.getDriverName().equals(driverName) && p.getAmount().equals(amount)) {
                return p;
            }
        }
        return null;
    }
}