package com.example.tricycle_app.activity.admin;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tricycle_app.R;
import com.example.tricycle_app.adapter.PayoutAdapter;
import com.example.tricycle_app.model.Payout;
import com.example.tricycle_app.repository.PayoutRepository;
import com.example.tricycle_app.utils.AdminNavbar;

import java.util.List;

public class AdminPayoutActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PayoutAdapter adapter;
    private String currentTab = "Pending"; // Default Tab

    // UI for Tabs
    private TextView tvPending, tvHistory;
    private View linePending, lineHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adminpayouts);

        AdminNavbar.setup(this);
        PayoutRepository.init(this); // Load Data

        // Setup Views
        recyclerView = findViewById(R.id.recyclerViewPayouts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        tvPending = findViewById(R.id.tvPending);
        tvHistory = findViewById(R.id.tvHistory);
        linePending = findViewById(R.id.linePending);
        lineHistory = findViewById(R.id.lineHistory);

        // Tab Logic
        findViewById(R.id.tabPending).setOnClickListener(v -> switchTab("Pending"));
        findViewById(R.id.tabHistory).setOnClickListener(v -> switchTab("Paid"));

        LinearLayout btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) btnBack.setOnClickListener(v -> finish());

        // Initial Load
        loadData();
    }

    private void switchTab(String tab) {
        currentTab = tab;

        if (tab.equals("Pending")) {
            tvPending.setTextColor(Color.parseColor("#0D141C"));
            linePending.setBackgroundColor(Color.parseColor("#0D141C"));
            tvHistory.setTextColor(Color.parseColor("#4A739C"));
            lineHistory.setBackgroundColor(Color.TRANSPARENT);
        } else {
            tvHistory.setTextColor(Color.parseColor("#0D141C"));
            lineHistory.setBackgroundColor(Color.parseColor("#0D141C"));
            tvPending.setTextColor(Color.parseColor("#4A739C"));
            linePending.setBackgroundColor(Color.TRANSPARENT);
        }

        loadData();
    }

    private void loadData() {
        List<Payout> list = PayoutRepository.getPayoutsByStatus(currentTab);
        adapter = new PayoutAdapter(this, list);

        adapter.setOnItemClickListener(payout -> {
            // Confirm Action
            new AlertDialog.Builder(this)
                    .setTitle("Confirm Payout")
                    .setMessage("Mark payout of ₱" + payout.getAmount() + " to " + payout.getDriverName() + " as PAID?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        PayoutRepository.markAsPaid(this, payout);
                        Toast.makeText(this, "Marked as Paid", Toast.LENGTH_SHORT).show();
                        loadData(); // Refresh list
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });

        recyclerView.setAdapter(adapter);
    }
}