package com.example.tricycle_app.activity.admin;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
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
    private String currentTab = "Pending";

    private TextView tvPending, tvHistory;
    private View linePending, lineHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adminpayouts);

        AdminNavbar.setup(this);
        PayoutRepository.init(this);

        recyclerView = findViewById(R.id.recyclerViewPayouts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        tvPending = findViewById(R.id.tvPending);
        tvHistory = findViewById(R.id.tvHistory);
        linePending = findViewById(R.id.linePending);
        lineHistory = findViewById(R.id.lineHistory);

        findViewById(R.id.tabPending).setOnClickListener(v -> switchTab("Pending"));
        findViewById(R.id.tabHistory).setOnClickListener(v -> switchTab("Paid"));

        LinearLayout btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) btnBack.setOnClickListener(v -> finish());
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData(); // Refresh list when returning from Details activity
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
            Intent intent = new Intent(AdminPayoutActivity.this, AdminPayoutDetailsActivity.class);
            intent.putExtra("DRIVER_NAME", payout.getDriverName());
            intent.putExtra("AMOUNT", payout.getAmount());
            intent.putExtra("METHOD", payout.getPaymentMethod());
            intent.putExtra("STATUS", payout.getStatus());
            startActivity(intent);
        });

        recyclerView.setAdapter(adapter);
    }
}