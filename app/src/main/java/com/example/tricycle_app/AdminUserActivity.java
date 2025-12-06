package com.example.tricycle_app;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class AdminUserActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DriverAdapter adapter;
    private String currentTab = "Verified";

    private TextView tvVerified, tvPending;
    private View lineVerified, linePending;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adminusers);

        AdminNavbar.setup(this);
        DriverRepository.init(this);

        recyclerView = findViewById(R.id.recyclerViewDrivers);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        tvVerified = findViewById(R.id.tvVerified);
        tvPending = findViewById(R.id.tvPending);
        lineVerified = findViewById(R.id.lineVerified);
        linePending = findViewById(R.id.linePending);
        EditText etSearch = findViewById(R.id.etSearchDriver);

        loadData();

        findViewById(R.id.tabVerified).setOnClickListener(v -> switchTab("Verified"));
        findViewById(R.id.tabPending).setOnClickListener(v -> switchTab("Pending"));

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterData(s.toString());
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        findViewById(R.id.btnPassengers).setOnClickListener(v -> {
            startActivity(new Intent(this, AdminPassengerActivity.class));
        });

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());
    }

    private void switchTab(String tab) {
        currentTab = tab;
        if (tab.equals("Verified")) {
            tvVerified.setTextColor(Color.parseColor("#0D141C"));
            lineVerified.setBackgroundColor(Color.parseColor("#0D141C"));
            tvPending.setTextColor(Color.parseColor("#4A739C"));
            linePending.setBackgroundColor(Color.TRANSPARENT);
        } else {
            tvPending.setTextColor(Color.parseColor("#0D141C"));
            linePending.setBackgroundColor(Color.parseColor("#0D141C"));
            tvVerified.setTextColor(Color.parseColor("#4A739C"));
            lineVerified.setBackgroundColor(Color.TRANSPARENT);
        }
        loadData();
    }

    private void loadData() {
        List<Driver> list = DriverRepository.getDriversByStatus(currentTab);
        adapter = new DriverAdapter(this, list);

        // --- CRITICAL FIX: PASS ID, NOT INDEX ---
        adapter.setOnItemClickListener(driver -> {
            Intent intent = new Intent(AdminUserActivity.this, AdmindriverdetailActivity.class);
            intent.putExtra("DRIVER_ID", driver.getId()); // Passing String ID
            startActivity(intent);
        });

        recyclerView.setAdapter(adapter);
    }

    private void filterData(String query) {
        List<Driver> filtered = DriverRepository.searchDrivers(query, currentTab);
        adapter.updateList(filtered);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData(); // Refresh list when coming back
    }
}